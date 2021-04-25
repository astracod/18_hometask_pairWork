package com.newspring.delivery.services;


import com.newspring.delivery.configuration.SecurityConfig;
import com.newspring.delivery.entities.user.User;
import com.newspring.delivery.utils.RequestUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;


@Service
public class JwtService {

    public String getToken(User u) {
        Claims claims = Jwts.claims();
        claims.put("userId", u.getId());
        claims.put("roleId", u.getRoleId());
        claims.put("userIp", RequestUtils.getUserIp());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SecurityConfig.key)
                .compact();
    }
}
