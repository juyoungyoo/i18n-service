package com.zoyi.i18nservice.channel.keys.dto;

import com.zoyi.i18nservice.channel.keys.Key;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeysResponse {

    private List<KeyDto> keys;

    private KeysResponse(List<KeyDto> keys) {
        this.keys = keys;
    }

    public static KeysResponse of(List<Key> keys) {
        return keys.stream()
                .map(KeyDto::of)
                .collect(Collectors.collectingAndThen(toList(), KeysResponse::new));
    }
}
