package com.edgar.e_commerce_shop_backend.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.edgar.e_commerce_shop_backend.model.LocalUser;
import com.edgar.e_commerce_shop_backend.model.dao.LocalUserDAO;
import com.edgar.e_commerce_shop_backend.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private LocalUserDAO localUserDAO;

    public JWTRequestFilter(JWTService jwtService, LocalUserDAO localUserDAO) {
        this.jwtService = jwtService;
        this.localUserDAO = localUserDAO;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try {
                String userName = jwtService.getUserName(token);
                Optional<LocalUser> localUserOptional = localUserDAO.findByUsernameIgnoreCase(userName);
                if (localUserOptional.isPresent()) {
                    LocalUser localUser = localUserOptional.get();
                    if (localUser.isEmailVerified()) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                localUser, null, new ArrayList<>());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (JWTDecodeException ex) {

            }
        }
        filterChain.doFilter(request, response);
    }
}
