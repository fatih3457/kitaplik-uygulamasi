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
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Yeni bir kitap oluşturur
    public Book createBook(Book newBook) {
        return bookRepository.save(newBook);
    }


    // Belirtilen ID'ye sahip kitabı günceller
    public Optional<Book> updateBook(Long id, Book updatedBookDetails) {
        // 1. Güncellenecek kitabı ID'si ile veritabanında ara
        Optional<Book> optionalBook = bookRepository.findById(id);

        // 2. Kitap bulunduysa, bilgilerini güncelle
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setTitle(updatedBookDetails.getTitle());
            existingBook.setAuthor(updatedBookDetails.getAuthor());
            existingBook.setPublicationYear(updatedBookDetails.getPublicationYear());
            existingBook.setIsbn(updatedBookDetails.getIsbn());

            // 3. Güncellenmiş kitabı kaydet ve geri döndür
            return Optional.of(bookRepository.save(existingBook));
        } else {
            // 4. Kitap bulunamadıysa, boş bir Optional döndür
            return Optional.empty();
        }
    }

    // Kitap silme
    public boolean deleteBook(Long id) {
        // 1. Silinecek kitabın var olup olmadığını kontrol et
        if (bookRepository.existsById(id)) {
            // 2. Kitap varsa sil
            bookRepository.deleteById(id);
            return true; // Silme işlemi başarılı
        }
        // 3. Kitap yoksa, silinemediğini belirt
        return false; // Silme işlemi başarısız (kitap bulunamadı)
    }
}