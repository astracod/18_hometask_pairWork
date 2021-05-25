package com.newspring.delivery.security;


import com.newspring.delivery.configuration.properties.JwtProperties;
import com.newspring.delivery.entities.user.User;
import com.newspring.delivery.exceptions.JwtAuthenticationException;
import com.newspring.delivery.utils.RequestUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;


@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String getToken(User u) {
        Claims claims = Jwts.claims();
        claims.put("userId", u.getId());
        claims.put("roleId", u.getRoleId());
        claims.put("userIp", RequestUtils.getUserIp());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        String secret = jwtProperties.getSecret();
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    private boolean validateToken(String token) {
        try {
            Claims claimsJws = getClaims(token);
            return RequestUtils.getUserIp().equals(claimsJws.get("userIp"));
        } catch (Exception e) {
            throw new JwtAuthenticationException("Token invalid");
        }
    }


    /**
     *  AuthenticationProvider функционал метода возвращающего Authentication
     *  UserDetailsService функционал возвращающий UserDetails
     * @param token
     * @return
     */
    public Authentication authentication(String token) {
        if (!validateToken(token)){
            throw new JwtAuthenticationException("Token not valid");
        }
        UserDetails userDetails = new JwtUserDetails(getClaims(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}






















