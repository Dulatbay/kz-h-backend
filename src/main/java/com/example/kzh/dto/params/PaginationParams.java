package com.example.kzh.dto.params;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationParams {
    private int page;
    private int size;
}
