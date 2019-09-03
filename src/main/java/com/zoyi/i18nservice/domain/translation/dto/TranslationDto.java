package com.zoyi.i18nservice.domain.translation.dto;

import com.zoyi.i18nservice.domain.translation.Translation;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TranslationDto {

    private Integer id;

    private Integer keyId;

    private String locale;

    private String value;

    @Builder
    private TranslationDto(Integer id, Integer keyId, String locale, String value) {
        this.id = id;
        this.keyId = keyId;
        this.locale = locale;
        this.value = value;
    }

    public static TranslationDto of(Translation translation) {
        return TranslationDto.builder()
                .id(translation.getId())
                .keyId(translation.getKey())
                .locale(translation.getLocale())
                .value(translation.getValue())
                .build();
    }
}
