package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;

public class MapSchemaTest {

    Validator validator = new Validator();
    MapSchema schema = validator.map();

    @Test
    public void testMapValidation() {
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> empty = new HashMap<>();

        map.put("Banana", 2);
        map.put("Cherry", 3);
        map.put("Apple", 7);
        map.put("Mango", 3);
        map.put("Huy", 0);


        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(new HashMap<>())).isTrue();

        // Тестирование обязательного поля
        schema.required();
        assertFalse(schema.isValid(null), "Null should not be valid");
        assertTrue(schema.isValid(map), "The map should be valid");

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap<>())).isTrue();

        //Тестирование ограничения на размер мапы
        schema.sizeof(2);

        assertThat(schema.isValid(new HashMap<>())).isFalse();
        Map<String, String> actual1 = new HashMap<>();
        actual1.put("key1", "value1");
        assertThat(schema.isValid(actual1)).isFalse();
        actual1.put("key2", "value2");
        assertThat(schema.isValid(actual1)).isTrue();

        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required().contains("ya"));
        schemas.put("lastName", validator.string().required().contains("ov"));
        schema.shape(schemas);

        Map<String, String> actual2 = new HashMap<>();
        actual2.put("firstName", "Kolya");
        actual2.put("lastName", "Ivanov");
        assertThat(schema.isValid(actual2)).isTrue();

        Map<String, String> actual3 = new HashMap<>();
        actual3.put("firstName", "Maya");
        actual3.put("lastName", "Krasnova");
        assertThat(schema.isValid(actual3)).isTrue();

        Map<String, String> actual4 = new HashMap<>();
        actual4.put("firstName", "John");
        actual4.put("lastName", "Jones");
        //assertThat(schema.isValid(actual4)).isFalse();

        assertFalse(schema.isValid(map), "The number of key-value pairs must be equal to the specified one");
        assertFalse(schema.isValid(empty), "There are not enough elements on the map");

    }

    @Test
    public void testMapWithoutRequired() {

        // Без обязательного поля
        assertTrue(schema.isValid(null), "Null should be valid when not required");
    }

    @Test
    public void testHumanValidation() {
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
