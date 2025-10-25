package esame_finale.ticket_platform.security;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
        .requestMatchers("/dashboard").hasAuthority("ADMIN")
        .requestMatchers("/ticket").hasAnyAuthority("ADMIN")
        .requestMatchers("/operatore/**").hasAnyAuthority("ADMIN", "OPERATORE")
        .requestMatchers("/**").permitAll()
        .and().formLogin().successHandler(customSuccessHandler()).and().logout()
        .and().csrf().disable();

        return http.build();
    }

    @Bean
    DatabaseUserDetailsService userDetailsService(){
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    private AuthenticationSuccessHandler customSuccessHandler() {
    return new AuthenticationSuccessHandler() {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response,
                                            Authentication authentication)
                                            throws IOException {

            String role = authentication.getAuthorities().iterator().next().getAuthority();
            System.out.println("[DEBUG] Ruolo autenticato: " + role);
            System.out.println("[DEBUG] Authentication object: " + authentication);

            if (role.equals("ADMIN")) {
                System.out.println("[DEBUG] Redirect verso /dashboard/");
                response.sendRedirect("/dashboard/");
            } else if (role.equals("OPERATORE")) {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                System.out.println("[DEBUG] Principal type: " + principal.getClass().getName());
                System.out.println("[DEBUG] Principal value: " + principal);

                if (principal instanceof DatabaseUserDetail currentUser) {
                    Integer id = currentUser.getId();
                    System.out.println("[DEBUG] ID Operatore: " + id);
                    response.sendRedirect("/operatore/dettaglio/" + id);
                } else {
                    System.out.println("[DEBUG] Principal non è un DatabaseUserDetail");
                    response.sendRedirect("/login?error=principal");
                }
            }
        }
    };
}

}
