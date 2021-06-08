package pl.kalksztejn.mateusz.reactiveapi.controllers;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import pl.kalksztejn.mateusz.reactiveapi.models.Article;
import pl.kalksztejn.mateusz.reactiveapi.models.payload.LoginRequest;
import pl.kalksztejn.mateusz.reactiveapi.services.ArticleService;
import pl.kalksztejn.mateusz.reactiveapi.services.AuthService;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private ArticleService articleService;

    private AuthService authService;


    @Autowired
    public AdminController(ArticleService articleService, AuthService authService) {
        this.articleService = articleService;
        this.authService = authService;
    }

    //LOGOWANIE zwraca JWT TOKEN
    @PostMapping("/singin")
    public Mono<ResponseEntity> login(@RequestBody LoginRequest loginRequest) {
        return authService.singIn(loginRequest);
    }

    //REJESTRACJA zwraca SUCCESS / ERROR
    @PostMapping("/singup")
    public Mono<String> create(@RequestBody LoginRequest loginRequest) {
        return authService.singUp(loginRequest);
    }

    //TWORZENIE ARYKUŁU
    @PostMapping("/{title}/{header}")
    public Publisher<Article> create(@PathVariable String title, @PathVariable String header, @RequestBody String text) {
        return articleService.save(title, header, text);
    }

    //USUWANIE ARTYKUŁU
    @DeleteMapping("/{id}")
    public Publisher<Void> deleteById(@PathVariable String id) {
        return articleService.delete(id);
    }

    //EDYCJA ARTYKUŁU
    @PutMapping("/{id}/{title}/{header}")
    public Publisher<Article> updateUser(@PathVariable String id, @PathVariable String title, @PathVariable String header, @RequestBody String text) {
        return articleService.update(id, title, header, text);
    }
    //DODANIE OBRAZKA ,ID artykułu
    @PostMapping("/img/{id}")
    public Publisher<ResponseEntity> upload(@PathVariable String id, @RequestPart Mono<FilePart> fileParts) {

        return articleService.addImg(id, fileParts).map((v) -> ok().body(v));
    }

}
