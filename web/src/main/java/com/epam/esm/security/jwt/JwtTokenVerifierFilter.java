package com.epam.esm.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.assertj.core.util.Strings;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifierFilter extends OncePerRequestFilter {
  private final JwtConfig jwtConfig;

  public JwtTokenVerifierFilter(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
    String tokenPrefix = jwtConfig.getTokenPrefix();
    if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(tokenPrefix)) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authorizationHeader.replace(tokenPrefix, "");
    try {
      SecretKey secretKey = jwtConfig.secretKey();
      Jws<Claims> claimsJws =
          Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
      Claims body = claimsJws.getBody();
      String login = body.getSubject();
      List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
      Set<GrantedAuthority> grantedAuthorities =
          authorities.stream()
              .map(m -> new SimpleGrantedAuthority(m.get("authority")))
              .collect(Collectors.toSet());
      Authentication authentication =
          new UsernamePasswordAuthenticationToken(login, null, grantedAuthorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (JwtException e) {
      throw new InsufficientAuthenticationException(
          String.format("The Token %s cannot be trusted", token));
    }
    filterChain.doFilter(request, response);
  }


}
