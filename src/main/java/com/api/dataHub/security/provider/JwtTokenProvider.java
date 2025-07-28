package com.api.dataHub.security.provider;

import com.api.dataHub.controller.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${api.key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long tokenValidTime;
    private Key key;

    // UserDetailsService 인터페이스 주입
    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        String encodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        byte[] keyBytes = encodedSecretKey.getBytes(); // Base64 인코딩된 문자열의 바이트 배열
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUserId());
        claims.put("uuid", user.getUuid());
        claims.put("roles", user.getGroupRole());
        claims.put("userName", user.getUsername());

        Date now = new Date();
        Date expiration = new Date(now.getTime() + tokenValidTime);

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 발행 시간
                .setExpiration(expiration) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // HS256 알고리즘즘
               .compact();
    }

    public Authentication getAuthentication(String token) {
        // 토큰에서 사용자 ID를 추출하여 UserDetailsService를 통해 UserDetails 객체를 로드
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        // UserDetails와 권한을 기반으로 UsernamePasswordAuthenticationToken 생성
        // 비밀번호는 이미 인증된 상태이므로 빈 문자열 또는 null로 설정
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserId(String token) {
        // 서명 키를 사용하여 토큰 파싱 후, 클레임에서 subject(사용자 ID) 추출
        return Jwts.parserBuilder() // Jwts.parser() -> Jwts.parserBuilder()로 변경 (jjwt 0.10.0 이후 권장)
                .setSigningKey(key) // Key 객체 사용
                .build() // 파서 빌드
                .parseClaimsJws(token) // 토큰 파싱
                .getBody() // 클레임 바디 추출
                .getSubject(); // subject 클레임 추출
    }

    public String resolevToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = "";

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        return token;
    }

    public boolean validateToken(String jwtToken) {
        try {
            // 서명 키를 사용하여 토큰을 파싱하고 유효성을 검사합니다.
            Jws<Claims> claimsJws = Jwts.parserBuilder() // Jwts.parser() -> Jwts.parserBuilder()로 변경
                    .setSigningKey(key) // Key 객체 사용
                    .build()
                    .parseClaimsJws(jwtToken);
            // 토큰 만료 여부 확인
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
