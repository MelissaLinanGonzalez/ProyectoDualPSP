package com.dualpsp.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad para la aplicación.
 * 
 * RA5 - Seguridad: Implementa autenticación y autorización usando
 * Spring Security con configuración moderna (SecurityFilterChain).
 * 
 * Usuarios en memoria:
 * - admin/admin123 (Rol: ADMIN) - Puede realizar todas las operaciones
 * - user/user123 (Rol: USER) - Solo puede realizar operaciones de lectura
 * 
 * Reglas de acceso:
 * - GET /api/productos/** -> Usuarios autenticados (USER, ADMIN)
 * - POST, PUT, DELETE /api/productos/** -> Solo ADMIN
 * 
 * @author Estudiante FP Dual
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura el codificador de contraseñas.
     * Se utiliza BCrypt para encriptar las contraseñas de forma segura.
     * 
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura los usuarios en memoria para pruebas.
     * 
     * Usuarios definidos:
     * - admin: Contraseña admin123, rol ADMIN
     * - user: Contraseña user123, rol USER
     * 
     * NOTA: En producción, usar una base de datos o LDAP para usuarios.
     * 
     * @param passwordEncoder Codificador de contraseñas
     * @return UserDetailsService con usuarios en memoria
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Usuario administrador - puede realizar todas las operaciones
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        // Usuario normal - solo puede consultar productos
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    /**
     * Configura la cadena de filtros de seguridad.
     * 
     * Configuración:
     * - HTTP Basic Authentication habilitado
     * - CSRF deshabilitado (para facilitar pruebas con curl/Postman)
     * - GET: Acceso para usuarios autenticados (USER y ADMIN)
     * - POST, PUT, DELETE: Solo ADMIN
     * 
     * @param http Objeto HttpSecurity para configurar
     * @return SecurityFilterChain configurado
     * @throws Exception Si hay error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF para permitir peticiones desde curl/Postman
                .csrf(csrf -> csrf.disable())

                // Configurar reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        // GET permitido para usuarios autenticados (USER y ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").hasAnyRole("USER", "ADMIN")

                        // POST, PUT, DELETE solo para ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("ADMIN")

                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated())

                // Habilitar HTTP Basic Authentication
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
