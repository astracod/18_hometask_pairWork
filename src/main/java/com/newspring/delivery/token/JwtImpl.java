package com.newspring.delivery.token;


import com.newspring.delivery.dto.optionsDto.usersDto.LoginForTokenResponse;
import com.newspring.delivery.entities.user.UserFromTokenAfterChecking;
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

    public JwtImpl(List<UserFromTokenAfterChecking> user) {
        user.stream().forEach(u -> u.getId());
        user.stream().forEach(u -> u.getRoleName());
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
     *  пробный, не проверенный. Не смог вызвать в контроллере
     * @param user
     * @return
     */
    public Jws<Claims> parseToken(LoginForTokenResponse user) {
        Jws<Claims> jws = null;
        String token = user.getToken();

        try {
            jws =  Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            Claims claims = jws.getBody();
            log.info("id : {}", claims.get("userId"));
            log.info("ip : {}", claims.get("roleName"));
            log.info("ip : {}", claims.get("userIp"));
        } catch (JwtException ex) {
            ex.printStackTrace();
        }
        return jws;
    }
}
