package dev.ngb.domain.user.repository;

import dev.ngb.domain.Repository;
import dev.ngb.domain.user.model.Otp;

import java.util.Optional;
import java.util.UUID;

public interface OtpRepository extends Repository<Otp, UUID> {
    Optional<Otp> findByUserIdAndOtpValue(UUID userId, String otpValue);
}
