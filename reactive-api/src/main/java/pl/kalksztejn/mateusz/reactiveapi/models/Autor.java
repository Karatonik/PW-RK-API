package pl.kalksztejn.mateusz.reactiveapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Collection;
import java.util.HashSet;
@EqualsAndHashCode
@NoArgsConstructor
@Data
@Getter
@Setter
@Document
public class Autor /* implements UserDetails */{
    @Id
    String id;

    String login;
    @JsonIgnore
    String password;

   // private Collection<? extends GrantedAuthority> authorities;


    public Autor(String id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
       // this.authorities = new HashSet<>();
    }

    public static Autor build(Autor autor) {

        return new Autor(
                autor.getId(),
                autor.getLogin(),
                autor.getPassword());
    }
/*
    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

 */
}
