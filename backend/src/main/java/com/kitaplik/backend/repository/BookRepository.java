package com.kitaplik.backend.repository;

import com.kitaplik.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Bu anotasyonu eklemek iyi bir pratiktir, Spring Bean'i olduğunu netleştirir.
public interface BookRepository extends JpaRepository<Book, Long> {
    // İçi boş kalacak.
}