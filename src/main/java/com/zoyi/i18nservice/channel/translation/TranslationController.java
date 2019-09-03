package com.zoyi.i18nservice.channel.translation;

import com.zoyi.i18nservice.channel.translation.dto.TranslationRequestDto;
import com.zoyi.i18nservice.channel.translation.dto.TranslationResponse;
import com.zoyi.i18nservice.channel.translation.dto.TranslationsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keys/{keyId}/translations")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping
    public ResponseEntity searchTranslation(@PathVariable Integer keyId) {
        List<Translation> translations = translationService.searchTranslations(keyId);
        return ResponseEntity.ok(new TranslationsResponse(translations));
    }

    @GetMapping("/{locale}")
    public ResponseEntity searchTranslation(@PathVariable Integer keyId,
                                            @PathVariable String locale) {
        TranslationRequestDto translationRequestDto = new TranslationRequestDto(keyId, locale);
        Translation translation = translationService.searchTranslation(translationRequestDto);
        return ResponseEntity.ok(new TranslationResponse(translation));
    }

    @PutMapping("/{locale}")
    public ResponseEntity updateTranslation(@PathVariable Integer keyId,
                                            @PathVariable String locale,
                                            @RequestBody String value) {
        TranslationRequestDto.Update updateDto = TranslationRequestDto.Update.builder()
                .keyId(keyId)
                .locale(locale)
                .value(value)
                .build();

        Translation translation = translationService.updateOfTranslation(updateDto);
        return ResponseEntity.ok(new TranslationResponse(translation));
    }

    @PostMapping("/{locale}")
    public ResponseEntity createTranslation(@PathVariable Integer keyId,
                                            @PathVariable String locale,
                                            @RequestBody String value) {
        Translation newTranslation = translationService.createTranslation(keyId, locale, value);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TranslationResponse(newTranslation));
    }
}