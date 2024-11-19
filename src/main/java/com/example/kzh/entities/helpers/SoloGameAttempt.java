package com.example.kzh.entities.helpers;

import com.example.kzh.entities.QuizQuestion;
import com.example.kzh.entities.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class SoloGameAttempt extends BaseEntity {
    private QuizQuestion quizQuestion;
    private List<Variant> selectedVariants;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Duration getDuration() {
        return Duration.between(startTime, endTime);
    }

    public boolean isFullyCorrect() {
        var correctSelected = selectedVariants.stream().filter(Variant::isCorrect).count();
        var correct = quizQuestion.getVariants().stream().filter(Variant::isCorrect).count();
        return correctSelected == correct;
    }


}
