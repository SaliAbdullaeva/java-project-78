package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public class StringSchema extends BaseSchema<String> {
    private int minLength = 0;
    private int maxLength = Integer.MAX_VALUE;
    private List<String> contains = new ArrayList<>(); // Добавляем список для подстрок

    public StringSchema required() {
        super.required(); // вызываем метод из базового класса
        return this;
    }

    public StringSchema minLength(int length) {
        this.minLength = length;
        return this;
    }

    public StringSchema contains(String substring) {
        this.contains.add(substring);
        return this;
    }

    @Override
    public boolean isValid(String value) {
        if (!super.isValid(value)) {
            return false; // Проверка на обязательное поле
        }

        // Если значение null и поле не обязательно
        if (value == null) {
            return true;
        }

        // Проверка на длину строки
        if (value.length() < minLength) {
            return false;
        }

        if (value.length() > maxLength) {
            return false;
        }

        // Проверка на наличие подстрок
        for (String substring : contains) {
            if (!value.contains(substring)) {
                return false; // Если строка содержит подстроку, возвращаем false
            }
        }
        return true; // Если все проверки пройдены, возвращаем true
    }
}
