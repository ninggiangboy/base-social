package dev.ngb.domain.user.error;

import dev.ngb.domain.DomainError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserError implements DomainError {
    INVALID_DATA("Invalid tenant data"),
    BAD_CREDENTIALS("Bad credentials"),
    INVALID_CREDENTIALS_TOKEN("Invalid or expired credentials token"),
    USER_DISABLED("User disabled"),
    NEED_CHANGE_PASSWORD("Need change password"),
    DONT_NEED_CHANGE_PASSWORD("Don't change password"),
    ;

    private final String message;
}
