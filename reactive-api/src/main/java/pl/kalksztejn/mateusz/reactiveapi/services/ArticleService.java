package pl.kalksztejn.mateusz.reactiveapi.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kalksztejn.mateusz.reactiveapi.Article;
import pl.kalksztejn.mateusz.reactiveapi.repositorys.ArticleRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ArticleService {
    private ArticleRepository articleRepository;

    public Flux<Article> findAll() {
        return articleRepository.findAll();
    }

    public Mono<Article> findById(String id) {
        return articleRepository.findById(id);
    }

    public Mono<Article> save(Article article) {
        return articleRepository.save(article);
    }

    public Mono<Void> delete(String id) {
        return articleRepository.deleteById(id);

    }


}
