package com.zoyi.i18nservice.channel.dto;

import com.zoyi.i18nservice.channel.domain.Translation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TranslationsResponse {

    private List<Translation> translations;

    public TranslationsResponse(List<Translation> translations) {
        this.translations = translations;
    }
}
