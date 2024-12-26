package org.example.demo_jpa_repositories.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


//класс обеспечивает базовую настройку безопасности для приложения
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)

public class SecurityConfig {

    /**
     * //метод настраивает цепочку фильтров безопасности(при использовании @EnableMethodSecurity не нужен)
     *
     * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     * http.authorizeHttpRequests(authz -> {
     * authz
     * .requestMatchers("/api/persons/city/{city}").permitAll()// разрешение доступа по этому эндпоинту всем пользователям
     * .requestMatchers("/api/persons/age/less-than/{age}").hasAuthority("user")// только пользователю с ролью "user"
     * .requestMatchers("/api/persons/name/{name}/surname/{surname}").hasAuthority("admin")// только пользователю с ролью "admin"
     * .anyRequest().authenticated();// все остальные запросы требуют аутентификации
     * }
     * )
     * .formLogin(withDefaults());// используется стандартная форма входа
     * <p>
     * return http.build();//возвращает настроенную цепочку фильтров безопасности
     * }
     */


    //метод для создания конфигурирования данных пользователей и управления ими
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = passwordEncoder();
        UserDetails user = User.builder()
                .username("Ivan")
                .password(passwordEncoder.encode("54321"))//задается и кодируется пароль
                .roles("READ")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("12345"))
                .roles("WRITE")
                .build();
        return new InMemoryUserDetailsManager(user, admin);//объект сохраняется в оперативной памяти
    }


    //метод для хеширования паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // используем BCrypt для хеширования паролей
    }
}
