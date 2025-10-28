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

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/bbab6b30-9672-408c-93a0-835d863d39cb" />

# 설정
## Spring Security
* CSRF, 로그인폼 비활성화
* exceptionHandling에 authenticationEntryPoint 등록하여 인증 실패 시 처리
* "/dataHub/**" 특정 url은 ADMIN, MANAGER 권한 처리
* addFilterBefore로 JWT 인증 필터를 기존 필터보다 먼저 실행하여 토큰 기반 인증 처리
## JwtAuthenticationFilter
* 모든 요청에서 Authorization 체크
* 내장된 apiKey 인증
* 토큰을 받으면 validateToken을 통해 서명 검증, 만료 검사 이후 토큰에서 사용자 ID 추출하여 userDetailsService를 통해 UserDetails 로드
## Swagger
* addSecurityItem에 new SecurityRequirement bearerAuth, apiKeyAuth 인증 방법 등록
* GroupedOpenApi에 public, Defailt 보안 그룹 분류
* 앱 실행 시 FilterRegistrationBean로 등록하여 시큐리티 필터로 막히지 않도록 우선 순위 설정

# Spring Security 테스트를 위한 간단한 api 리스트
# Swagger API List
## 1. public(인증 불필요)
### register
* url : /post public/register
### login
* url : /post public/get-token

## 2. Defailt(jwt, apiKey 필요)
### updateUser
* url : /post /dataHub/user
### getUser
* url : /get /dataHub/user/{uuid}
### getUsers
* url : /get /dataHub/users
### deleteUser
* url : /delete /dataHub/user/{uuid}
