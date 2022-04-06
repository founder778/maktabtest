package com.company.util;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {
    private final static String secretKey = "CODER";

    public static String createJwtById(Integer id) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.setSubject(String.valueOf(id));
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)));
        jwtBuilder.setIssuer("Maktab");

        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public static Integer decodeJwt(String jwt) {
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = (Claims) jws.getBody();
        String id = claims.getSubject();

        return Integer.parseInt(id);
    }
}
