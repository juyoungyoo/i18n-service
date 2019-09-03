package com.zoyi.i18nservice.channel.controller;

import com.zoyi.i18nservice.channel.domain.Key;
import com.zoyi.i18nservice.channel.domain.KeyName;
import com.zoyi.i18nservice.channel.domain.Translation;
import com.zoyi.i18nservice.channel.dto.*;
import com.zoyi.i18nservice.channel.service.ChannelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/keys")
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping
    public ResponseEntity queryKeys(@RequestParam(required = false) KeyName name) {
        if (Objects.isNull(name)) {
            List<Key> keys = channelService.findAll();
            return ResponseEntity.ok(KeysResponse.of(keys));
        }
        Key key = channelService.searchKey(name);
        return ResponseEntity.ok(KeyResponse.of(key));
    }

    @PostMapping
    public ResponseEntity createKey(@RequestBody KeyName name) {
        Key key = channelService.createOfKey(name);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(KeyResponse.of(key));
    }

    @PutMapping("/{keyId}")
    public ResponseEntity updateKey(@PathVariable Integer keyId,
                                    @RequestBody KeyName name) {
        Key updateKey = channelService.updateOfKey(keyId, name);
        return ResponseEntity.ok(KeyResponse.of(updateKey));
    }

    @PostMapping("/{keyId}/translations/{locale}")
    public ResponseEntity createTranslation(@PathVariable Integer keyId,
                                            @PathVariable String locale,
                                            @RequestBody String value) {
        Translation translation = Translation.builder()
                .keyId(keyId)
                .locale(locale)
                .value(value)
                .build();

        Translation newTranslation = channelService.createOfTranslation(translation);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TranslationResponse(newTranslation));
    }

    @GetMapping("/{keyId}/translations")
    public ResponseEntity searchTranslation(@PathVariable Integer keyId) {
        List<Translation> translations = channelService.searchTranslations(keyId);
        return ResponseEntity.ok(new TranslationsResponse(translations));
    }

    @GetMapping("/{keyId}/translations/{locale}")
    public ResponseEntity searchTranslation(@PathVariable Integer keyId,
                                            @PathVariable String locale) {
        TranslationRequestDto translationRequestDto = new TranslationRequestDto(keyId, locale);
        Translation translation = channelService.findTranslation(translationRequestDto);

        return ResponseEntity.ok(new TranslationResponse(translation));
    }

    @PutMapping("/{keyId}/translations/{locale}")
    public ResponseEntity updateTranslation(@PathVariable Integer keyId,
                                            @PathVariable String locale,
                                            @RequestBody String value) {
        TranslationRequestDto.Update updateDto = TranslationRequestDto.Update.builder()
                .keyId(keyId)
                .locale(locale)
                .value(value)
                .build();

        Translation translation = channelService.updateOfTranslation(updateDto.toEntity());
        return ResponseEntity.ok(new TranslationResponse(translation));
    }
}
