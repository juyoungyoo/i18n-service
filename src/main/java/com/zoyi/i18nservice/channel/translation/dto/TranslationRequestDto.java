package com.zoyi.i18nservice.channel.translation.dto;

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
    public static class Update {

        private Integer keyId;

        private String locale;

        private String value;

        @Builder
        public Update(Integer keyId, String locale, String value) {
            this.keyId = keyId;
            this.locale = locale;
            this.value = value;
        }
    }
}

