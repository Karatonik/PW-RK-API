package pl.kalksztejn.mateusz.reactiveapi.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import pl.kalksztejn.mateusz.reactiveapi.models.Autor;
import pl.kalksztejn.mateusz.reactiveapi.repositorys.AutorRepository;
import reactor.core.publisher.Mono;

@Service
public class AutorService /*implements ReactiveUserDetailsService*/ {
    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }
/*
    @Override
    public Mono<UserDetails> findByUsername(String login) {
        return autorRepository.findByLogin(login)
                .cast(UserDetails.class);
    }

 */

    public Mono<Autor> save(String id, String login , String password){
        return autorRepository.save(new Autor(id,login,password));
    }

}
