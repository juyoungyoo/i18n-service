package com.zoyi.i18nservice.channel.keys;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyRepository extends JpaRepository<Key, Integer> {

    Optional<Key> findAllByName(KeyName name);
}
