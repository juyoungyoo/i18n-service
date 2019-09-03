package com.zoyi.i18nservice.channel.translation;

import com.zoyi.i18nservice.channel.translation.dto.TranslationRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslationService {

    private final TranslationRepository translationRepository;

    public TranslationService(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    List<Translation> searchTranslations(Integer keyId) {
        return translationRepository.findAllByKeyId(keyId);
    }

    Translation searchTranslation(TranslationRequestDto requestDto) {
        return findTranslation(requestDto.getKeyId(), requestDto.getLocale());
    }

    Translation createTranslation(Translation translation) {
        return translationRepository.save(translation);
    }

    Translation updateOfTranslation(Translation translation) {
        Translation origin = findTranslation(translation.getKeyId(), translation.getLocale());
        origin.update(translation);
        return translationRepository.save(origin);
    }

    private Translation findTranslation(Integer keyId, String locale) {
        return translationRepository.findByKeyIdAndLocale(keyId, locale)
                .orElseThrow(() -> new NotFoundTranslationException(keyId, locale));
    }
}
