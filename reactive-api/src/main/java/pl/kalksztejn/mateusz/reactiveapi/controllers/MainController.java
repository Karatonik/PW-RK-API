package pl.kalksztejn.mateusz.reactiveapi.controllers;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import pl.kalksztejn.mateusz.reactiveapi.models.Article;
import pl.kalksztejn.mateusz.reactiveapi.services.ArticleService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {
    private ArticleService articleService;

    @Autowired
    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public Publisher<Article> getAll() {
        return articleService.findAll();
    }
    //POBRANIE ARTYKUŁU
    @GetMapping("/{id}")
    public Publisher<Article> getById(@PathVariable String id) {
        return articleService.findById(id);
    }
    //POBRANIE ZDJĘCIA
    @GetMapping("/img/{imageId}")
    public Publisher<Void> read(@PathVariable String imageId, ServerWebExchange exchange) {
        return articleService.getImg(imageId, exchange);
    }
    //POBRANIE wszystkich ARTYKUŁÓW (ładowane po kolei)
    @GetMapping(value = "/All", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<Article> getAllStream() {
        return articleService.findAllStream();
    }
}
