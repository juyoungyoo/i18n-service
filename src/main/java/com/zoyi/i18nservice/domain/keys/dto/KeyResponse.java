package com.zoyi.i18nservice.domain.keys.dto;

import com.zoyi.i18nservice.domain.keys.Key;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeyResponse {

    private KeyDto key;

    private KeyResponse(KeyDto key) {
        this.key = key;
    }

    public static KeyResponse of(Key key) {
        return new KeyResponse(KeyDto.of(key));
    }
}
