package dev.ngb.util.validate;

/**
 * @param constraint optional (max length, regex, range, enum values...)
 * @param message    optional (for debug / fallback)
 */
public record ValidationError(String field, ValidationErrorType type, Object constraint, String message) {

    public static ValidationError required(String field) {
        return new ValidationError(
                field,
                ValidationErrorType.REQUIRED,
                null,
                field + " must not be blank"
        );
    }

    public static ValidationError maxLength(String field, int max) {
        return new ValidationError(
                field,
                ValidationErrorType.MAX_LENGTH,
                max,
                field + " must not exceed " + max + " characters"
        );
    }

    public static ValidationError pattern(String field, String regex) {
        return new ValidationError(
                field,
                ValidationErrorType.PATTERN,
                regex,
                field + " has invalid format"
        );
    }

    public static ValidationError nullNotAllowed(String field) {
        return new ValidationError(
                field,
                ValidationErrorType.NULL_NOT_ALLOWED,
                null,
                field + " must not be null"
        );
    }

    public static ValidationError custom(String field, ValidationErrorType type, String constraint, String message) {
        return new ValidationError(field, type, constraint, message);
    }
}
