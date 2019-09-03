package com.zoyi.i18nservice.channel.keys;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeysService {

    private final KeyRepository keyRepository;

    public KeysService(KeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    public Key searchKey(Integer keyId) {
        return keyRepository.findById(keyId)
                .orElseThrow(() -> new NotFoundKeyException(keyId));
    }

    List<Key> findAll() {
        return keyRepository.findAll();
    }

    Key searchKey(KeyName name) {
        Key key = keyRepository.findAllByName(name)
                .orElseThrow(() -> new NotFoundKeyException(name));
        return key;
    }

    Key createKey(KeyName name) {
        return keyRepository.save(Key.of(name));
    }

    Key updateKey(Integer keyId, KeyName name) {
        Key key = keyRepository.findById(keyId)
                .orElseThrow(() -> new NotFoundKeyException(keyId));
        key.update(name);
        return keyRepository.save(key);
    }
}
