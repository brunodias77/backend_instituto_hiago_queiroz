package br.org.institutohiagoqueiroz.hiagoqueiroz.auth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}") // Injeta o valor da propriedade 'application.security.jwt.expiration' do arquivo de configuração
    private long jwtExpiration;


    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compara a data de expiração com a data atual.
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // Define as reivindicações adicionais.
                .setSubject(userDetails.getUsername()) // Define o nome de usuário como o assunto do token.
                .setIssuedAt(new Date(System.currentTimeMillis())) // Define a data de emissão do token.
                .setExpiration(new Date(System.currentTimeMillis() + 25 * 1000)) // Define a data de expiração do token (25 segundos a partir de agora).
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Assina o token usando a chave de assinatura e o algoritmo HS256.
                .compact(); // Gera o token JWT compactado.
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    private Key getSignInKey() {
        // Decodifica a chave secreta de Base64.
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes); // Cria uma chave de assinatura HMAC a partir dos bytes decodificados.
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()) // Define a chave de assinatura para verificar o token.
                .build()
                .parseClaimsJws(token) // Analisa o token e obtém o corpo das reivindicações.
                .getBody();
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
