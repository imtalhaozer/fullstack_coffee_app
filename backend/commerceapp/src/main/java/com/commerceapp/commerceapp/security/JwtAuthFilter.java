package com.commerceapp.commerceapp.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.commerceapp.commerceapp.service.JwtService;
import com.commerceapp.commerceapp.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{
    final private  JwtService jwtService;
    final private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                final String authHeader=request.getHeader("Authorization");
                final String token;
                //usermail actually
                final String userName;

                if(authHeader==null ||!authHeader.startsWith("Bearer ")){
                    filterChain.doFilter(request, response);
                    return;
                }
                token=authHeader.substring(7);
                userName=jwtService.extractUser(token);

                if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                    UserDetails user=userService.loadUserByUsername(userName);
                    if (jwtService.validateToken(token, user)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                filterChain.doFilter(request, response);
    }
    
}
