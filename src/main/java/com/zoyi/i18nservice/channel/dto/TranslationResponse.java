package com.zoyi.i18nservice.channel.dto;

import com.zoyi.i18nservice.channel.domain.Translation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TranslationResponse {

    private Translation translation;

    public TranslationResponse(Translation translation) {
        this.translation = translation;
    }
}
