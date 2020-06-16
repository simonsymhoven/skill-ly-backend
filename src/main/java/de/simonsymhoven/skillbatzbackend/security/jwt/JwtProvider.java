package de.simonsymhoven.skillbatzbackend.security.jwt;

import de.simonsymhoven.skillbatzbackend.security.services.EmployeePrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
  private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

  @Value("${skillbatz.app.jwtSecret}")
  private String jwtSecret;

  @Value("${skillbatz.app.jwtExpiration}")
  private int jwtExpiration;

  public String generateJwtToken(Authentication authentication) {

    EmployeePrinciple employeePrinciple = (EmployeePrinciple) authentication.getPrincipal();

    final String authorities = authentication.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    return Jwts.builder()
      .setSubject((employeePrinciple.getUsername()))
      .setIssuedAt(new Date())
      .claim("authorities", authorities)
      .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
      .signWith(SignatureAlgorithm.HS512, jwtSecret)
      .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser()
      .setSigningKey(jwtSecret)
      .parseClaimsJws(token)
      .getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature -> Message: {} ", e);
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token -> Message: {}", e);
    } catch (ExpiredJwtException e) {
      logger.error("Expired JWT token -> Message: {}", e);
    } catch (UnsupportedJwtException e) {
      logger.error("Unsupported JWT token -> Message: {}", e);
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty -> Message: {}", e);
    }

    return false;
  }
}
