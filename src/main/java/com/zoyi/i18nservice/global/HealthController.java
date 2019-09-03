package com.zoyi.i18nservice.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthController {

    public static final String SERVER_CONNECT_SUCCESS_MESSAGE = "Server connect success";

    @RequestMapping("/health")
    private ResponseEntity healthCheck() {
        log.debug("Server connect success");
        return ResponseEntity.ok().body(SERVER_CONNECT_SUCCESS_MESSAGE);
    }

    @RequestMapping
    private ResponseEntity notFound() {
        return ResponseEntity.notFound().build();
    }
}
