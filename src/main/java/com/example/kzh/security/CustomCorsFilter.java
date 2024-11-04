package com.example.kzh.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Slf4j
public class CustomCorsFilter extends OncePerRequestFilter {

    private static final Set<String> ALLOWED_LOCALES = Set.of("KAZ", "RU");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, accept-language");

        if (!isLocaleCorrect(request, response)) {
            return;
        }


        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private static boolean isLocaleCorrect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String locale = request.getHeader("Accept-Language");

        if (locale == null) {
            locale = "RU";
            request.setAttribute("Accept-Language", locale);
            return true;
        }

        var words = locale.split(",");

        if (words.length == 0) {
            return false;
        }

        locale = words[0];

        return ALLOWED_LOCALES.contains(locale.toUpperCase());
    }
}

