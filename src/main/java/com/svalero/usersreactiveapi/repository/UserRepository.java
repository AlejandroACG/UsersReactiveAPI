package com.svalero.usersreactiveapi.repository;
import com.svalero.usersreactiveapi.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Flux<User> findAll();
}
