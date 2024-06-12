package com.svalero.usersreactiveapi.router;
import com.svalero.usersreactiveapi.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class UserRouter {
    @Bean
    public RouterFunction<ServerResponse> usersRouter(UserHandler userHandler) {
        return RouterFunctions
                .route(GET("/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::getAllUsers)
                .andRoute(POST("/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::createUser)
                .andRoute(GET("/user/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUser)
                .andRoute(PUT("/user/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::updateUser)
                .andRoute(DELETE("/user/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::deleteUser);
    }
}
