package pl.kalksztejn.mateusz.reactiveapi.services.Interfaces;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.server.ServerWebExchange;
import pl.kalksztejn.mateusz.reactiveapi.models.Article;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArticleServiceIf {

    Flux<Article> findAll();

    Flux<Article> findAllStream();

    Mono<Article> findById(String id);

    Mono<Article> save(String title, String header, String text);

    Mono<Article> update(String id ,String title, String header, String text);


    Mono<Void> delete(String id);

    Mono<Article> addImg(String id, Mono<FilePart> fileParts);

    Flux<Void> getImg(String id, ServerWebExchange exchange);

}
