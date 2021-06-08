package pl.kalksztejn.mateusz.reactiveapi.models.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginRequest {
    String login;

    String password;
}
