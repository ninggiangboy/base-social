package dev.ngb.util.validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ValidationErrors {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final List<ValidationError> errors = new ArrayList<>();

    public void checkNotBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            errors.add(ValidationError.required(field));
        }
    }

    public void checkMaxLength(String value, int max, String field) {
        if (value != null && value.length() > max) {
            errors.add(ValidationError.maxLength(field, max));
        }
    }

    public void checkPattern(String value, Pattern pattern, String field) {
        if (value != null && !pattern.matcher(value).matches()) {
            errors.add(ValidationError.pattern(field, pattern.pattern()));
        }
    }

    public void checkNotNull(Object value, String field) {
        if (value == null) {
            errors.add(ValidationError.nullNotAllowed(field));
        }
    }

    public void checkEmail(String value, String field) {
        if (value != null && !EMAIL_PATTERN.matcher(value).matches()) {
            errors.add(ValidationError.custom(
                    field,
                    ValidationErrorType.PATTERN, EMAIL_PATTERN.pattern(),
                    field + " has invalid email format"
            ));
        }
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public Map<String, Object> getExDetails() {
        return Map.of("errors", getErrors());
    }
}
