package com.kitaplik.backend.controller;

import com.kitaplik.backend.model.Book;
import com.kitaplik.backend.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET /api/books -> Tüm kitapları listeler
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // POST /api/books -> Yeni bir kitap oluşturur (Çakışma giderildi)
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book newBook) {
        Book createdBook = bookService.createBook(newBook);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    // PUT /api/books/{id} -> Belirtilen ID'ye sahip kitabı günceller
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookService.updateBook(id, bookDetails)
                .map(ResponseEntity::ok) // Kitap bulunduysa 200 OK ve kitabı döndür
                .orElse(ResponseEntity.notFound().build()); // Kitap bulunamadıysa 404 Not Found döndür
    }

    // DELETE /api/books/{id} -> Belirtilen ID'ye sahip kitabı siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookService.deleteBook(id)) {
            // Kitap başarıyla silindiyse 204 No Content döndür
            return ResponseEntity.noContent().build();
        } else {
            // Kitap bulunamadıysa 404 Not Found döndür
            return ResponseEntity.notFound().build();
        }
    }
}