package com.ecommerce.config.security;

import com.ecommerce.exception.AuthenticationException;
import com.ecommerce.exception.EErrorType;
import com.ecommerce.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Configuration
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenManager jwtTokenManager;
    @Autowired
    JwtUserDetail jwtUserDetail;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeaderBearerToken = request.getHeader("Authorization");  //bu string request içinde var sabit
        if(authHeaderBearerToken!= null && authHeaderBearerToken.startsWith("Bearer ")  //üstteki Bearar [Token] dan dolayı
            && SecurityContextHolder.getContext().getAuthentication() == null
        ){
            String token = authHeaderBearerToken.substring(7);

            Optional<Long> id = jwtTokenManager.validateToken(token);

            if(id.isEmpty())
                throw new AuthenticationException(EErrorType.INVALID_TOKEN);

            UserDetails userDetails =jwtUserDetail.getUserByAuthId(id.get());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null,userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request,response);
    }
}
