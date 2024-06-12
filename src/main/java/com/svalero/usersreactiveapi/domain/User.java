package com.svalero.usersreactiveapi.domain;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Document(value = "users")
public class User {
    @Id
    private String id;

    @Field
    private String username;

    @Field
    private String email;

    @Field
    private String password;
}
