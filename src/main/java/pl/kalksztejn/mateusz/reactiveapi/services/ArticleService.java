package pl.kalksztejn.mateusz.reactiveapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import pl.kalksztejn.mateusz.reactiveapi.models.Article;
import pl.kalksztejn.mateusz.reactiveapi.repositorys.ArticleRepository;
import pl.kalksztejn.mateusz.reactiveapi.services.Interfaces.ArticleServiceIf;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service

public class ArticleService implements ArticleServiceIf {

    private final ReactiveGridFsTemplate gridFsTemplate;
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ReactiveGridFsTemplate gridFsTemplate) {
        this.articleRepository = articleRepository;
        this.gridFsTemplate = gridFsTemplate;
    }

    @Override
    public Flux<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Flux<Article> findAllStream() {
        return Flux.zip(Flux.interval(Duration.ofSeconds(1)), articleRepository.findAll()).map(Tuple2::getT2);

    }

    @Override
    public Mono<Article> findById(String id) {
        return articleRepository.findById(id);
    }

    @Override
    public Mono<Article> save(String title, String header, String text) {
        return articleRepository.save(new Article(String.valueOf(title.hashCode()), title, header, text));
    }

    @Override
    public Mono<Article> update(String id, String title, String header, String text) {
        return articleRepository.save(new Article(id, title, header, text));
    }

    @Override
    public Mono<Void> delete(String id) {
        return articleRepository.deleteById(id);
    }

    @Override
    public Mono<Article> addImg(String id, Mono<FilePart> fileParts) {
        return articleRepository.findById(id).flatMap(v -> fileParts
                .flatMap(part -> this.gridFsTemplate.store(part.content(), part.filename()))
                .map(z -> {
                    List<String> list = v.getImagesId();
                    list.add(z.toHexString());
                    v.setImagesId(list);
                    return v;
                }).flatMap(z -> articleRepository.save(z))
        );
    }

    @Override
    public Flux<Void> getImg(String id, ServerWebExchange exchange) {
        return this.gridFsTemplate.findOne(query(where("_id").is(id)))
                .log()
                .flatMap(gridFsTemplate::getResource)
                .flatMapMany(r -> exchange.getResponse().writeWith(r.getDownloadStream()));
    }
}
