package dev.ngb.application.command;

import dev.ngb.domain.DomainEntity;

/**
 * Generic result object containing an identifier produced by a command.
 * <p>
 * Commonly used for commands that create or persist a new entity and
 * need to return its generated identifier.
 *
 * @param <ID>  the type of the identifier
 * @param value the generated or resulting identifier
 */
public record IdResult<ID>(
        ID id
) {
    public static <ID> IdResult<ID> of(ID id) {
        return new IdResult<>(id);
    }

    public static <ID> IdResult<ID> from(DomainEntity<ID> entity) {
        return IdResult.of(entity.getId());
    }
}
