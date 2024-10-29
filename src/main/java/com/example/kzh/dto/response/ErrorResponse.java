package com.example.kzh.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

import static com.example.kzh.constants.ValueConstants.ZONE_ID;

@Data
public class ErrorResponse {
    private String error;
    private String message;
    private long timestamp;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;

        this.timestamp = LocalDateTime.now(ZONE_ID).atZone(ZONE_ID).toEpochSecond();
    }

}
