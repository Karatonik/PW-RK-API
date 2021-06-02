package pl.kalksztejn.mateusz.reactiveapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.HashSet;
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
@Data
@Document
public class Admin implements UserDetails {
    @Id
    String id;

    String login;
    @JsonIgnore
    String password;

    private Collection<? extends GrantedAuthority> authorities;


    public Admin(String id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authorities = new HashSet<>();
    }

    public static Admin build(Admin admin) {

        return new Admin(
                admin.getId(),
                admin.getLogin(),
                admin.getPassword());
    }

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


}
