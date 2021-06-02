package pl.kalksztejn.mateusz.reactiveapi.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.kalksztejn.mateusz.reactiveapi.models.Admin;
import pl.kalksztejn.mateusz.reactiveapi.repositorys.AdminRepository;
import reactor.core.publisher.Mono;

@Service
public class AdminService implements ReactiveUserDetailsService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;

    }

    @Override
    public Mono<UserDetails> findByUsername(String login) {
        return adminRepository.findByLogin(login)
                .cast(UserDetails.class);
    }



    public Mono<Admin> save(String id, String login , String password){
        return adminRepository.save(new Admin(id,login,password));
    }

}
