package com.zoyi.i18nservice.channel.translation;

import com.zoyi.i18nservice.channel.keys.Key;
import com.zoyi.i18nservice.channel.keys.KeyName;
import com.zoyi.i18nservice.channel.keys.KeyRepository;
import com.zoyi.i18nservice.supports.BaseControllerTest;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TranslationControllerTest extends BaseControllerTest {

    private static final String DEFAULT_URL = "/keys/{keyId}/translations";
    private static final String URL_WITH_LOCALE = "/keys/{keyId}/translations/{locale}";

    private static final String TEST_KEY_NAME = "test.key.first.one";
    private Integer fixtureOfKeyId;

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private TranslationRepository translationRepository;

    @BeforeEach
    void setUp() throws Exception {
        keyRepository.deleteAll();
        fixtureOfKeyId = generateKey(Strings.EMPTY).getId();

        translationRepository.deleteAll();
    }

    @DisplayName("키의 특정 언어 번역 데이터를 수정한다")
    @Test
    void updateTranslation() throws Exception {
        Integer expectedKeyId = fixtureOfKeyId;
        String expectedLocale = "en";
        String expectedValue = "update Hi";
        generateTranslation(expectedKeyId, expectedLocale, "Hi");

        mockMvc.perform(put(URL_WITH_LOCALE, expectedKeyId, expectedLocale)
                        .content(expectedValue))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.translation.id").exists())
                .andExpect(jsonPath("$.translation.keyId").value(expectedKeyId))
                .andExpect(jsonPath("$.translation.locale").value(expectedLocale))
                .andExpect(jsonPath("$.translation.value").value(expectedValue));
    }

    @DisplayName("키의 특정 언어 번역한다")
    @Test
    void searchTranslation() throws Exception {
        Integer expectedKeyId = fixtureOfKeyId;
        String expectedLocale = "en";
        generateTranslation(expectedKeyId, expectedLocale, "Hi");
        generateTranslation(expectedKeyId, "ko", "안녕하세요");

        mockMvc.perform(get(URL_WITH_LOCALE, expectedKeyId, expectedLocale))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.translation.id").exists())
                .andExpect(jsonPath("$.translation.keyId").value(expectedKeyId))
                .andExpect(jsonPath("$.translation.locale").value(expectedLocale));
    }

    @DisplayName("키의 모든 번역 확인한다")
    @Test
    void searchTranslations() throws Exception {
        Integer expectedKeyId = fixtureOfKeyId;
        generateTranslation(expectedKeyId, "en", "Hi");
        generateTranslation(expectedKeyId, "ko", "안녕하세요");

        mockMvc.perform(get(DEFAULT_URL, expectedKeyId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.translations.length()").value(2))
                .andExpect(jsonPath("$.translations[0].id").exists())
                .andExpect(jsonPath("$.translations[0].keyId").value(expectedKeyId))
                .andExpect(jsonPath("$.translations[0].locale").value("en"))
                .andExpect(jsonPath("$.translations[0].value").value("Hi"));
    }

    @DisplayName("번역 추가하기")
    @Test
    void createTranslation() throws Exception {
        Integer expectedKeyId = 1;
        String expectedLocale = "en";
        String expectedValue = "Hi";

        mockMvc.perform(post(URL_WITH_LOCALE, expectedKeyId, expectedLocale)
                .content(expectedValue))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.translation.id").exists())
                .andExpect(jsonPath("$.translation.keyId").value(expectedKeyId))
                .andExpect(jsonPath("$.translation.locale").value(expectedLocale))
                .andExpect(jsonPath("$.translation.value").value(expectedValue));
    }

    private Translation generateTranslation(Integer keyId, String locale, String value) {
        Translation translation = Translation.builder()
                .keyId(keyId)
                .locale(locale)
                .value(value)
                .build();
        return translationRepository.save(translation);
    }

    private Key generateKey(@Nullable String keyName) throws Exception {
        if (Strings.isBlank(keyName)) {
            keyName = TEST_KEY_NAME;
        }
        KeyName request = KeyName.of(keyName);
        return keyRepository.save(Key.of(request));
    }
}