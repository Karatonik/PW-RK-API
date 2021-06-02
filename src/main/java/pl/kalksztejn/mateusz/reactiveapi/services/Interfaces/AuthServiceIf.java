package pl.kalksztejn.mateusz.reactiveapi.services.Interfaces;

import org.springframework.http.ResponseEntity;
import pl.kalksztejn.mateusz.reactiveapi.models.payload.LoginRequest;
import reactor.core.publisher.Mono;

public interface AuthServiceIf {

    Mono<ResponseEntity> singIn(LoginRequest loginRequest);

    Mono<String> singUp(LoginRequest loginRequest);
}
