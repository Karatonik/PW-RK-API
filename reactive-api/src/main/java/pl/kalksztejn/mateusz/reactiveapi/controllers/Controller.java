package pl.kalksztejn.mateusz.reactiveapi.controllers;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kalksztejn.mateusz.reactiveapi.Article;
import pl.kalksztejn.mateusz.reactiveapi.services.ArticleService;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Publisher<Article> getAll() {
        return articleService.findAll();
    }

    @GetMapping("/{id}")
    public Publisher<Article> getById(@PathVariable String id) {
        return articleService.findById(id);
    }

    @PostMapping
    public Publisher<Article> create(@RequestBody Article article) {
        return articleService.save(article);
    }

    @DeleteMapping("/{id}")
    public Publisher<Void> deleteById(@PathVariable String id) {
        return articleService.delete(id);
    }

    @PutMapping
    public Publisher<Article> updateUser(@RequestBody Article article) {
        return articleService.save(article);
    }
}
