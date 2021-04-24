package pl.kalksztejn.mateusz.reactiveapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import pl.kalksztejn.mateusz.reactiveapi.models.Article;
import pl.kalksztejn.mateusz.reactiveapi.repositorys.ArticleRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.http.ResponseEntity.ok;

@Service

public class ArticleService {

    private ArticleRepository articleRepository;

    private final ReactiveGridFsTemplate gridFsTemplate;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ReactiveGridFsTemplate gridFsTemplate) {
        this.articleRepository = articleRepository;
        this.gridFsTemplate = gridFsTemplate;
    }

    public Flux<Article> findAll() {
        return articleRepository.findAll();
    }

    public Flux<Article> findAllStream(){
        return Flux.zip(Flux.interval(Duration.ofSeconds(1)),articleRepository.findAll()).map(Tuple2::getT2);

    }

    public Mono<Article> findById(String id) {
        return articleRepository.findById(id);
    }

    public Mono<Article> save(String id , String title , String header , String text) {
        return articleRepository.save(new Article(id,title,header,text));
    }

    public Mono<Void> delete(String id) {
        return articleRepository.deleteById(id);
    }

    public Mono<Article> addImg(String id ,Mono<FilePart> fileParts){
      return  articleRepository.findById(id).flatMap(v->{
                  return fileParts
                           .flatMap(part -> this.gridFsTemplate.store(part.content(), part.filename()))
                          .map(z ->{
                           List<String> list= v.getImagesId();
                           list.add(z.toHexString());
                           v.setImagesId(list);
                            return v;
                              }).flatMap(z->articleRepository.save(z));
        }
        );
    }
    public Flux<Void> getImg(String id, ServerWebExchange exchange) {
    return this.gridFsTemplate.findOne(query(where("_id").is(id)))
            .log()
                .flatMap(gridFsTemplate::getResource)
                .flatMapMany(r -> exchange.getResponse().writeWith(r.getDownloadStream()));
}



    /*
    public Flux<Object> read(String id, ServerWebExchange exchange) {
   Flux<Object> flux=  articleRepository.findById(id).map(Article::getImagesId)
                .flatMapMany(Flux::fromIterable).log()
            .flatMap(z->{ return this.gridFsTemplate.findOne(query(where("_id").is(z))).log().flatMap(gridFsTemplate::getResource)
                    .flatMapMany(r -> exchange.getResponse().writeWith(r.getDownloadStream()));
            });

      return   Flux.zip(Flux.interval(Duration.ofSeconds(1)),flux.;

    }
     */





}
