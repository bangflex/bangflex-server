package springbootmonolithic.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Bangflex API Specification",
                description = "Bangflex APIs",
                version = "v1"
        )
)
@Configuration
public class OASConfiguration {
    @Bean
    public OpenApiCustomizer customOpenApiCustomizer() {
        return openApi -> openApi
                .schemaRequirement("bearerAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .path("/api/v1/auth/login", new PathItem()
                        .post(new Operation()
                                .summary("User Login")
                                .description("Authenticate user and issue access and refresh tokens")
                                .tags(List.of("security"))
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse().description("Login successful"))
                                        .addApiResponse("401", new ApiResponse().description("Unauthorized"))
                                )
                        )
                )
                .path("/api/v1/auth/refresh", new PathItem()
                        .post(new Operation()
                                .summary("Refresh Token")
                                .description("Refresh the user's access token using a valid refresh token")
                                .tags(List.of("security"))
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse().description("Token refreshed successfully"))
                                        .addApiResponse("401", new ApiResponse().description("Invalid or expired refresh token"))
                                )
                        )
                );
    }
}
