package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map> {
    private Integer size;
    private Map<String, BaseSchema<String>> shapeSchemas;

    public MapSchema required() {
        super.required();
        return this;
    }

    public MapSchema sizeof(int newSize) {
        this.size = newSize;
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<String>> schemas) {
        this.shapeSchemas = schemas;
        return this;
    }

    @Override
    public boolean isValid(Map map) {

        // Если карта равна null и не обязательна, возвращаем true
        if (map == null) {
            return !required;
        }

        // Если базовая валидация не прошла, возвращаем false
        if (!super.isValid(map)) {
            return false;
        }

        // Проверяем, что размер карты соответствует ожидаемому
        if (size != null && map.size() != size) {
            return false;
        }

        // Проверяем вложенные схемы, если они заданы
        if (shapeSchemas != null) {
            // Получаем все ключи из shapeSchemas
            for (String key : shapeSchemas.keySet()) {
                BaseSchema<String> valueSchema = shapeSchemas.get(key); // Получаем значение по ключу
                Object value = map.get(key);

                // Важное изменение: вызываем isValid для каждого значения, даже если оно null
                if (!valueSchema.isValid((String) value)) {
                    return false;
                }
            }
        }
        return true; // Если все проверки пройдены, возвращаем true
    }
}
