package com.zoyi.i18nservice.channel.service;

import com.zoyi.i18nservice.channel.domain.Key;
import com.zoyi.i18nservice.channel.domain.KeyName;
import com.zoyi.i18nservice.channel.domain.Translation;
import com.zoyi.i18nservice.channel.dto.TranslationRequestDto;
import com.zoyi.i18nservice.channel.exception.NotFoundKeyException;
import com.zoyi.i18nservice.channel.exception.NotFoundTranslationException;
import com.zoyi.i18nservice.channel.repository.KeyRepository;
import com.zoyi.i18nservice.channel.repository.TranslationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {

    private final KeyRepository keyRepository;
    private final TranslationRepository translationRepository;

    public ChannelService(KeyRepository keyRepository, TranslationRepository translationRepository) {
        this.keyRepository = keyRepository;
        this.translationRepository = translationRepository;
    }

    public List<Key> findAll() {
        return keyRepository.findAll();
    }

    public Key searchKey(KeyName name) {
        Key key = keyRepository.findAllByName(name)
                .orElseThrow(() -> new NotFoundKeyException(name));
        return key;
    }

    public Key createOfKey(KeyName name) {
        return keyRepository.save(Key.of(name));
    }

    public Key updateOfKey(Integer keyId, KeyName name) {
        Key key = keyRepository.findById(keyId)
                .orElseThrow(() -> new NotFoundKeyException(keyId));
        key.update(name);
        return keyRepository.save(key);
    }

    public Translation createOfTranslation(Translation translation) {
        return translationRepository.save(translation);
    }

    public List<Translation> searchTranslations(Integer keyId) {
        return translationRepository.findAllByKeyId(keyId);
    }

    public Translation findTranslation(TranslationRequestDto requestDto) {
        return findTranslation(requestDto.getKeyId(), requestDto.getLocale());
    }

    public Translation updateOfTranslation(Translation translation) {
        Translation origin = findTranslation(translation.getKeyId(), translation.getLocale());
        origin.update(translation);
        return translationRepository.save(origin);
    }

    private Translation findTranslation(Integer keyId, String locale) {
        return translationRepository.findByKeyIdAndLocale(keyId, locale)
                .orElseThrow(() -> new NotFoundTranslationException(keyId, locale));
    }
}