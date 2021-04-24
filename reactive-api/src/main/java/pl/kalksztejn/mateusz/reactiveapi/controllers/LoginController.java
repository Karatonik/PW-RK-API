package pl.kalksztejn.mateusz.reactiveapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import pl.kalksztejn.mateusz.reactiveapi.config.JwtUtil;
import pl.kalksztejn.mateusz.reactiveapi.models.Autor;
import pl.kalksztejn.mateusz.reactiveapi.services.AutorService;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/log")
public class LoginController {
  /*  private final static ResponseEntity<Object> UNAUTHORIZED =
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    private final JwtUtil jwtUtil;

    */
    private final AutorService autorService;


    @Autowired
    public LoginController(AutorService autorService/*, JwtUtil jwtUtil*/) {
        this.autorService = autorService;
       // this.jwtUtil = jwtUtil;
    }

  /*  @PostMapping("/login")
    public Mono<ResponseEntity> login(ServerWebExchange swe) {
        return swe.getFormData().flatMap(credentials ->
                autorService.findByUsername(credentials.getFirst("username"))
                        .cast(Autor.class)
                        .map(userDetails ->
                                Objects.equals(
                                        credentials.getFirst("password"),
                                        userDetails.getPassword()
                                )
                                        ? ResponseEntity.ok(jwtUtil.generateToken(userDetails))
                                        : UNAUTHORIZED
                        )
                        .defaultIfEmpty(UNAUTHORIZED)
        );
    }
*/
    @PostMapping("/{id}/{login}/{password}")
    public Mono<Autor> create(@PathVariable String id , @PathVariable String login, @PathVariable String password){
    return     autorService.save(id,login,password);
    }
}
