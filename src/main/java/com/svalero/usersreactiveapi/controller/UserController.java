package com.svalero.usersreactiveapi.controller;
import com.svalero.usersreactiveapi.domain.User;
import com.svalero.usersreactiveapi.exception.EntityNotFoundException;
import com.svalero.usersreactiveapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> getUsers() {
        return userService.findAll();
    }

    @PostMapping(value = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping(value = "/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<User> getUser(@PathVariable String id) {
        return userService.findById(id);
    }

    @PutMapping(value = "/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<User> updateUser(@PathVariable String id, @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping(value = "/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUser(@PathVariable String id) {
        Mono<User> user = userService.findById(id);
        user.switchIfEmpty(Mono.error(new EntityNotFoundException("User not found")));
        return userService.deleteUser(id);
    }
}
