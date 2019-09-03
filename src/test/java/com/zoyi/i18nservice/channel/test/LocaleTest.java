package com.zoyi.i18nservice.channel.test;

import org.junit.jupiter.api.Test;

import java.util.Locale;

public class LocaleTest {

    @Test
    void create() {
        Locale locale = new Locale("en");
        System.out.println(locale);

    }
}
