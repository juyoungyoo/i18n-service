package com.zoyi.i18nservice.channel.detectlanguage;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.errors.APIError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
public class DetectLanguageController {

    private String API_KEY = "be9af9d428732bbcb6a096a674a3b104";

    @GetMapping("/language_detect")
    public ResponseEntity detect(@RequestParam("message") String message) {
        DetectLanguage.apiKey = API_KEY;
        try {
            String detect = DetectLanguage.simpleDetect(message);
            log.debug("Input Data: {}, language: {}", message, detect);
            Locale locale = new Locale(detect);
            log.debug("change locale: {}", locale);

            return ResponseEntity.ok().body(new LocaleResponse(locale));
        } catch (APIError apiError) {
            return ResponseEntity.badRequest().body(apiError.getMessage());
        }
    }
}
