package com.api.dataHub.security.filter;

import com.api.dataHub.common.exception.InValidException;
import com.api.dataHub.common.exception.NotHeaderException;
import com.api.dataHub.security.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends GenericFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Value("${api.key}")
    private String secretKey;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletrequest, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletrequest;
        String token = jwtTokenProvider.resolevToken(request);
        String requestApiKey = request.getParameter("apiKeyAuth");
        String requestUri = request.getRequestURI();

        if(requestUri.contains("/dataHub/") && !(requestUri.matches("(.*?(get-token)).*"))) {

            if(!secretKey.equals(requestApiKey)) {
                throw new InValidException("API 토큰 인증 실패");
            }

            if(token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new NotHeaderException("인증정보 없음");
            }
        }

        chain.doFilter(request, response);
    }
}
