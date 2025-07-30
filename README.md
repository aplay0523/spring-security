#[노션](https://www.notion.so/API-Server-23d9271921f480eebd55d94db1009e9e)

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


# Swagger API List
## public
* register /post public/register
* login /post public/get-token

## Defailt
* updateUser /post /dataHub/user
* getUser /get /dataHub/user/{uuid}
* getUsers /get /dataHub/users
* deleteUser /delete /dataHub/user/{uuid}
