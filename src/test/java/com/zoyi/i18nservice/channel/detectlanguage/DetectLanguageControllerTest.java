package com.zoyi.i18nservice.channel.detectlanguage;

import com.zoyi.i18nservice.supports.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DetectLanguageControllerTest extends BaseControllerTest {

    @DisplayName("사용한 언어를 찾아 반환한다")
    @Test
    void detect() throws Exception {
        String message = "Hello world";

        mockMvc.perform(get("/language_detect")
                .param("message", message)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.locale").value("en"));
    }
}