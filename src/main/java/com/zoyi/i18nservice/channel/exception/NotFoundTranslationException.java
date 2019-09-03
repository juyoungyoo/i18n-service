package com.zoyi.i18nservice.channel.exception;

public class NotFoundTranslationException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "%s의 번역 데이터가 존재하지 않습니다. (key 값 : %d)";

    public NotFoundTranslationException(Integer keyId, String locale) {
        super(String.format(ERROR_MESSAGE, keyId, locale));
    }
}
