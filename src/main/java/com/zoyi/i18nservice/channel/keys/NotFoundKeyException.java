package com.zoyi.i18nservice.channel.keys;

class NotFoundKeyException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "키가 존재하지 않습니다. (입력값: %d)";

    NotFoundKeyException(Integer keyId) {
        super(String.format(ERROR_MESSAGE, keyId));
    }

    NotFoundKeyException(KeyName name) {
        super(String.format(ERROR_MESSAGE,name));
    }
}
