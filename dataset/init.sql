CREATE FUNCTION update_quiz_level() RETURNS TRIGGER
    LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE quiz
    SET level = (
        SELECT AVG(level) FROM question
                                   JOIN quiz_questions ON quiz_questions.question_id = question.id
        WHERE quiz_questions.quiz_id = NEW.quiz_id
    )
    WHERE id = NEW.quiz_id;
    RETURN NEW;
END;
$$;

CREATE TRIGGER update_quiz_level_trigger
    AFTER INSERT OR UPDATE ON quiz_questions
    FOR EACH ROW
EXECUTE FUNCTION update_quiz_level();



