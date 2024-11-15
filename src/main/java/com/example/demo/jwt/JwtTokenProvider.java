package com.example.demo.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKeyString;

    @Value("${jwt.token-validity-in-seconds}")
    private long validityInMilliseconds;

    //jwt서명에 사용할 SecretKey생성
    private SecretKey getSecretKey() {
        // secretKeyString을 바이트 배열로 변환 후 SecretKey 객체 생성
        return Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    //토큰 생성
    public String createToken(String memberName,long validityInMilliseconds){

        //JWT(Json Web Token)에서 payload 부분에 포함되는 claims 객체를 생성하고, JWT에 사용자에 대한 정보를 설정하는 역할
        //JWT는 보통 Header, Payload, Signature로 구성
        //memberName대신 고유 값 넣어도 됨.
        Claims claims= Jwts.claims().setSubject(memberName);
        Date now=new Date();
        Date validity=new Date(now.getTime()+validityInMilliseconds*1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSecretKey(),SignatureAlgorithm.HS256)
                .compact();

    }

    public Map<String, String> generateTokens(String memberName) {
        String accessToken = createToken(memberName, 3600); // 1 hour validity
        String refreshToken = createToken(memberName, 86400); // 24 hours validity

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }


    //토큰에서 사용자 정보 추출
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT token");
            return false;
        }
    }

}
