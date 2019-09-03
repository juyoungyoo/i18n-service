package com.zoyi.i18nservice.channel.dto;

import com.zoyi.i18nservice.channel.domain.Key;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeyDto {

    private Integer id;
    private String name;

    private KeyDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static KeyDto of(Key key) {
        return new KeyDto(key.getId(), key.getName().getName());
    }

    @Override
    public String toString() {
        return "KeyResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
