package com.projectTutoriel.AppUser.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectTutoriel.AppUser.entities.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthentifationFilter extends UsernamePasswordAuthenticationFilter {

    AuthenticationManager authenticationManager;

    public JWTAuthentifationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            AppUser user = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);

           return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPwd()));

        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User user = (User)authResult.getPrincipal();

        List<String> roles = new ArrayList<>();

        authResult.getAuthorities().forEach(a->{
            roles.add(a.getAuthority());
        });

        String JWT = com.auth0.jwt.JWT.create()
                        .withIssuer(request.getRequestURI())
                        .withSubject(user.getUsername())
                        .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
                        .withExpiresAt(new Date(System.currentTimeMillis()*10*24*3600))
                        .sign(Algorithm.HMAC256(securityParam.PRIVATE_KEY));

        response.addHeader(securityParam.JWT_HEADER, securityParam.TOKEN_PREFIX + JWT);

    }
}
