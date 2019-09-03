package com.zoyi.i18nservice.channel.keys;

import com.zoyi.i18nservice.channel.ChannelService;
import com.zoyi.i18nservice.channel.keys.dto.KeyResponse;
import com.zoyi.i18nservice.channel.keys.dto.KeysResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequestMapping("/keys")
@RestController
public class KeysController {

    private final ChannelService channelService;

    public KeysController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping
    public ResponseEntity query(@RequestParam(required = false) KeyName name) {
        if (Objects.isNull(name)) {
            List<Key> keys = channelService.findAll();
            return ResponseEntity.ok(KeysResponse.of(keys));
        }
        Key key = channelService.searchKey(name);
        return ResponseEntity.ok(KeyResponse.of(key));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody KeyName name) {
        Key key = channelService.createKey(name);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(KeyResponse.of(key));
    }

    @PutMapping("/{keyId}")
    public ResponseEntity update(@PathVariable Integer keyId,
                                 @RequestBody KeyName name) {
        Key updateKey = channelService.updateKey(keyId, name);
        return ResponseEntity.ok(KeyResponse.of(updateKey));
    }
}