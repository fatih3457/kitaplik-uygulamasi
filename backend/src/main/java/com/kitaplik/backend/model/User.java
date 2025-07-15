package com.kitaplik.backend.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
@Table(name = "users") // Veritabanı tablo adı
public class User implements UserDetails { // Spring Security için UserDetails arayüzünü implemente ediyoruz

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // Kullanıcı adları benzersiz olmalı
    private String username;

    private String password;

    // --- Constructor'lar ---
    public User() {
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // --- Getter ve Setter'lar ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // --- UserDetails Arayüzünden Gelen Zorunlu Metotlar ---

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Bu projede rollerle (ADMIN, USER vs.) şimdilik ilgilenmiyoruz.
        // Bu yüzden boş bir liste döndürüyoruz.
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Hesabın süresinin dolup dolmadığını kontrol eder. Şimdilik hep true.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Hesabın kilitli olup olmadığını kontrol eder. Şimdilik hep true.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Kimlik bilgilerinin (şifre) süresinin dolup dolmadığını kontrol eder. Şimdilik hep true.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Kullanıcının aktif olup olmadığını kontrol eder. Şimdilik hep true.
        return true;
    }

    // --- equals, hashCode, toString ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}