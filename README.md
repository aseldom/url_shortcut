# url_shortcut

Данный проект представляет web-приложение по выдаче сокращенных ссылок.

---
## Данное web-приложение реализует следующий функционал:
- регистрацию сайта (получение регистрационных данных: логин, пароль)
- авторизацию/аутентификацию (JWT)
- регистрацию URL и получение преобразованного URL (код)
- получение оригинального URL по переданному коду (данная операция не требует авторизации)
- получение статистики запросов по каждому URL (с учетом регистрации)

---
## Стек технологий
* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* Liquibase
* MapStruct
* Lombok
* PostgreSQL
* H2 Data Base
* JWT authorization
* GitHub Action

---
## Запуск проекта
- Импортировать проект в IntelliJ IDEA
- В PostgreSQL создать БД url_shortcut
- Выполнить метод main
- Взаимодействие с приложением осуществляется с помощью REST запросов.

---
## Работа с приложением (пример):
1) Регистрация сайта:
   - (POST) URL: http://localhost:8080/site/registration/
     - тело JSON объекта {"site" : "job4j.ru"}
   - Ответ сервера: {registration : true/false, login: УНИКАЛЬНЫЙ_КОД, password : УНИКАЛЬНЫЙ_КОД}, где login и password необходимы для авторизации.
2) Авторизация.
    - (POST) URL: http://localhost:8080/login
        - тело JSON объекта {"login" : "УНИКАЛЬНЫЙ_КОД", "password" : "УНИКАЛЬНЫЙ_КОД""}
    - Ответ сервера (заголовок):
      - Authorization: Bearer "уникальный ключ"
        (В дальнейшем токен: Bearer "уникальный ключ" будет нужен для аутентификации при операциях: Регистрация URL, получение статистики)
3) Регистрация URL.
    - (POST) URL: http://localhost:8080//convert
      - тело JSON объекта {url: "https://google.com/mail"}
    - Ответ сервера (заголовок):
        - {code: УНИКАЛЬНЫЙ_КОД}
4) Переадресация. Выполняется без авторизации.
    - (GET) URL: http://localhost:8080//redirect/УНИКАЛЬНЫЙ_КОД
    - Ответ сервера (заголовок):
      - HTTP CODE - 302 REDIRECT URL
5) Статистика (считается количество вызовов каждого адреса).
    - (GET) URL: http://localhost:8080/statistic
   - Ответ сервера (JSON):
       - {
         {url : "https://job4j.ru/doogle.com/mail", total : 103}
         }
---
#### Взаимодействие с приложением удобнее осуществлять через [Postman](https://www.postman.com/)
---
test
---
#### Контакты для связи:
* email: a.seldom@gmail.com
* telegram: @aseldom       

             
