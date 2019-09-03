package com.zoyi.i18nservice.channel.detectlanguage;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.errors.APIError;
import com.zoyi.i18nservice.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DetectLanguageController {

    private final AppProperties appSecurityProperties;

    public DetectLanguageController(AppProperties appSecurityProperties) {
        this.appSecurityProperties = appSecurityProperties;
    }

    @GetMapping("/language_detect")
    public ResponseEntity detect(@RequestParam("message") String message) {
        DetectLanguage.apiKey = appSecurityProperties.getApiKey();
        try {
            String language = DetectLanguage.simpleDetect(message);
            log.debug("Language: {}, Message: {}", language, message);
            return ResponseEntity.ok().body(LocaleResponse.of(language));
        } catch (APIError apiError) {
            return ResponseEntity.badRequest().body(apiError.getMessage());
        }
    }
}
