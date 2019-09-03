package com.zoyi.i18nservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "app-detect-language")
@Getter
@Setter
public class AppProperties {

    @NotEmpty
    private String apiKey;
}
