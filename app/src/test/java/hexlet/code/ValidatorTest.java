package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    Validator validator;
    BaseSchema baseSchema;

    @BeforeEach
    public void setUp() {
        validator = new Validator();
        baseSchema = new BaseSchema();

    }

// тесты для проверки String схемы
    @Test
    public void testStringValidation() {
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
        StringSchema schema = validator.string();

        // Без обязательного поля
        assertTrue(schema.isValid(null), "Null should be valid when not required");
        assertTrue(schema.isValid(""), "Empty string should be valid when not required");
    }

// тесты для проверки Number схемы
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
        schema.range(0,100);
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

