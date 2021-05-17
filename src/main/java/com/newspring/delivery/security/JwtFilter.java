package com.newspring.delivery.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtService jwtService;

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (Objects.nonNull(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) request);
        if (token != null) {
            Authentication authentication = jwtService.authentication(token);
            if (authentication != null){
                SecurityContextHolder .getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}















