package dev.ngb.domain.tenant.model;

import dev.ngb.domain.DomainEntity;
import lombok.Getter;

import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

@Getter
public class TenantPreferenceVersion extends DomainEntity<Long> {

    private static final int INITIAL_VERSION = 1;
    private Long tenantId;
    private ZoneId timeZone;
    private Locale locale;
    private String uiLanguage;
    private String dateFormat;
    private String timeFormat;
    private Currency currency;
    private RoundingMode roundingMode;
    private DayOfWeek firstDayOfWeek;
    private String primaryThemeColor;
    private String secondaryThemeColor;
    private String logoUrl;
    private Integer preferenceVersion;

    private TenantPreferenceVersion() {
    }

    public static TenantPreferenceVersion reconstruct(
            Long id,
            Long tenantId,
            ZoneId timeZone,
            Locale locale,
            String uiLanguage,
            String dateFormat,
            String timeFormat,
            Currency currency,
            RoundingMode roundingMode,
            DayOfWeek firstDayOfWeek,
            String primaryThemeColor,
            String secondaryThemeColor,
            String logoUrl,
            Integer preferenceVersion,
            Integer lockVersion,
            UUID createdBy,
            UUID updatedBy,
            Instant createdAt,
            Instant updatedAt
    ) {
        TenantPreferenceVersion preference = new TenantPreferenceVersion();
        preference.id = id;
        preference.lockVersion = lockVersion;
        preference.createdBy = createdBy;
        preference.updatedBy = updatedBy;
        preference.createdAt = createdAt;
        preference.updatedAt = updatedAt;
        preference.tenantId = tenantId;
        preference.applyFields(timeZone, locale, uiLanguage, dateFormat, timeFormat, currency, roundingMode, firstDayOfWeek, primaryThemeColor, secondaryThemeColor, logoUrl);
        preference.preferenceVersion = preferenceVersion;
        return preference;
    }

    public static TenantPreferenceVersion create(
            Long tenantId,
            ZoneId timeZone,
            Locale locale,
            String uiLanguage,
            String dateFormat,
            String timeFormat,
            Currency currency,
            RoundingMode roundingMode,
            DayOfWeek firstDayOfWeek,
            String primaryThemeColor,
            String secondaryThemeColor,
            String logoUrl
    ) {
        TenantPreferenceVersion preference = new TenantPreferenceVersion();
        preference.tenantId = tenantId;
        preference.applyFields(timeZone, locale, uiLanguage, dateFormat, timeFormat, currency, roundingMode, firstDayOfWeek, primaryThemeColor, secondaryThemeColor, logoUrl);
        preference.preferenceVersion = INITIAL_VERSION;
        return preference;
    }

    public static TenantPreferenceVersion createNextVersion(
            TenantPreferenceVersion existing,
            ZoneId timeZone,
            Locale locale,
            String uiLanguage,
            String dateFormat,
            String timeFormat,
            Currency currency,
            RoundingMode roundingMode,
            DayOfWeek firstDayOfWeek,
            String primaryThemeColor,
            String secondaryThemeColor,
            String logoUrl) {
        TenantPreferenceVersion preference = new TenantPreferenceVersion();
        preference.tenantId = existing.getTenantId();
        preference.applyFields(timeZone, locale, uiLanguage, dateFormat, timeFormat, currency, roundingMode, firstDayOfWeek, primaryThemeColor, secondaryThemeColor, logoUrl);
        preference.preferenceVersion = existing.getPreferenceVersion() + 1;
        return preference;
    }

    private void applyFields(
            ZoneId timeZone,
            Locale locale,
            String uiLanguage,
            String dateFormat,
            String timeFormat,
            Currency currency,
            RoundingMode roundingMode,
            DayOfWeek firstDayOfWeek,
            String primaryThemeColor,
            String secondaryThemeColor,
            String logoUrl) {
        this.timeZone = timeZone;
        this.locale = locale;
        this.uiLanguage = uiLanguage;
        this.dateFormat = dateFormat;
        this.timeFormat = timeFormat;
        this.currency = currency;
        this.roundingMode = roundingMode;
        this.firstDayOfWeek = firstDayOfWeek;
        this.primaryThemeColor = primaryThemeColor;
        this.secondaryThemeColor = secondaryThemeColor;
        this.logoUrl = logoUrl;
    }
}
