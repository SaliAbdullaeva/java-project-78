package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map> {
    Integer size;

    public MapSchema required() {
        super.required();
        return this;
    }

    public MapSchema sizeof(int newSize) {
        this.size = newSize;
        return this;
    }

    @Override
    public boolean isValid(Map map) {

        // Если карта равна null и не обязательна, возвращаем true
        if (map == null) {
            return !required;
        }

        // Проверяем базовую валидацию
        if (!super.isValid(map)) {
            return false; // Если базовая валидация не прошла, возвращаем false
        }

        // Если размер не установлен, возвращаем true
        if (size == null) {
            return true;
        }

            // Проверяем, что размер карты соответствует ожидаемому
        return map.size() == size;
    }

}
