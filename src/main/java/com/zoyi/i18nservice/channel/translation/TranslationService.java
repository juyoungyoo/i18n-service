package com.zoyi.i18nservice.channel.translation;

import com.zoyi.i18nservice.channel.keys.Key;
import com.zoyi.i18nservice.channel.keys.KeysService;
import com.zoyi.i18nservice.channel.translation.dto.TranslationRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TranslationService {

    private final KeysService keysService;

    private final TranslationRepository translationRepository;

    public TranslationService(KeysService keysService, TranslationRepository translationRepository) {
        this.keysService = keysService;
        this.translationRepository = translationRepository;
    }

    List<Translation> searchTranslations(Integer keyId) {
        return translationRepository.findAllByKeyId(keyId);
    }

    Translation searchTranslation(TranslationRequestDto requestDto) {
        return findTranslation(requestDto.getKeyId(), requestDto.getLocale());
    }

    Translation createTranslation(Integer keyId, String locale, String value) {
        Key key = keysService.searchKey(keyId);
        log.debug("log :{}", key);
        Translation translation = Translation.builder()
                .key(key)
                .locale(locale)
                .value(value)
                .build();
        return translationRepository.save(translation);
    }

    Translation updateOfTranslation(TranslationRequestDto.Update translation) {
        Translation origin = findTranslation(translation.getKeyId(), translation.getLocale());

        origin.update(translation.getValue());
        return translationRepository.save(origin);
    }

    private Translation findTranslation(Integer keyId, String locale) {
        return translationRepository.findByKeyIdAndLocale(keyId, locale)
                .orElseThrow(() -> new NotFoundTranslationException(keyId, locale));
    }
}