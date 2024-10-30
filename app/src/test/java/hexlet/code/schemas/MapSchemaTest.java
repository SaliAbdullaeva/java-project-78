package hexlet.code.schemas;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;
import java.util.HashMap;

public final class MapSchemaTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = new Validator();
    }

    @Test
    @DisplayName("'required' rule works correctly")
    public void testRequired() {
        MapSchema schema = validator.map();
        assertThat(schema.isValid(null)).isTrue();

        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap<>())).isTrue();
    }

    @Test
    @DisplayName("'sizeof' rule works correctly")
    public void testSizeof() {
        MapSchema schema = validator.map();
        final int testSize1 = 1;
        final int testSize2 = 2;


        Map<String, String> testMap1 = Map.of("key", "value");
        Map<String, String> testMap2 = Map.of(
                "key", "value",
                "another key", "another value"
        );

        schema.required().sizeof(testSize2).sizeof(testSize1);

        assertThat(schema.isValid(testMap1)).isTrue();
        assertThat(schema.isValid(testMap2)).isFalse();

        schema.required().sizeof(testSize1).sizeof(testSize2);

        assertThat(schema.isValid(testMap1)).isFalse();
        assertThat(schema.isValid(testMap2)).isTrue();
    }

    @Test
    @DisplayName("'shape' rule works correctly")
    public void testShape() {
        MapSchema schema = validator.map();

        // Схема для проверки каждого ключа
        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        // Устанавливаем схему
        schema.shape(schemas);

        // Создаем тестовые карты
        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith"); // Валидно

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null); // Невалидно

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B"); // Невалидно (должно быть минимум 2 символа)

        Map<String, String> human4 = new HashMap<>();
        human4.put("firstName", "John");
        human4.put("lastName", "Smith"); // Валидно

        // Проверяем валидность карт
        assertThat(schema.isValid(human1)).isTrue(); // должно быть true
        assertThat(schema.isValid(human2)).isFalse(); // должно быть false
        assertThat(schema.isValid(human3)).isFalse(); // должно быть false
        assertThat(schema.isValid(human4)).isTrue(); // должно быть true
    }

    @Test
    @DisplayName("Rules in different order return the same result")
    public void testRuleComposition() {
        MapSchema schema1 = validator.map();
        MapSchema schema2 = validator.map();
        MapSchema schema3 = validator.map();
        final int testSize = 2;

        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));

        Map<String, String> testMap1 = new HashMap<>();
        testMap1.put("firstName", "John");
        testMap1.put("lastName", "Smith");

        Map<String, String> testMap2 = new HashMap<>(testMap1);
        testMap2.put("occupation", "DevOps");

        schema1.required().sizeof(testSize).shape(schemas);
        schema2.sizeof(testSize).required().shape(schemas);
        schema3.sizeof(testSize).shape(schemas).required();

        // When true
        assertThat(schema1.isValid(testMap1)).isEqualTo(schema2.isValid(testMap1));
        assertThat(schema2.isValid(testMap1)).isEqualTo(schema3.isValid(testMap1));

        // When false
        assertThat(schema1.isValid(testMap2)).isEqualTo(schema2.isValid(testMap2));
        assertThat(schema2.isValid(testMap2)).isEqualTo(schema3.isValid(testMap2));
    }
}
