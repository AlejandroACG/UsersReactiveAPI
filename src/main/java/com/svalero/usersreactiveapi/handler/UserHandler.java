package com.svalero.usersreactiveapi.handler;
import com.svalero.usersreactiveapi.domain.User;
import com.svalero.usersreactiveapi.service.UserService;
import com.svalero.usersreactiveapi.util.ErrorResponse;
import com.svalero.usersreactiveapi.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class UserHandler {
    @Autowired
    private UserService userService;

    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    private final Validator validator = new UserValidator();

    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getUsers(), User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        Mono<User> userToSave = serverRequest.bodyToMono(User.class)
                .doOnNext(this::validate);

        return userToSave.flatMap(user ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.addUser(user), User.class));
    }

    public Mono<ServerResponse> getUser(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return userService.getUser(id)
                .flatMap(u -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(u), User.class))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(new ErrorResponse(404, "User not found")), ErrorResponse.class));
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<User> userMono = serverRequest.bodyToMono(User.class)
                .map(user -> {
                    user.setId(id);
                    return user;
                })
                .doOnNext(this::validate);

        return userService.updateUser(userMono).flatMap(user ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(fromObject(user)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return userService.deleteUser(id)
                .flatMap(deleted -> {
                    if (deleted) {
                        return ServerResponse.noContent().build();
                    } else {
                        return ServerResponse.status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(new ErrorResponse(404, "User not found")), ErrorResponse.class);
                    }
                });
    }

    private void validate(User user) {
        Errors errors = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, errors);
        if (errors.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    errors.getAllErrors().toString());
        }
    }
}
