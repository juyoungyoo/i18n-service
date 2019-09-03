package com.zoyi.i18nservice.channel.translation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TranslationRepository extends JpaRepository<Translation, Integer> {

    List<Translation> findAllByKeyId(Integer keyId);

    Optional<Translation> findByKeyIdAndLocale(Integer keyId, String locale);
}
