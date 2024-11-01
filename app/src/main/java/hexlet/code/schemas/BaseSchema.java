package hexlet.code.schemas;

/**
 * Базовый класс для схем.
 *
 * @param <T> тип, который будет использоваться в схемах.
 */

public class BaseSchema<T> {
    protected boolean required = false;

    /**
     * Указывает, требуется ли этому схеме значение.
     *
     * <p>Подклассы должны гарантировать, что переопределенный метод
     * поддерживает контракт, установленный этим методом. В частности, если
     * этот метод возвращает true, подкласс должен убедиться, что значение
     * проверяется соответствующим образом.</p>
     *
     * @return true, если схеме требуется значение; false в противном случае
     */

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
