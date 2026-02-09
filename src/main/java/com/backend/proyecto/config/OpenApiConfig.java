package com.backend.proyecto.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

//Configuración de Swagger/OpenAPI con soporte para seguridad JWT
@Configuration
@OpenAPIDefinition(info = @Info(title = "API Backend Portafolio Programadores", version = "1.0.0", description = "API REST para gestión de portafolios de programadores, asesorías y proyectos", contact = @Contact(name = "Equipo de Desarrollo", email = "soporte@portafolio.com")), servers = {
        @Server(description = "Servidor Local", url = "http://localhost:8080"),
        @Server(description = "Servidor de Desarrollo", url = "https://dev.portafolio.com")
})
@SecurityScheme(name = "bearer-jwt", description = "Token JWT para autenticación. Usar formato: Bearer {token}", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
    //La configuración se realiza mediante anotaciones
    //No se requiere código adicional
}
