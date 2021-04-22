package pl.kalksztejn.mateusz.reactiveapi.repositorys;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.kalksztejn.mateusz.reactiveapi.Article;

public interface ArticleRepository  extends ReactiveMongoRepository<Article, String> {
}
