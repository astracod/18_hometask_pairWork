package com.newspring.delivery.token;

import com.newspring.delivery.entities.user.UserFromTokenAfterChecking;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;


import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.List;


@Data
@RequiredArgsConstructor
public class JwtImpl {


    private String header;
    private String payload;
    private byte[] signature;
    private Long id;
    private String roleName;

    public JwtImpl(List<UserFromTokenAfterChecking> u) {
        this.header = "{\"alg\":\"HS256\"}";
        this.payload = setPayload(u);
    }

    KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

    public String setPayload(List<UserFromTokenAfterChecking> u) {
        for (UserFromTokenAfterChecking user : u) {
            id = user.getId();
            roleName = user.getRoleName();
        }
        return "{\"userId\":\"id\",\"roleName\":\"roleName\"}";
    }

    public String getToken() {
        String jws = "";
        String encodedHeader = Arrays.toString(Base64.encodeBase64(getHeader().getBytes(StandardCharsets.UTF_8)));
        String encodedClaims = Arrays.toString(Base64.encodeBase64(getPayload().getBytes(StandardCharsets.UTF_8)));
        String concatenated = encodedHeader + "." + encodedClaims;
        jws = Jwts.builder()
                .setSubject(concatenated)
                .signWith(keyPair.getPrivate())
                .compact();
        return jws;
    }

    public String qwerty() {
        return String.valueOf(Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic()) // <---- publicKey, not privateKey
                .build()
                .parseClaimsJws(getToken()));
    }
}
