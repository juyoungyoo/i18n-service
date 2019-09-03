package com.zoyi.i18nservice.channel.test;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.Result;
import com.detectlanguage.errors.APIError;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DetectLanguageTest {


    private static final Logger log = LoggerFactory.getLogger(DetectLanguageTest.class);

    @ParameterizedTest
    @CsvSource({"안녕하세요, ko",
            "Hello world, en"})
    void detectLanguage(String text, String expectedLocale) throws APIError {
        DetectLanguage.apiKey = "be9af9d428732bbcb6a096a674a3b104";

        String language = DetectLanguage.simpleDetect(text);

        log.debug("Locale ({}): {}", expectedLocale, text);

        assertThat(language).isEqualTo(expectedLocale);
    }

    @ParameterizedTest
    @CsvSource({"안녕하세요, ko",
            "Hello world, en"})
    void detectLanguages(String text, String expectedLocale) throws APIError {
        DetectLanguage.apiKey = "be9af9d428732bbcb6a096a674a3b104";

        List<Result> results = DetectLanguage.detect(text);
        Result result = results.get(0);

        log.debug("Language: " + result.language);
        log.debug("Is reliable: " + result.isReliable);
        log.debug("Confidence: " + result.confidence);

        assertThat(result.language).isEqualTo(expectedLocale);
        assertThat(result.isReliable).isTrue();
    }
}
