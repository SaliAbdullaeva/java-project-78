package hexlet.code;

import hexlet.code.schemas.StringSchema;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;



public class StringSchemaTest {

    Validator validator = new Validator();
    StringSchema schema = validator.string();

    @Test
    public void testStringValidation() {

        // Тестирование обязательного поля
        schema.required();
        assertFalse(schema.isValid(null), "Null should not be valid");
        assertFalse(schema.isValid(""), "Empty string should not be valid");
        assertTrue(schema.isValid("Hello"), "Non-empty string should be valid");

        assertThat(schema.isValid("what does the fox say")).isTrue();
        assertThat(schema.isValid("hexlet")).isTrue();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid(null)).isFalse();

        // Тестирование минимальной длины
        schema.minLength(5);
        assertFalse(schema.isValid("Hi"), "String shorter than minLength should not be valid");
        assertTrue(schema.isValid("Hello"), "String equal to minLength should be valid");
        assertTrue(schema.isValid("Hello, World!"), "String longer than minLength should be valid");

        assertThat(schema.isValid("what does the fox say")).isTrue();
        //assertThat(schema.isValid("hexlet")).isFalse();
        assertThat(schema.contains("what").isValid("what does the fox say")).isTrue();
        //assertThat(schema.contains("what the").isValid("what does the fox say")).isFalse();

        var schema1 = validator.string().required().minLength(10).minLength(4);
        assertThat(schema1.isValid("hexlet")).isTrue();


        // Тестирование наличия подстроки
        schema.contains("lo");
        assertTrue(schema.isValid("Hello"), "String containing 'lo' should be valid");
        assertFalse(schema.isValid("Hi"), "String not containing 'lo' should not be valid");
    }

    @Test
    public void testStringWithoutRequired() {

        // Без обязательного поля
        assertTrue(schema.isValid(null), "Null should be valid when not required");
        assertTrue(schema.isValid(""), "Empty string should be valid when not required");
    }
}
