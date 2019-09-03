package com.zoyi.i18nservice.domain.learningtest;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.Result;
import com.detectlanguage.errors.APIError;
import com.zoyi.i18nservice.config.AppProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppProperties.class)
class DetectLanguageTest {

    @Value("${app-detect-language.api-key}")
    private String API_KEY;

    private static final Logger log = LoggerFactory.getLogger(DetectLanguageTest.class);

    @ParameterizedTest
    @CsvSource({"안녕하세요, ko",
            "Hello world, en"})
    void detectLanguage(String text, String expectedLocale) throws APIError {
        DetectLanguage.apiKey = API_KEY;

        String language = DetectLanguage.simpleDetect(text);

        log.debug("Locale ({}): {}", expectedLocale, text);

        assertThat(language).isEqualTo(expectedLocale);
    }

    @ParameterizedTest
    @CsvSource({"안녕하세요, ko",
            "Hello world, en"})
    void detectLanguages(String text, String expectedLocale) throws APIError {
        DetectLanguage.apiKey = API_KEY;

        List<Result> results = DetectLanguage.detect(text);
        Result result = results.get(0);

        log.debug("Language: " + result.language);
        log.debug("Is reliable: " + result.isReliable);
        log.debug("Confidence: " + result.confidence);

        assertThat(result.language).isEqualTo(expectedLocale);
        assertThat(result.isReliable).isTrue();
    }
}