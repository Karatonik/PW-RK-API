package pl.kalksztejn.mateusz.reactiveapi.controllers;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import pl.kalksztejn.mateusz.reactiveapi.models.Article;
import pl.kalksztejn.mateusz.reactiveapi.models.ArticleDTO;
import pl.kalksztejn.mateusz.reactiveapi.models.payload.LoginRequest;
import pl.kalksztejn.mateusz.reactiveapi.services.ArticleService;
import pl.kalksztejn.mateusz.reactiveapi.services.AuthService;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*" ,allowedHeaders = "*")
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
    public Mono<String> register(@RequestBody LoginRequest loginRequest) {
        return authService.singUp(loginRequest);
    }

    //TWORZENIE ARYKUŁU
    @PostMapping
    public Mono<Article> create(@RequestBody ArticleDTO articleDTO) {
        return articleService.save(articleDTO.getTitle(), articleDTO.getHeader(), articleDTO.getText());
    }

    //USUWANIE ARTYKUŁU
    @DeleteMapping("/{id}")
    public Publisher<Void> deleteById(@PathVariable String id) {
        return articleService.delete(id);
    }

    //EDYCJA ARTYKUŁU
    @PutMapping("/{id}")
    public Mono<Article> update(@PathVariable String id, @RequestBody ArticleDTO articleDTO) {
        return articleService.update(id, articleDTO.getTitle(), articleDTO.getHeader(), articleDTO.getText());
    }
    //DODANIE OBRAZKA ,ID artykułu
    @PostMapping("/img/{id}")
    public Mono<ResponseEntity> upload(@PathVariable String id, @RequestPart Mono<FilePart> fileParts) {

        return articleService.addImg(id, fileParts).map((v) -> ok().body(v));
    }

}
