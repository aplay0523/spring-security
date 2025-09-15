# 상세정리
* [노션 정리](https://uk-study.notion.site/API-Server-26f09e879c048124b7bac9e25d2092c0?pvs=74)

## 1.개발 환경
 * Version: Java 17
 * IDE: IntelliJ
 * Framework: Spring Boot 3.5.3
 * ORM: Spring Data JPA
 * build: Gradle
 * 의존성 관리: Lombok
## 2.기술 스택
 * Server: wsl2 ubuntu, Docker
 * DataBase: PostgreSql
 * backEnd: Spring Boot Web(RESTful API)
 * Security:Spring Security, JWT(JJWT)
 * Swagger3.0: SpringDoc OpenAPI 2.7.0
 * TEST: JUnit 5, postman
 * CI/CD: GitHub Actions, Docker


# Swagger API List
## 1. public
### register
* url : /post public/register
### login
* url : /post public/get-token

## 2. Defailt
### updateUser
* url : /post /dataHub/user
### getUser
* url : /get /dataHub/user/{uuid}
### getUsers
* url : /get /dataHub/users
### deleteUser
* url : /delete /dataHub/user/{uuid}
