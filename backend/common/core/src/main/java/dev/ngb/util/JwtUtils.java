package dev.ngb.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public final class JwtUtils {

    private JwtUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateToken(
            String username,
            Map<String, Object> extraClaims,
            PrivateKey privateKey,
            long expirationMs
    ) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(privateKey) // RS256 inferred
                .compact();
    }

    public static String generateToken(
            String username,
            PrivateKey privateKey,
            long expirationMs
    ) {
        return generateToken(username, Map.of(), privateKey, expirationMs);
    }

    public static boolean isTokenValid(
            String token,
            String expectedUsername,
            PublicKey publicKey
    ) {
        return expectedUsername.equals(extractUsername(token, publicKey))
                && !isTokenExpired(token, publicKey);
    }

    public static String extractUsername(String token, PublicKey publicKey) {
        return extractClaim(token, Claims::getSubject, publicKey);
    }

    public static Date extractExpiration(String token, PublicKey publicKey) {
        return extractClaim(token, Claims::getExpiration, publicKey);
    }

    public static <T> T extractClaim(
            String token,
            Function<Claims, T> resolver,
            PublicKey publicKey
    ) {
        Claims claims = extractAllClaims(token, publicKey);
        return resolver.apply(claims);
    }

    private static Claims extractAllClaims(String token, PublicKey publicKey) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private static boolean isTokenExpired(String token, PublicKey publicKey) {
        return extractExpiration(token, publicKey).before(new Date());
    }
}
