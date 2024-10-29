package com.example.kzh.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
