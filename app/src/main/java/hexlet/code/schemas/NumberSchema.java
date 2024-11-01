package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Number> {
    private boolean positive = false;
    private Double minValue = null;
    private Double maxValue = null;

    // добавляет в схему ограничение, которое не позволяет использовать null в качестве значения
    public NumberSchema required() {
        super.required(); // вызываем метод из базового класса
        return this;
    }

    // добавляет ограничение на знак числа.
    public NumberSchema positive() {
        this.positive = true;
        return this;
    }

    // добавляет допустимый диапазон, в который должно попадать значение числа включая границы
    public NumberSchema range(double min, double max) {
        this.minValue = min;
        this.maxValue = max;
        return this;
    }

    @Override
    public boolean isValid(Number value) {
        if (!super.isValid(value)) {
            return false; // Проверка на обязательное поле
        }

        // Если значение null и поле не обязательно
        if (value == null) {
            return true;
        }
        double numberValue = value.doubleValue();

        // Проверка на положительное значение
        if (positive && numberValue <= 0) {
            return false;
        }

        // Проверка на диапазон
        if (minValue != null && numberValue < minValue) {
            return false;
        }

        if (maxValue != null && numberValue > maxValue) {
            return false;
        }

        return true; // Если все проверки пройдены, возвращаем true
    }
}
