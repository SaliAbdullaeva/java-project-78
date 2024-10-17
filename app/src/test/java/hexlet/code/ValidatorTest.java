package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    @Test
    public void testStringValidation() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();

        // Тестирование обязательного поля
        schema.required();
        assertFalse(schema.isValid(null), "Null should not be valid");
        assertFalse(schema.isValid(""), "Empty string should not be valid");
        assertTrue(schema.isValid("Hello"), "Non-empty string should be valid");

        // Тестирование минимальной длины
        schema.minLength(5);
        assertFalse(schema.isValid("Hi"), "String shorter than minLength should not be valid");
        assertTrue(schema.isValid("Hello"), "String equal to minLength should be valid");
        assertTrue(schema.isValid("Hello, World!"), "String longer than minLength should be valid");

        // Тестирование наличия подстроки
        schema.contains("lo");
        assertTrue(schema.isValid("Hello"), "String containing 'lo' should be valid");
        assertFalse(schema.isValid("Hi"), "String not containing 'lo' should not be valid");
    }

    @Test
    public void testStringWithoutRequired() {
        Validator validator = new Validator();
        StringSchema schema = validator.string();

        // Без обязательного поля
        assertTrue(schema.isValid(null), "Null should be valid when not required");
        assertTrue(schema.isValid(""), "Empty string should be valid when not required");
    }
}

