package com.kitaplik.backend.config;

import com.kitaplik.backend.service.JwtService;
import com.kitaplik.backend.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Gelen isteğin başlığından (header) "Authorization" bilgisini al
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Eğer Authorization başlığı yoksa veya "Bearer " ile başlamıyorsa,
        // bu isteğin JWT ile ilgisi yoktur, filtre zincirindeki bir sonraki adıma geç.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " ifadesini (7 karakter) atlayarak sadece token'ın kendisini al
        jwt = authHeader.substring(7);

        try {
            // Token'dan kullanıcı adını (username) çıkar
            username = jwtService.extractUsername(jwt);

            // Eğer kullanıcı adı varsa VE bu kullanıcı için henüz bir kimlik doğrulama işlemi yapılmadıysa
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Veritabanından kullanıcı bilgilerini yükle
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Token'ın bu kullanıcı için geçerli olup olmadığını kontrol et
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Eğer token geçerliyse, Spring Security için bir kimlik doğrulama token'ı oluştur
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // Şifre bilgisine gerek yok, çünkü JWT ile doğrulandı
                            userDetails.getAuthorities()
                    );
                    // Oluşturulan token'a isteğin detaylarını ekle
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    // Son olarak, bu kimlik doğrulama bilgisini Spring Security'nin ana context'ine yerleştir.
                    // Bu andan itibaren bu istek, kimliği doğrulanmış bir kullanıcı tarafından yapılmış sayılır.
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Token işlenirken bir hata olursa (süre dolması, imza hatası vb.),
            // hatayı konsola yazdırıp, kimlik doğrulaması yapmadan devam et.
            // Bu, güvenlik katmanının isteği reddetmesini sağlar.
            System.err.println("JWT token'ı işlenirken bir hata oluştu: " + e.getMessage());
        }


        // Filtre zincirindeki bir sonraki adıma devam et
        filterChain.doFilter(request, response);
    }
}