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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springbootmonolithic.security.filter.DaoAuthenticationFilter;
import springbootmonolithic.security.filter.JwtAccessTokenFilter;
import springbootmonolithic.security.filter.JwtRefreshTokenFilter;
import springbootmonolithic.security.provider.ProviderManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final ProviderManager providerManager;
    private final ObjectMapper objectMapper;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfiguration(
            ProviderManager providerManager,
            ObjectMapper objectMapper,
            AuthenticationEntryPoint authenticationEntryPoint,
            AccessDeniedHandler accessDeniedHandler
    ) {
        this.providerManager = providerManager;
        this.objectMapper = objectMapper;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);  // csrf 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessionManagement ->  // 세션 비활성화
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(authorize ->
                authorize
//                        .requestMatchers("/**").permitAll()   // for development
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/auth/signup",
                                "/api/v1/auth/email/validate").permitAll()
                        .requestMatchers("/api/v1/check/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/auth/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )

                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)           // 403 FORBIDDEN
                        .authenticationEntryPoint(authenticationEntryPoint) // 401 UNAUTHORIZED
                )

                .addFilterBefore(new JwtAccessTokenFilter(providerManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtRefreshTokenFilter(providerManager, objectMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new DaoAuthenticationFilter(providerManager, objectMapper));

        return http.build();
    }
}
