package com.zoyi.i18nservice.domain.translation.dto;

import com.zoyi.i18nservice.domain.translation.Translation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TranslationResponse {

    private TranslationDto translation;

    public TranslationResponse(Translation translation) {
        this.translation = TranslationDto.of(translation);
    }
}
