package com.svalero.usersreactiveapi.service;
import com.svalero.usersreactiveapi.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> findAll();
    Mono<User> addUser(User user);
    Mono<User> findById(String id);
    Mono<User> updateUser(String id, User user);
    Mono<Void> deleteUser(String id);
}
