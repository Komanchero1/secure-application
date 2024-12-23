package org.example.demo_jpa_repositories.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //метод настраивает цепочку фильтров безопасности
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> {
                            authz
                                    .requestMatchers("/api/persons/city/{city}").permitAll()// разрешение доступа по этому эндпоинту всем пользователям
                                    .requestMatchers("/api/persons/age/less-than/{age}").hasAuthority("user")// только пользователю с ролью "user"
                                    .requestMatchers("/api/persons/name/{name}/surname/{surname}").hasAuthority("admin")// только пользователю с ролью "admin"
                                    .anyRequest().authenticated();// все остальные запросы требуют аутентификации
                        }
                )
                .formLogin(withDefaults());// используется стандартная форма входа

        return http.build();//возвращает настроенную цепочку фильтров безопасности
    }

    //метод настраивает аутентификацию пользователей (задает пароль, логин, роль)
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        //этот объект позволяет добавлять пользователей и учетные данные
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .inMemoryAuthentication() //настройка аутентификации в памяти
                .withUser("admin")//добавление пользователя "admin"
                .password(passwordEncoder().encode("12345")) // пароль для "admin"
                .authorities("admin")//роль для "admin"
                .and()
                .withUser("user")//добавление пользователя "user"
                .password(passwordEncoder().encode("54321")) // пароль для "user"
                .authorities("user");//роль для "user"
        return authenticationManagerBuilder.build();
    }

    //метод для хеширования паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // используем BCrypt для хеширования паролей
    }
}
