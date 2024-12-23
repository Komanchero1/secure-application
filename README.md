                   Задача «Безопасное приложение»
                              Описание

- Возьмите любое из уже реализованных приложений на Spring Boot, где есть контроллер и не менее одного метода-обработчика запросов на разные endpoint.

- Добавьте в приложение зависимость на spring-boot-starter-security.

- Реализуйте класс-наследник WebSecurityConfigurerAdapter так, чтобы:

- пользователь логинился через стандартную форму логина от Spring;
как минимум на один из endpoint вашего приложения можно было попасть без авторизации, а на все остальные — только после авторизации.
                    
                                                      
 Для выполнения задания использовал приложение из прошлого урока "demo_jpa_repositories"

  Наследоваться от класса "WebSecurityConfigurerAdapter" не получилось, так как этот клас удален из "SpringBoot" начиная с 5 версии.Использовал класс  "SecurityFilterChain".
 
  Реализовал класс "SecurityConfig" в котором метод "authManager"позволяет быстро настроить логин, пароль , роль пользователя.

  метод "securityFilterChain" настраивает цепочку безопасности , т.е. для какой роли какое разрешение.

  метод "passwordEncoder" предназначается для хеширования паролей и хранения их. С помощью реализованного интерфейса "BCryptPasswordEncoder" с паралей вычисляется хеш и хранится в иде набора символов понятного только для "BCrypt".