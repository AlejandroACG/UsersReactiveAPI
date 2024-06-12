package com.svalero.usersreactiveapi.service;
import com.svalero.usersreactiveapi.domain.User;
import com.svalero.usersreactiveapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Flux<User> getUsers() { return userRepository.findAll(); }

    public Mono<User> addUser(User user) { return userRepository.save(user); }

    public Mono<User> getUser(String id) { return userRepository.findById(id); }

    public Mono<User> updateUser(Mono<User> user) {
        return user
                .flatMap((u) -> userRepository.findById(u.getId())
                        .flatMap(product1 -> {
                            product1.setUsername(u.getUsername());
                            return userRepository.save(product1);
                        }));
    }

    public Mono<Boolean> deleteUser(String id) {
        return userRepository.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return userRepository.deleteById(id).thenReturn(true);
                    } else {
                        return Mono.just(false);
                    }
                });
    }
}
