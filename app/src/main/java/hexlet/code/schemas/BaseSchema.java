package hexlet.code.schemas;

/**
 * Базовый класс для схем.
 *
 * @param <T> тип, который будет использоваться в схемах.
 */

public class BaseSchema<T> {
    protected boolean required = false;

    public BaseSchema<T> required() {
        this.required = true;
        return this;
    }

    /**
     * Проверяет, является ли схема валидной.
     *
     * @param value значение, которое нужно проверить на валидность.
     * @return true, если схема валидна; иначе false.
     */

    public boolean isValid(T value) {
        // Проверка обязательного поля
        if (required && (value == null || "".equals(value))) {
            return false;
        }

        return true; // Если все проверки пройдены, возвращаем true
    }
}
