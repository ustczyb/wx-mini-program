package com.tencent.wxcloudrun.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private static final String SECRET = "2a10slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";

    private static final String DEV_TOKEN = "dev_token";

    public static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    public static String generateToken(UserDetails userDetails) {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.builder()
                .setClaims(Collections.emptyMap())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Boolean validateClaims(Claims claims, UserDetails userDetails) {
        String openId = claims.getSubject();
        Date expiration = claims.getExpiration();
        return (openId.equals(userDetails.getUsername()) && !expiration.before(new Date()));
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        Claims claims = getAllClaimsFromToken(token);
        return validateClaims(claims, userDetails);
    }
}
