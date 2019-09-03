package com.zoyi.i18nservice.domain.keys;

import com.zoyi.i18nservice.domain.keys.dto.KeyDto;
import com.zoyi.i18nservice.domain.keys.dto.KeyResponse;
import com.zoyi.i18nservice.domain.translation.TranslationRepository;
import com.zoyi.i18nservice.supports.BaseControllerTest;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class KeysControllerTest extends BaseControllerTest {

    private static final String CHANNEL_URL = "/keys";
    private static final String STRING_OF_KEY_NAME = "test.key.first.one";
    private static final KeyName FIXTURE_KEY_NAME = KeyName.of(STRING_OF_KEY_NAME);

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private TranslationRepository translationRepository;

    @BeforeEach
    void setUp() {
        translationRepository.deleteAll();
        keyRepository.deleteAll();
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

    @DisplayName(STRING_OF_KEY_NAME + "를 조회한다")
    @Test
    void queryKey_whenInputKeyName_thenSearchKeyByName() throws Exception {
        KeyName request = FIXTURE_KEY_NAME;
        generateKey(FIXTURE_KEY_NAME.getName());
        generateKey(10);

        mockMvc.perform(get(CHANNEL_URL)
                .param("name", FIXTURE_KEY_NAME.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key.id").exists())
                .andExpect(jsonPath("$.key.name").value(request.getName()));
    }

    @DisplayName("키를 생성한다")
    @Test
    void createKey() throws Exception {
        KeyName request = FIXTURE_KEY_NAME;

        postResource(CHANNEL_URL, request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("key.id").exists())
                .andExpect(jsonPath("key.name").value(request.getName()));
    }

    @DisplayName("키를 수정한다")
    @Test
    void updateKey() throws Exception {
        KeyName request = FIXTURE_KEY_NAME;
        KeyDto originKey = generateKey(FIXTURE_KEY_NAME.getName());

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
                        generateKey("keyName" + num);
                    } catch (Exception ignored) { }
                });
    }

    private KeyDto generateKey(@Nullable String keyName) throws Exception {
        if (Strings.isBlank(keyName)) {
            keyName = STRING_OF_KEY_NAME;
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