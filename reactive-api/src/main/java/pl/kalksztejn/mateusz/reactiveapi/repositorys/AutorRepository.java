package pl.kalksztejn.mateusz.reactiveapi.repositorys;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.kalksztejn.mateusz.reactiveapi.models.Article;
import pl.kalksztejn.mateusz.reactiveapi.models.Autor;
import reactor.core.publisher.Mono;

public interface AutorRepository  extends ReactiveMongoRepository<Autor, String> {
    Mono<Autor> findByLogin(String login);
}
