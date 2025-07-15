package com.kitaplik.backend.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private int publicationYear;
    private String genre; // Alan adımız 'genre' olarak güncellendi.

    // Gerekli constructor'lar (Boş olan JPA için gereklidir)
    public Book() {
    }

    // Parametreli constructor'ı da güncelleyelim.
    public Book(Long id, String title, String author, int publicationYear, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.genre = genre; // Parametre adı da 'genre' oldu.
    }

    // --- MANUEL GETTER VE SETTER METOTLARI (İSİMLERİ DÜZELTİLDİ) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    // --- BURASI TAMAMEN GÜNCELLENDİ ---
    // Metot adları artık 'genre' alanına uygun
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    // ------------------------------------


    // equals, hashCode ve toString metotları da güncellendi.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return publicationYear == book.publicationYear && Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, publicationYear, genre);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                ", genre='" + genre + '\'' + // Burası da 'genre' olarak güncellendi.
                '}';
    }
}