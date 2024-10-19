package hexlet.code.schemas;

public class BaseSchema<T> {
    protected boolean required = false;

    public BaseSchema<T> required() {
        this.required = true;
        return this;
    }

    public boolean isValid(T value) {
        // Проверка обязательного поля
        if (required && (value == null || "".equals(value))) {
            return false;
        }

        return true; // Если все проверки пройдены, возвращаем true
    }
}
