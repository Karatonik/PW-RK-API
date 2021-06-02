package pl.kalksztejn.mateusz.reactiveapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kalksztejn.mateusz.reactiveapi.config.JwtUtil;
import pl.kalksztejn.mateusz.reactiveapi.models.Admin;
import pl.kalksztejn.mateusz.reactiveapi.models.payload.LoginRequest;
import pl.kalksztejn.mateusz.reactiveapi.services.Interfaces.AuthServiceIf;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class AuthService implements AuthServiceIf {

    private final static ResponseEntity<Object> UNAUTHORIZED =
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    private final JwtUtil jwtUtil;
    AdminService adminService;
    PasswordEncoder encoder;

    @Autowired
    public AuthService(AdminService adminService, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public Mono<ResponseEntity> singIn(LoginRequest loginRequest) {
        return adminService.findByUsername(loginRequest.getLogin()).cast(Admin.class).map(userDetails -> encoder.matches(
                loginRequest.getPassword(),
                userDetails.getPassword()
        )
                ? ResponseEntity.ok(jwtUtil.generateToken(userDetails))
                : UNAUTHORIZED);
    }
    @Override
    public Mono<String> singUp(LoginRequest loginRequest) {
        return adminService.save(String.valueOf(loginRequest.hashCode()), loginRequest.getLogin()
                , encoder.encode(loginRequest.getPassword()))
                .map(v -> Objects.equals(loginRequest.getLogin(), v.getLogin()) ? "Success" : "Error");
    }
}
