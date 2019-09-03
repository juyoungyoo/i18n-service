package com.zoyi.i18nservice.domain.translation.dto;

import com.zoyi.i18nservice.domain.translation.Translation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TranslationsResponse {

    private List<TranslationDto> translations;

    public TranslationsResponse(List<Translation> translations) {
        this.translations = translations.stream()
                .map(TranslationDto::of)
                .collect(Collectors.toList());
    }
}
