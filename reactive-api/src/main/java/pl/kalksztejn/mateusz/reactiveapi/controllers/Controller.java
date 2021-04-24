package pl.kalksztejn.mateusz.reactiveapi.controllers;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import pl.kalksztejn.mateusz.reactiveapi.models.Article;
import pl.kalksztejn.mateusz.reactiveapi.services.ArticleService;
import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {

    private ArticleService articleService;

    @Autowired
    public Controller(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public Publisher<Article> getAll() {
        return articleService.findAll();
    }

    @GetMapping(value = "/All", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<Article> getAllStream() {
        return articleService.findAllStream();
    }


    @GetMapping("/{id}")
    public Publisher<Article> getById(@PathVariable String id) {
        return articleService.findById(id);
    }

    @PostMapping("/{id}/{title}/{header}")
    public Publisher<Article> create(@PathVariable String id, @PathVariable String title, @PathVariable String header, @RequestBody String text) {
        return articleService.save(id, title, header, text);
    }

    @DeleteMapping("/{id}")
    public Publisher<Void> deleteById(@PathVariable String id) {
        return articleService.delete(id);
    }

    @PutMapping("/{id}/{title}/{header}")
    public Publisher<Article> updateUser(@PathVariable String id, @PathVariable String title, @PathVariable String header, @RequestBody String text) {
        return articleService.save(id, title, header, text);
    }


    @PostMapping("/img/{id}")
    public Publisher<ResponseEntity> upload(@PathVariable String id, @RequestPart Mono<FilePart> fileParts) {

        return articleService.addImg(id, fileParts).map((v) -> ok().body(v));
    }

    @GetMapping("/img/{imageId}")
    public Publisher<Void> read(@PathVariable String imageId, ServerWebExchange exchange) {
        return articleService.getImg(imageId, exchange);
    }
}
