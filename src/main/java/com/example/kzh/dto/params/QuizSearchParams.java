package com.example.kzh.dto.params;

import com.example.kzh.entities.enums.Level;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuizSearchParams extends PaginationParams {
    private Level level;
    private Boolean status;
    private List<Long> topics = new ArrayList<>();
    private String searchText;
}

