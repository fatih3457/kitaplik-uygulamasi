package com.kitaplik.backend.config;

import com.kitaplik.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Bu sınıfın bir Spring Bean'i olduğunu belirtir
public class JwtAuthFilter extends OncePerRequestFilter { // Her istekte sadece bir kez çalışmasını sağlar

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 1. Authorization header'ını al
        final String authHeader = request.getHeader("Authorization");

        // 2. Header yoksa veya "Bearer " ile başlamıyorsa, isteği sonraki filtreye devam ettir
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. "Bearer " kısmını atıp sadece token'ı al
        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);

        // 4. Token'dan kullanıcı adı alındıysa VE henüz güvenlik konteksinde bir kimlik yoksa
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 5. Veritabanından kullanıcı detaylarını al
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // 6. Token geçerli mi diye kontrol et
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // 7. Token geçerliyse, Spring Security için bir AuthenticationToken oluştur
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // credentials (şifre) artık gerekli değil
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 8. Oluşturulan kimliği Security Context'e yerleştir
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 9. İsteği zincirdeki bir sonraki filtreye devam ettir
        filterChain.doFilter(request, response);
    }
}