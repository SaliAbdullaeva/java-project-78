package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MapSchemaTest {

    Validator validator = new Validator();

    @Test
    public void testMapValidation() {
        MapSchema schema = validator.map();
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> empty = new HashMap<>();

        map.put("Banana", 2);
        map.put("Cherry", 3);
        map.put("Apple", 7);
        map.put("Mango", 3);
        map.put("Huy", 0);

        // Тестирование обязательного поля
        schema.required();
        assertFalse(schema.isValid(null), "Null should not be valid");
        assertTrue(schema.isValid(map), "The map should be valid");

        //Тестирование ограничения на размер мапы
        schema.sizeof(2);
        assertFalse(schema.isValid(map), "The number of key-value pairs must be equal to the specified one");
        assertFalse(schema.isValid(empty), "There are not enough elements on the map");

    }

    @Test
    public void testMapWithoutRequired() {
        MapSchema schema = validator.map();

        // Без обязательного поля
        assertTrue(schema.isValid(null), "Null should be valid when not required");
    }

    @Test
    public void testHumanValidation() {
        MapSchema schema = validator.map();
        // Создаем набор схем для проверки каждого ключа проверяемого объекта:
        Map<String, BaseSchema<String>> schemas = new HashMap<>();

        // Определяем схемы валидации для значений свойств "firstName" и "lastName"
        schemas.put("firstName", validator.string().required().minLength(3)); // Минимальная длина 3
        schemas.put("lastName", validator.string().required().minLength(2)); // Минимальная длина 2
        schema.shape(schemas);

        // Проверяем валидные данные
        Map<String, String> validHuman = new HashMap<>();
        validHuman.put("firstName", "John");
        validHuman.put("lastName", "Smith");
        assertTrue(schema.isValid(validHuman), "Valid human data should be accepted");

        // Проверяем невалидные данные: lastName = null
        Map<String, String> invalidHuman1 = new HashMap<>();
        invalidHuman1.put("firstName", "John");
        invalidHuman1.put("lastName", null);
        assertFalse(schema.isValid(invalidHuman1), "Null lastName should not be valid");

        // Проверяем невалидные данные: lastName слишком короткий
        Map<String, String> invalidHuman2 = new HashMap<>();
        invalidHuman2.put("firstName", "Anna");
        invalidHuman2.put("lastName", "B");
        assertFalse(schema.isValid(invalidHuman2), "LastName shorter than 2 characters should not be valid");

    }
}
