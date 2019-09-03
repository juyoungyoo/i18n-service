package com.zoyi.i18nservice.channel.repository;

import com.zoyi.i18nservice.channel.domain.Key;
import com.zoyi.i18nservice.channel.domain.KeyName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyRepository extends JpaRepository<Key, Integer> {

    Optional<Key> findAllByName(KeyName name);

}
