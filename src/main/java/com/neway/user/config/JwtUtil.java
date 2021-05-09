package com.neway.user.config;

import static java.util.Collections.emptyList;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class JwtUtil {

  // Método para crear el JWT y enviarlo al cliente en el header de la respuesta
  static void addAuthentication(HttpServletResponse res, String username) throws IOException {

    Date date = new Date();
    long t = date.getTime();
    Date expirationTime = new Date(t + 5000l);

    String token =
        Jwts.builder()
            .setSubject(username)
            // Hash con el que firmaremos la clave
            //.setExpiration(expirationTime)
            .signWith(SignatureAlgorithm.HS512, "N3w4yS3cr3t")
            .compact();

    // agregamos al encabezado el token
    res.addHeader("Authorization", "Bearer " + token);
    res.getWriter().print(token);
  }

  // Método para validar el token enviado por el cliente
  static Authentication getAuthentication(HttpServletRequest request) {

    try {
    // Obtenemos el token que viene en el encabezado de la peticion
    String token = request.getHeader("Authorization");

    // si hay un token presente, entonces lo validamos
    if (token != null) {
      String user =
          Jwts.parser()
              .setSigningKey("N3w4yS3cr3t")
              .parseClaimsJws(token.replace("Bearer", "")) // este metodo es el que valida
              .getBody()
              .getSubject();

      // Recordamos que para las demás peticiones que no sean /login
      // no requerimos una autenticacion por username/password
      // por este motivo podemos devolver un UsernamePasswordAuthenticationToken sin password
      return user != null ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
    }
    return null;
    } catch (SignatureException ex) {
      throw new BadCredentialsException("Bad Credentials");
    }
  }
}
