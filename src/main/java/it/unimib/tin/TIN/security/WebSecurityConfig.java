package it.unimib.tin.TIN.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http
                .authorizeHttpRequests((authz) ->
                        authz
                                .requestMatchers("/protected/**").authenticated()
                                .anyRequest().permitAll())
                .addFilterBefore(new AdminFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/")
                                .usernameParameter("username")
                                .defaultSuccessUrl("/protected")
                                .permitAll())
                .logout((logout) ->
                        logout
                                .logoutSuccessUrl("/?logout")
                                .permitAll());
        return http.build();
    }

}
