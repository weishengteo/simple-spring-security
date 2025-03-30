package com.example.test.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private static final String SECRET_KEY = "43276d7ddc7364d5904fda5ea44d3659a9d3a121353013654e991563bc0785d81502e2d33130672fcf3206894c8f34d752b1992dd36d464f684d012c48fb02f0f5ea3cbead0941aa9cc00b7044b87c9bc8a49c1b2a32871c0a67fc938c86e2f8d77e3aa8f3c17ddacf8998d6a37494ea39f3d23698776c61e5fe6739ae208bb0837504d655ff4eb7f4784933a2d6d7d8dffe12af7aa746711e4c1d22c2c4f85a02a82a138df88c666dd917a76d317ceed9b6da16e19236c7a8b3cfb3dbbd75ad5a1dc8af15630cf111a46960044cd5f1fbdd44ce0b99aeb8d8ab3b41c8fac7844bfccd336198c3d5be035b67c16de481233d37a0a44fb6b42f0e714e416f8b0c";
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder()
				.claims(claims)
				.subject(username)
				.issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignKey(), Jwts.SIG.HS256)
                .compact();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	
	public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
	
	private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
}
