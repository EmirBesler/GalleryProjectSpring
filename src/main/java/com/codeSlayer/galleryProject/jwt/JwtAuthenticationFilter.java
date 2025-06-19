package com.codeSlayer.galleryProject.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    //token ile istek geldiğinde ilk olarak buraya gelir.burada token ile kullanıcı incelenir!
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header;
        String token;
        String username;
        header = request.getHeader("Authorization");
        if (header == null) {
            filterChain.doFilter(request, response);
            return;
        }
        token = header.substring(7);
        try {
            username = jwtService.getUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null && jwtService.isTokenExpired(token) && request.getRemoteAddr().equals(jwtService.getMapAttributeFromToken(token,"ip address"))) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(userDetails);
                    //Kişiyi artık SecurityContext'te koyabiliriz.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            System.out.println("genel bir hata oluştu");
        }
        filterChain.doFilter(request, response);
    }
}