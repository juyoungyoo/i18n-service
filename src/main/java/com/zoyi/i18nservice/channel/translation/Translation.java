package com.zoyi.i18nservice.channel.translation;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@EqualsAndHashCode(of = "id")

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Translation {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer keyId;

    @Column(nullable = false)
    private String locale;

    @Column(nullable = false)
    private String value;

    @Builder
    public Translation(Integer keyId, String locale, String value) {
        this.keyId = keyId;
        this.locale = locale;
        this.value = value;
    }

    public void update(Translation translation) {
        this.value = translation.value;
    }
}