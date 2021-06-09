package pl.kalksztejn.mateusz.reactiveapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public WebSecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
    }




    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                                )
                )
                .accessDeniedHandler(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
                                )
                )
                .and()
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers("/", "/favicon.ico").permitAll()
                .pathMatchers("/api/**").permitAll()
                .pathMatchers("/admin/singin","/admin/singup").permitAll()
                .anyExchange().authenticated()
                .and()
                .build();
    }


}
