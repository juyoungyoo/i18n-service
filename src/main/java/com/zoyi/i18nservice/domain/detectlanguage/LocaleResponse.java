package com.zoyi.i18nservice.domain.detectlanguage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LocaleResponse {

    private Locale locale;

    static LocaleResponse of(String language) {
        return new LocaleResponse(new Locale(language));
    }
}
