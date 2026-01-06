package dev.ngb.infrastructure.jdbc.repository.event;

import dev.ngb.infrastructure.jdbc.entity.event.EventPublicationEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.ListCrudRepository;

public interface EventPublicationRepository
        extends ListCrudRepository<@NonNull EventPublicationEntity, @NonNull Long> {
}
