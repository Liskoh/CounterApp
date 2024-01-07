package me.liskoh.counter.configuration;

import lombok.RequiredArgsConstructor;
import me.liskoh.counter.constants.PermissionEnum;
import me.liskoh.counter.constants.RoleEnum;
import me.liskoh.counter.filter.JwtFilter;
import me.liskoh.counter.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/v1/hello").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login", "/api/v1/auth/register").permitAll()

                        /* User endpoints */
                        .requestMatchers(HttpMethod.POST, "/api/v1/counter/add").hasAuthority(PermissionEnum.ADD_SELF_COUNTER.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/counter/find-personal-counter/{id}").hasAuthority(PermissionEnum.VIEW_SELF_COUNTER.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/counter/find-counters").hasAuthority(PermissionEnum.VIEW_SELF_COUNTER.name())

                        /* Moderator endpoints */
                        .requestMatchers(HttpMethod.GET, "/api/v1/counter/find-counter/{id}").hasAuthority(PermissionEnum.VIEW_OTHER_COUNTER.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/counter/find-counters/{username}").hasAuthority(PermissionEnum.VIEW_OTHER_COUNTER.name())

                        /* Admin endpoints */
                        .requestMatchers(HttpMethod.GET, "/api/v1/counter/find-all-counters").hasRole(RoleEnum.ADMINISTRATOR.name())

                        .anyRequest().authenticated()

                )
                .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new MappingJackson2HttpMessageConverter());
//    }
//
//    @Bean
//    public MappingJackson2CborHttpMessageConverter jacson2CborHttpMessageConverter() {
//        return new MappingJackson2CborHttpMessageConverter();
//    }
}