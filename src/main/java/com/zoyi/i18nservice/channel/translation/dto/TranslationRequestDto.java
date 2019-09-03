package com.zoyi.i18nservice.channel.translation.dto;

import com.zoyi.i18nservice.channel.translation.Translation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TranslationRequestDto {

    private Integer keyId;
    private String locale;

    public TranslationRequestDto(Integer keyId, String locale) {
        this.keyId = keyId;
        this.locale = locale;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateOrUpdate {

        private Integer keyId;

        private String locale;

        private String value;

        @Builder
        public CreateOrUpdate(Integer keyId, String locale, String value) {
            this.keyId = keyId;
            this.locale = locale;
            this.value = value;
        }

        public Translation toEntity() {
            return Translation.builder()
                    .keyId(keyId)
                    .locale(locale)
                    .value(value)
                    .build();
        }
    }
}

