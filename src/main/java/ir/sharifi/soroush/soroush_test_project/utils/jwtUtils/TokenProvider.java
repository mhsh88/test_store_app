package ir.sharifi.soroush.soroush_test_project.utils.jwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ir.sharifi.soroush.soroush_test_project.base.service.BaseUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class TokenProvider {

    private final String secretKey;
    private final long tokenValidityInMilliseconds;

    private BaseUserDetailsService userDetailsService;

    public TokenProvider(JwtProperties config, BaseUserDetailsService userDetailsService) {
        this.secretKey = Base64.getEncoder().encodeToString(config.getSecret().getBytes());
        this.tokenValidityInMilliseconds = 1000 * config.getTokenValidityInSeconds();
        this.userDetailsService = userDetailsService;
    }

    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);

        JwtBuilder builder = Jwts.builder();


        return builder.setId(UUID.randomUUID().toString()).setSubject(username)
                .setIssuedAt(now).signWith(SignatureAlgorithm.HS512, this.secretKey)
                .setExpiration(validity).compact();
    }

    public Authentication getAuthentication(String token) {
        Claims body = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        String username = body.getSubject();
        UserDetails userDetails;
            userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}