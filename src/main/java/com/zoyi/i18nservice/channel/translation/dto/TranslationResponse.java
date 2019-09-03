package com.zoyi.i18nservice.channel.translation.dto;

import com.zoyi.i18nservice.channel.translation.Translation;
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
