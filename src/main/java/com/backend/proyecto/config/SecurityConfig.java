package com.backend.proyecto.config;

import com.backend.proyecto.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

//Configuración de seguridad de Spring Security con JWT
//Define qué endpoints requieren autenticación y qué roles pueden acceder
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final CorsConfigurationSource corsConfigurationSource;

        // Constructor para inyección de dependencias
        public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                        CorsConfigurationSource corsConfigurationSource) {
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
                this.corsConfigurationSource = corsConfigurationSource;
        }

        // Bean para encriptar contraseñas con BCrypt
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // Bean para el AuthenticationManager (usado en login)
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        // Configuración principal de seguridad
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                // Deshabilitar CSRF (no necesario para APIs REST stateless)
                                .csrf(csrf -> csrf.disable())

                                // Configurar CORS
                                .cors(cors -> cors.configurationSource(corsConfigurationSource))

                                // Configurar autorización de endpoints
                                .authorizeHttpRequests(auth -> auth
                                                // Endpoints públicos (sin autenticación)
                                                .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                                                .requestMatchers("/api/auth/me").authenticated()
                                                .requestMatchers(HttpMethod.GET, "/api/proyectos/publicos").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/usuarios/programadores")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/programadores/**").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/horarios/**").permitAll()

                                                .requestMatchers("/actuator/**").permitAll()
                                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**",
                                                                "/swagger-ui.html")
                                                .permitAll()

                                                // Endpoints de USUARIOS
                                                .requestMatchers(HttpMethod.GET, "/api/usuarios/{id}")
                                                .hasAnyRole("ADMIN", "PROGRAMMER", "EXTERNAL")
                                                .requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}")
                                                .hasAnyRole("ADMIN", "PROGRAMMER", "EXTERNAL")
                                                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                                                .requestMatchers("/api/reportes/**").hasAnyRole("ADMIN", "PROGRAMMER")
                                                .requestMatchers("/api/dashboard/**").hasAnyRole("ADMIN", "PROGRAMMER")

                                                // Endpoints de PROGRAMADOR
                                                .requestMatchers(HttpMethod.POST, "/api/programadores").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.PUT, "/api/programadores/me/**")
                                                .hasRole("PROGRAMMER")
                                                .requestMatchers(HttpMethod.DELETE, "/api/programadores/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers("/api/portafolios/**")
                                                .hasAnyRole("PROGRAMMER", "ADMIN")
                                                .requestMatchers("/api/proyectos/**").hasAnyRole("PROGRAMMER", "ADMIN")
                                                .requestMatchers("/api/horarios/**").hasAnyRole("PROGRAMMER", "ADMIN")

                                                // Endpoints de ASESORÍAS
                                                .requestMatchers(HttpMethod.POST, "/api/asesorias").hasRole("EXTERNAL")
                                                .requestMatchers(HttpMethod.GET, "/api/asesorias/mias")
                                                .hasRole("EXTERNAL")
                                                .requestMatchers("/api/asesorias/programador/**").hasRole("PROGRAMMER")
                                                .requestMatchers("/api/asesorias/**")
                                                .hasAnyRole("ADMIN", "PROGRAMMER", "EXTERNAL")

                                                // Cualquier otro endpoint requiere autenticación
                                                .anyRequest().authenticated())

                                // Configurar sesión como STATELESS (JWT no usa sesiones)
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                // Agregar filtro JWT antes del filtro de autenticación por defecto
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
