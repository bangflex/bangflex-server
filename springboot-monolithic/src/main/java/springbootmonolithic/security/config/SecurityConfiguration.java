package springbootmonolithic.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import springbootmonolithic.security.filter.DaoAuthenticationFilter;
import springbootmonolithic.security.provider.ProviderManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final ProviderManager providerManager;
    private final ObjectMapper objectMapper;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public SecurityConfiguration(
            ProviderManager providerManager,
            ObjectMapper objectMapper,
            AuthenticationEntryPoint authenticationEntryPoint
    ) {
        this.providerManager = providerManager;
        this.objectMapper = objectMapper;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);  // csrf 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessionManagement ->  // 세션 비활성화
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(authorize ->
                authorize
//                        .requestMatchers("/**").permitAll()
//                        .requestMatchers("/api/v1/check/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint) // 401 UNAUTHORIZED
//                        .accessDeniedHandler(accessDeniedHandler)           // 403 FORBIDDEN
                )

                .addFilter(new DaoAuthenticationFilter(providerManager, objectMapper));

        return http.build();
    }
}
