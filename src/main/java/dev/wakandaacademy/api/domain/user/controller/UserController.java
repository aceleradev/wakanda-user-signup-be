package dev.wakandaacademy.api.domain.user.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import dev.wakandaacademy.api.domain.user.controller.dto.UserCreatedDto;
import dev.wakandaacademy.api.domain.user.controller.dto.UserCreationFormDto;
import dev.wakandaacademy.api.domain.user.model.User;
import dev.wakandaacademy.api.domain.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService service) {
        this.userService = service;
    }

    @PostMapping
    public ResponseEntity<UserCreatedDto> userCreation(@RequestBody @Valid UserCreationFormDto dto,
                                                            UriComponentsBuilder uriBuilder) throws Exception {
        log.info("Initialing userCreation method");
        User user = this.userService.create(dto);
        log.info("generating uri return code 201");
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        log.info("returning uri and user's created info");
        return ResponseEntity.created(uri).body(new UserCreatedDto(user));
    }

}
