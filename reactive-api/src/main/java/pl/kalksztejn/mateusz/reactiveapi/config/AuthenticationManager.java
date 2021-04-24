package pl.kalksztejn.mateusz.reactiveapi.config;


import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

//@Component
public class AuthenticationManager /*implements ReactiveAuthenticationManager*/ {
   /* private final JwtUtil jwtUtil;

    public AuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String username;

        try {
            username = jwtUtil.extractUsername(authToken);
        } catch (Exception e) {
            username = null;
            System.out.println(e);
        }

        if (username != null && jwtUtil.validateToken(authToken)) {
            Claims claims = jwtUtil.getClaimsFromToken(authToken);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,null

            );

            return Mono.just(authenticationToken);
        } else {
            return Mono.empty();
        }
    }

    */
}
