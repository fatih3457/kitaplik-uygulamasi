package com.kitaplik.backend.service;

import com.kitaplik.backend.model.Book;
import com.kitaplik.backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Tüm kitapları getirir
    // BookService.java içinde
    public List<Book> getAllBooks() {
        List<Book> booksFromDb = bookRepository.findAll();
        System.out.println("--- Veritabanından " + booksFromDb.size() + " adet kitap bulundu. ---");
        return booksFromDb;
    }

    // Yeni bir kitap oluşturur (addBook ile aynı işi yaptığı için birleştirildi)
    public Book createBook(Book newBook) {
        // ID'nin null olduğundan emin olarak yeni bir kayıt olduğunu garantileyebiliriz.
        // newBook.setId(null); // Gerekirse bu satır eklenebilir.
        return bookRepository.save(newBook);
    }

    // Belirtilen ID'ye sahip kitabı günceller
    public Optional<Book> updateBook(Long id, Book updatedBookDetails) {
        // 1. Güncellenecek kitabı ID'si ile veritabanında ara
        return bookRepository.findById(id)
                .map(existingBook -> {
                    // 2. Kitap bulunduysa, bilgilerini güncelle
                    existingBook.setTitle(updatedBookDetails.getTitle());
                    existingBook.setAuthor(updatedBookDetails.getAuthor());
                    existingBook.setPublicationYear(updatedBookDetails.getPublicationYear());
                    existingBook.setGenre(updatedBookDetails.getGenre());
                    // 3. Güncellenmiş kitabı kaydet
                    return bookRepository.save(existingBook);
                });
        // .map() sayesinde, kitap bulunamazsa otomatik olarak boş Optional döner.
    }

    // Belirtilen ID'ye sahip kitabı siler
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true; // Silme işlemi başarılı
        }
        return false; // Silme işlemi başarısız (kitap bulunamadı)
    }
}