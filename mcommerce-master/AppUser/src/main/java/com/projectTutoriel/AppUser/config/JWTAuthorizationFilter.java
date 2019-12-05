package com.projectTutoriel.AppUser.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest Request, HttpServletResponse Response, FilterChain filterChain) throws ServletException, IOException {

            String jwt = Request.getHeader(securityParam.JWT_HEADER);

            if (jwt==null || !jwt.startsWith(securityParam.TOKEN_PREFIX)){
                filterChain.doFilter(Request, Response);
                return;
            }

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(securityParam.PRIVATE_KEY)).build();

        DecodedJWT decodedJWT = verifier.verify(jwt.substring(securityParam.TOKEN_PREFIX.length()));

       String userName = decodedJWT.getSubject();

        List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        roles.forEach(rn->{
            ((ArrayList<GrantedAuthority>) authorities).add(new SimpleGrantedAuthority(rn));
        });

        UsernamePasswordAuthenticationToken User =  new UsernamePasswordAuthenticationToken(userName, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(User);

        filterChain.doFilter(Request, Response);
    }
}
