package com.zoyi.i18nservice.domain.keys;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeyName {

    @Column(unique = true, nullable = false)
    private String name;

    private KeyName(String name) {
        this.name = name;
    }

    public static KeyName of(String name) {
        return new KeyName(name);
    }

    @Override
    public String toString() {
        return "KeyName{" +
                "name='" + name + '\'' +
                '}';
    }
}
