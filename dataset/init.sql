-- Trigger function to calculate and update the quiz level
CREATE OR REPLACE FUNCTION update_quiz_level() RETURNS TRIGGER AS $$
BEGIN
UPDATE quiz
SET level = (
    SELECT AVG(level) FROM question
                               JOIN quiz_questions ON quiz_questions.question_id = question.id
    WHERE quiz_questions.quiz_id = NEW.quiz_id
), is_deleted = false
WHERE id = NEW.quiz_id;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


