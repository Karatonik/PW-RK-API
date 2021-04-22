package pl.kalksztejn.mateusz.reactiveapi.config;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.kalksztejn.mateusz.reactiveapi.Article;
import pl.kalksztejn.mateusz.reactiveapi.repositorys.ArticleRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;
@Log4j2
@Component
@AllArgsConstructor
@org.springframework.context.annotation.Profile("local")
public class DemoDataInit implements ApplicationListener<ApplicationReadyEvent> {
    private final ArticleRepository articleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        articleRepository.deleteAll()
                .thenMany(
                        Flux.just("1 wpis", "2 wpis", "3 wpis")
                                .map(v -> new Article(UUID.randomUUID().toString(), v))
                                .flatMap(articleRepository::save)
                )
                .thenMany(articleRepository.findAll())
                .subscribe(v -> log.info("Saving  " + v));
    }
}
