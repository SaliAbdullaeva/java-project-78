package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public class StringSchema {
    private boolean required = false;
    private Integer minLength = null;
    private List<String> contains = new ArrayList<>();

    public StringSchema required() {
        this.required = true;
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

    public boolean isValid(String data) {
        if (required) {
            if (data == null || data.isEmpty()) {
                return false;
            }
        }

        if (minLength != null && (data == null || data.length() < minLength)) {
            return false;
        }

        for (String substring : contains) {
            if (data == null || !data.contains(substring)) {
                return false;
            }
        }

        return true;
    }
}
