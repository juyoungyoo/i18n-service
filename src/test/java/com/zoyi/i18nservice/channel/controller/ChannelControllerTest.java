package com.zoyi.i18nservice.channel.controller;

import com.zoyi.i18nservice.channel.domain.KeyName;
import com.zoyi.i18nservice.channel.domain.Translation;
import com.zoyi.i18nservice.channel.dto.KeyDto;
import com.zoyi.i18nservice.channel.dto.KeyResponse;
import com.zoyi.i18nservice.channel.repository.KeyRepository;
import com.zoyi.i18nservice.channel.repository.TranslationRepository;
import com.zoyi.i18nservice.supports.BaseControllerTest;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChannelControllerTest extends BaseControllerTest {

    private static final String CHANNEL_URL = "/keys";
    private static final String TEST_KEY_NAME = "test.key.first.one";

    @Autowired
    private KeyRepository keyRepository;
    @Autowired
    private TranslationRepository translationRepository;

    @BeforeEach
    void setUp() {
        keyRepository.deleteAll();
        translationRepository.deleteAll();
    }

    @DisplayName("키의 특정 언어 번역 데이터를 수정한다")
    @Test
    void updateTranslation() throws Exception {
        Integer keyId = generateKey(null).getId();
        String expectedLocale = "en";
        String expectedValue = "update Hi";
        generateTranslation(keyId, expectedLocale, "Hi");

        String url = CHANNEL_URL + "/{keyId}/translations/{locale}";
        mockMvc.perform(put(url, keyId, expectedLocale)
                        .content(expectedValue))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.translation.id").exists())
                .andExpect(jsonPath("$.translation.keyId").value(keyId))
                .andExpect(jsonPath("$.translation.locale").value(expectedLocale))
                .andExpect(jsonPath("$.translation.value").value(expectedValue));
    }

    @DisplayName("키의 특정 언어 번역한다")
    @Test
    void searchTranslation() throws Exception {
        Integer keyId = generateKey(null).getId();
        String expectedLocale = "en";
        generateTranslation(keyId, expectedLocale, "Hi");
        generateTranslation(keyId, "ko", "안녕하세요");

        String url = CHANNEL_URL + "/{keyId}/translations/{locale}";
        mockMvc.perform(get(url, keyId, expectedLocale))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.translation.id").exists())
                .andExpect(jsonPath("$.translation.keyId").value(keyId))
                .andExpect(jsonPath("$.translation.locale").value(expectedLocale));
    }

    @DisplayName("키의 모든 번역 확인한다")
    @Test
    void searchTranslations() throws Exception {
        String url = CHANNEL_URL + "/{keyId}/translations";
        KeyDto keyDto = generateKey(null);
        Integer keyId = keyDto.getId();
        generateTranslation(keyId, "en", "Hi");
        generateTranslation(keyId, "ko", "안녕하세요");

        mockMvc.perform(get(url, keyId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.translations.length()").value(2))
                .andExpect(jsonPath("$.translations[0].id").exists())
                .andExpect(jsonPath("$.translations[0].keyId").value(keyId))
                .andExpect(jsonPath("$.translations[0].locale").value("en"))
                .andExpect(jsonPath("$.translations[0].value").value("Hi"));
    }

    private Translation generateTranslation(Integer keyId, String locale, String value) {
        Translation translation = Translation.builder()
                .keyId(keyId)
                .locale(locale)
                .value(value)
                .build();
        return translationRepository.save(translation);
    }

    @DisplayName("번역 추가하기")
    @Test
    void createTranslation() throws Exception {
        Integer expectedKeyId = 1;
        String expectedLocale = "en";
        String expectedValue = "Hi";

        String url = CHANNEL_URL + "/{keyId}/translations/{locale}";
        mockMvc.perform(post(url, expectedKeyId, expectedLocale)
                .content(expectedValue))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.translation.id").exists())
                .andExpect(jsonPath("$.translation.keyId").value(expectedKeyId))
                .andExpect(jsonPath("$.translation.locale").value(expectedLocale))
                .andExpect(jsonPath("$.translation.value").value(expectedValue));
    }

    @DisplayName("모든 키를 가져온다")
    @Test
    void queryKey_whenNoInput_thenAllKeys() throws Exception {
        generateKey(10);

        mockMvc.perform(get(CHANNEL_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keys.length()").value(10));
    }

    @DisplayName(TEST_KEY_NAME + "를 조회한다")
    @Test
    void queryKey_whenInputKeyName_thenSearchKeyByName() throws Exception {
        generateKey(10);
        generateKey(null);
        KeyName request = KeyName.of(TEST_KEY_NAME);

        mockMvc.perform(get(CHANNEL_URL)
                .param("name", TEST_KEY_NAME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key.id").exists())
                .andExpect(jsonPath("$.key.name").value(request.getName()));
    }

    @DisplayName("키를 생성한다")
    @Test
    void createKey() throws Exception {
        KeyName request = KeyName.of("test.key.first.one");

        postResource(CHANNEL_URL, request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("key.id").exists())
                .andExpect(jsonPath("key.name").value(request.getName()));
    }

    @DisplayName("키를 수정한다")
    @Test
    void updateKey() throws Exception {
        KeyDto originKey = generateKey(null);
        KeyName request = KeyName.of("test.key.revised.first.one");

        putResource(CHANNEL_URL + "/" + originKey.getId(), request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("key.id").value(originKey.getId()))
                .andExpect(jsonPath("key.name").value(request.getName()));
    }

    private void generateKey(int count) {
        IntStream.range(0, count)
                .forEach((num) -> {
                    try {
                        generateKey("key" + num);
                    } catch (Exception ignored) {
                    }
                });
    }

    KeyDto generateKey(@Nullable String keyName) throws Exception {
        if (Strings.isBlank(keyName)) {
            keyName = TEST_KEY_NAME;
        }
        KeyName request = KeyName.of(keyName);

        String responseBody = postResource(CHANNEL_URL, request)
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        KeyResponse keyResponse = objectMapper.readValue(responseBody, KeyResponse.class);
        return keyResponse.getKey();
    }
}