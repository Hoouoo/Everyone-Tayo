package team.sw.everyonetayo.zebal;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    private Key key;

    public JwtUtil(String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(Long id, String name) {


        String token = Jwts.builder()
                .claim("userId", id)
                .claim("name", name)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }
}