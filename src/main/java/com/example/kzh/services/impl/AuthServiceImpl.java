package com.example.kzh.services.impl;

import com.example.kzh.dto.request.AuthRequest;
import com.example.kzh.dto.request.RegisterUserRequestDto;
import com.example.kzh.dto.response.AuthResponse;
import com.example.kzh.entities.Token;
import com.example.kzh.entities.User;
import com.example.kzh.entities.enums.Role;
import com.example.kzh.entities.enums.TokenType;
import com.example.kzh.exceptions.DbNotFoundException;
import com.example.kzh.repositories.TokenRepository;
import com.example.kzh.repositories.UserRepository;
import com.example.kzh.security.JwtService;
import com.example.kzh.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void registerUser(RegisterUserRequestDto registerUserRequestDto) {
        userRepository.findByEmail(registerUserRequestDto.getEmail().trim())
                .ifPresent(usr -> {
                    throw new IllegalArgumentException("User with email "+ registerUserRequestDto.getEmail() + " already exists");
                });

        if(!registerUserRequestDto.getPassword().trim().equals(registerUserRequestDto.getConfirmPassword().trim())){
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = new User();
        user.setEmail(registerUserRequestDto.getEmail());
        user.setUsername(registerUserRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));
        user.setRole(Role.USER);

        log.info("Registered user: {}", user);
        userRepository.save(user);
    }

    @Override
    public AuthResponse authenticateUser(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "User doesn't exist"));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException{

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token is empty");
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {

            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new DbNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Token is invalid"));


            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);

            } else {
                throw new IllegalArgumentException("Token is invalid");
            }
        } else
            throw new IllegalArgumentException("Token is empty");
    }
}
