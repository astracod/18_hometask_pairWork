package com.newspring.delivery.token;


import com.newspring.delivery.dto.optionsDto.usersDto.LoginForTokenResponse;
import com.newspring.delivery.entities.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.newspring.delivery.configuration.SecurityConfig.key;


@Data
@Slf4j
@RequiredArgsConstructor
public class JwtImpl {

    private Long id;
    private String roleName;
    private String ip;

    public JwtImpl(User user) {
        id = user.getId();
        roleName = user.getRoleName();
    }

    public String getToken() {
        Claims claims = Jwts.claims();
        claims.put("userId", id);
        claims.put("roleName", roleName);
        claims.put("userIp", ip);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .compact();
    }

    /**
     * пробный, не проверенный. Не смог вызвать в контроллере
     *
     * @param user
     * @return
     */
    public Claims parseToken(LoginForTokenResponse user) {
        Jws<Claims> jws = null;
        Claims claims = null;
        String token = user.getToken();

        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            claims = jws.getBody();
            claims.get("userId");
            claims.get("roleName");
            claims.get("userIp");
        } catch (JwtException ex) {
            ex.printStackTrace();
        }
        return claims;
    }
}
