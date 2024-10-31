package com.example.kzh.mappers;

import com.example.kzh.entities.*;
import com.example.kzh.entities.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VariantMapper {
    public Variant mapFromCreateRequest(String text, User user) {
        Variant variant = new Variant();
        variant.setContent(text);
        variant.setUser(user);
        variant.setVerified(false);
        return variant;
    }
}
