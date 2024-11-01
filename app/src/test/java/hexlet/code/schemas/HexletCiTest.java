package hexlet.code.schemas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import hexlet.code.Validator;

class HexletCiTest {

    @Test
    public void testStringValidator() {
        var v = new Validator();
        var schema = v.string();

        assertTrue(schema.isValid(""), "An empty string should be valid (by default)");


        schema.required(); // Устанавливаем, что строка является обязательной
        assertThat(schema.isValid("what does the fox say")).isTrue(); // Проверяем, что непустая строка валидна
        assertThat(schema.isValid("hexlet")).isTrue(); // и тут
        assertThat(schema.isValid("")).isFalse(); // Проверяем, что пустая строка больше не валидна
        assertThat(schema.isValid(null)).isFalse(); // Проверяем, что null не является валидным значением

        schema.minLength(7); // Устанавливаем минимальную длину строки в 7 символов
        assertThat(schema.isValid("what does the fox say")).isTrue(); // Проверяем, что строка длиной 22 символа валидна
        assertThat(schema.isValid("hexlet")).isFalse(); // Проверяем, что строка длиной 6 символов не валидна

        assertThat(schema.contains("what").isValid("what does the fox say")).isTrue();

        // 1 Проверяем, что строка не содержит подстроку "whatthe"
        assertThat(schema.contains("whatthe").isValid("what does the fox say")).isFalse();

        var schema1 = v.string().required().minLength(10).minLength(4); // Создаем новую схему с несколькими условиями
        // Проверяем, что строка длиной 6 символов валидна (что яв-ся ошибкой, так как мин. длина должна быть 10)
        assertThat(schema1.isValid("hexlet")).isTrue();
    }

    @Test
    public void testNumberValidator() {
        var v = new Validator();
        var schema = v.number();

        assertThat(schema.isValid(5)).isTrue();
        assertThat(schema.isValid(null)).isTrue();

        schema.positive();
        assertThat(schema.isValid(null)).isTrue();

        schema.required();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(-10)).isFalse();
        assertThat(schema.isValid(0)).isFalse();
        assertThat(schema.isValid(10)).isTrue();

        schema.range(5, 10);
        assertThat(schema.isValid(5)).isTrue();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(4)).isFalse();
        assertThat(schema.isValid(11)).isFalse();

        schema.range(6, 9);
        assertThat(schema.isValid(5)).isFalse();
        assertThat(schema.isValid(10)).isFalse();
    }

    @Test
    public void testMapValidator() {
        var v = new Validator();
        var schema = v.map();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(new HashMap<>())).isTrue();

        schema.required();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap<>())).isTrue();

        schema.sizeof(2);
        assertThat(schema.isValid(new HashMap<>())).isFalse();
        Map<String, String> actual1 = new HashMap<>();
        actual1.put("key1", "value1");
        assertThat(schema.isValid(actual1)).isFalse();
        actual1.put("key2", "value2");
        assertThat(schema.isValid(actual1)).isTrue();

        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", v.string().required().contains("ya"));
        schemas.put("lastName", v.string().required().contains("ov"));
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
        assertThat(schema.isValid(actual4)).isFalse(); //2
    }
}

