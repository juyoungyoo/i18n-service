package com.zoyi.i18nservice.domain.translation;

import com.zoyi.i18nservice.domain.keys.Key;
import com.zoyi.i18nservice.domain.keys.KeyName;
import com.zoyi.i18nservice.domain.keys.KeyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TranslationRepositoryTest {

    private Key FIXTURE_KEY;

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private TranslationRepository translationRepository;

    @BeforeEach
    void setUp() {
        translationRepository.deleteAll();
        keyRepository.deleteAll();
        FIXTURE_KEY = keyRepository.save(Key.of(KeyName.of("test")));

        save("en", "Hi");
        save("ko", "안녕하세요");
    }

    @DisplayName("키의 번역데이터 2개를 반환한다")
    @Test
    void findAllByKeyId() {
        List<Translation> result = translationRepository.findAllByKeyId(FIXTURE_KEY.getId());

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Translation::getLocale).containsExactly("en", "ko");
    }

    @DisplayName("키의 특정 언어 번역 데이터 1개를 반환한다")
    @Test
    void findByKeyIdAndLocale() {
        String expectedLocale = "en";

        Translation result = translationRepository.findByKeyIdAndLocale(FIXTURE_KEY.getId(), expectedLocale)
                .get();

        assertThat(result.getKey()).isEqualTo(FIXTURE_KEY.getId());
        assertThat(result.getLocale()).isEqualTo(expectedLocale);
    }

    private Translation save(String locale, String value) {
        Translation translation = Translation.builder()
                .key(FIXTURE_KEY)
                .locale(locale)
                .value(value)
                .build();
        return translationRepository.save(translation);
    }
}