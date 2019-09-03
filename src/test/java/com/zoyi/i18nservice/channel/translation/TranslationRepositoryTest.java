package com.zoyi.i18nservice.channel.translation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class TranslationRepositoryTest {

    private final Integer FIXTURE_KEY_ID = 1;

    @Autowired
    private TranslationRepository translationRepository;

    @BeforeEach
    void setUp() {
        translationRepository.deleteAll();
        save(FIXTURE_KEY_ID, "en", "Hi");
        save(FIXTURE_KEY_ID, "ko", "안녕하세요");
        save(2, "en", "My name is");
    }

    @DisplayName("키의 번역데이터 2개를 반환한다")
    @Test
    void findAllByKeyId() {
        List<Translation> result = translationRepository.findAllByKeyId(FIXTURE_KEY_ID);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Translation::getLocale).containsExactly("en", "ko");
    }

    @DisplayName("키의 특정 언어 번역 데이터 1개를 반환한다")
    @Test
    void findByKeyIdAndLocale() {
        String expectedLocale = "en";

        Translation result = translationRepository.findByKeyIdAndLocale(FIXTURE_KEY_ID, expectedLocale)
                .get();

        assertThat(result.getKeyId()).isEqualTo(FIXTURE_KEY_ID);
        assertThat(result.getLocale()).isEqualTo(expectedLocale);
    }

    private Translation save(Integer keyId, String locale, String value) {
        Translation translation = Translation.builder()
                .keyId(keyId)
                .locale(locale)
                .value(value)
                .build();
        return translationRepository.save(translation);
    }
}