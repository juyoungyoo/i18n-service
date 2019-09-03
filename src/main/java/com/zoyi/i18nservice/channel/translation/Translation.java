package com.zoyi.i18nservice.channel.translation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zoyi.i18nservice.channel.keys.Key;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.*;
import org.springframework.context.annotation.Import;

import javax.persistence.*;

@Table(name = "TB_TRANSLATION")
@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Translation {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "key_id")
    private Key key;

    @Column(nullable = false)
    private String locale;

    @Column(nullable = false)
    private String value;

    @Builder
    public Translation(Key key, String locale, String value) {
        this.key = key;
        this.locale = locale;
        this.value = value;
    }

    void update(String value) {
        this.value = value;
    }

    @JsonIgnore
    public Integer getKey() {
        return key.getId();
    }
}