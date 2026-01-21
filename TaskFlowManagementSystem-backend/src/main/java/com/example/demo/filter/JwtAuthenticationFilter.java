package com.example.demo.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.secure.KeyInitializer;
import com.example.demo.secure.CustomUserDetails;
import com.example.demo.exception.TaskflowException;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;

import java.io.IOException;
import java.security.PublicKey;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private KeyInitializer keyInitializer;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");

        // 1. 檢查 Header 格式。如果為空、不是 Bearer 開頭、或是 "Bearer null"，直接放行
        if (authHeader == null || !authHeader.startsWith("Bearer ") || authHeader.length() <= 11) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            if (keyInitializer.getKeyPair() == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "金鑰未初始化");
                return;
            }

            PublicKey publicKey = keyInitializer.getKeyPair().getPublic();

            Claims claims = Jwts.parser()
                                .verifyWith(publicKey)
                                .build()
                                .parseSignedClaims(token)
                                .getPayload();

            String userIdStr = claims.getSubject();

            if (userIdStr != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Long userId = Long.parseLong(userIdStr);
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new TaskflowException("用戶不存在"));

                CustomUserDetails userDetails = new CustomUserDetails(user);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            System.out.println("Filter 正在處理路徑: " + request.getRequestURI());
            filterChain.doFilter(request, response);

        } catch (Exception e) {
           
        	SecurityContextHolder.clearContext();
       	 	filterChain.doFilter(request, response);
            }
        }
    
}