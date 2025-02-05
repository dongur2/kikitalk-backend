package com.kikitalk.chatting.global.security.jwt;

import com.kikitalk.chatting.global.security.jwt.service.CustomUserDetailsService;
import com.kikitalk.chatting.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j @Component
public class JwtProvider {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer";

    private final Key key;
    private final Long accessTokenExpTime;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.exp_time}") Long period, CustomUserDetailsService customUserDetailsService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = period;
        this.customUserDetailsService = customUserDetailsService;
    }

    //액세스 토큰 발급
    public String createAccessToken(User user) {
        return createToken(user, accessTokenExpTime);
    }

    //토큰 생성
    private String createToken(User user, Long period) {
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("snsId", user.getSnsId());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(period);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //헤더로부터 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //토큰을 이용해 인증 객체 생성
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(getSnsId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰 파싱
    public Long getUserId(String token) {
        return parseClaims(token).get("id", Long.class);
    }
    public String getSnsId(String token) {
        return parseClaims(token).get("snsId", String.class);
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    //토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("토큰이 유효하지 않습니다. - {}", e);
        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰입니다. - {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 토큰입니다. - {}", e);
        } catch (IllegalArgumentException e) {
            log.info("토큰 안의 내용이 비어있습니다. - {}", e);
        }
        return false;
    }
}
