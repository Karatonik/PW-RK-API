package pl.kalksztejn.mateusz.reactiveapi.repositorys;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.kalksztejn.mateusz.reactiveapi.models.Admin;
import reactor.core.publisher.Mono;

public interface AdminRepository extends ReactiveMongoRepository<Admin, String> {
    Mono<Admin> findByLogin(String login);
}
