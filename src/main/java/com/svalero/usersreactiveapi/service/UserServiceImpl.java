package com.svalero.usersreactiveapi.service;
import com.svalero.usersreactiveapi.domain.User;
import com.svalero.usersreactiveapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.lang.reflect.Field;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<User> findAll() { return userRepository.findAll(); }

    @Override
    public Mono<User> addUser(User user) { return userRepository.save(user); }

    @Override
    public Mono<User> findById(String id) { return userRepository.findById(id); }

    @Override
    public Mono<User> updateUser(String id, User user) {
        return userRepository.findById(id).flatMap(existingUser -> {
            copyNonNullProperties(user, existingUser);
            return userRepository.save(existingUser);
        });
    }

    @Override
    public Mono<Void> deleteUser(String id) {
        Mono<User> user = findById(id).cache();
        return userRepository.deleteById(id);
    }

    private void copyNonNullProperties(User source, User target) {
        for (Field field : User.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error copying properties", e);
            }
        }
    }
}
