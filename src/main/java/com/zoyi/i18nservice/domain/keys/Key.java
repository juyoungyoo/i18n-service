package com.zoyi.i18nservice.domain.keys;

import lombok.*;

import javax.persistence.*;

@Table(name = "TB_KEYS")
@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Key {

    @Id
    @GeneratedValue
    @Column(name = "key_id")
    private Integer id;

    @Embedded
    private KeyName name;

    public static Key of(KeyName name) {
        return new Key(null, name);
    }

    void update(KeyName name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Key{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
