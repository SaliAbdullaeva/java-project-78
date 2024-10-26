package hexlet.code;

import hexlet.code.schemas.NumberSchema;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class NumberShemaTest {

    Validator validator = new Validator();

    @Test
    public void testNumberValidation() {
        NumberSchema schema = validator.number();

        // Тестирование обязательного поля
        schema.required();
        assertFalse(schema.isValid(null), "Null should not be valid");
        assertTrue(schema.isValid(1995), "Valid number should be accepted");

        //Тестирование отрицательного значения
        schema.positive();
        assertTrue(schema.isValid(10), "Valid number should be accepted");
        assertFalse(schema.isValid(-5), "Negative number should not be valid");
        assertFalse(schema.isValid(0), "Zero should not be valid when positive constraint is applied");

        //Тестирование допустимого диапазона
        schema.range(0, 100);
        assertFalse(schema.isValid(150), "Values greater than 100 should not be valid");
        assertFalse(schema.isValid(-1), "Values less than 0 should not be valid");
        assertTrue(schema.isValid(50), "Value within range should be valid");

    }

    @Test
    public void testNumberWithoutRequired() {
        NumberSchema schema = validator.number();

        // Без обязательного поля
        assertTrue(schema.isValid(null), "Null should be valid when not required");
    }
}
