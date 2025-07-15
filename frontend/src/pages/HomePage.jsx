import React, { useState, useEffect } from 'react';
import axios from 'axios';
import BookList from '../components/BookList';
import AddBook from '../components/AddBook';
import { useAuth } from '../context/AuthContext';

const API_URL = 'http://localhost:8081/api/books';

const HomePage = () => {
    const [books, setBooks] = useState([]);
    const { token } = useAuth();

    const fetchBooks = async () => {
        try {
            const response = await axios.get(API_URL);
            setBooks(response.data);
        } catch (error) {
            console.error("Kitaplar getirilirken hata oluştu:", error);
        }
    };

    useEffect(() => {
        fetchBooks();
    }, []);

    const handleBookAdded = async (newBook) => {
        try {
            await axios.post(API_URL, newBook);
            fetchBooks();
        } catch (error) {
            console.error("Kitap eklenirken hata oluştu:", error);
        }
    };

    const handleBookDeleted = async (id) => {
        try {
            await axios.delete(`${API_URL}/${id}`);
            setBooks(books.filter(book => book.id !== id));
        } catch (error) {
            console.error("Kitap silinirken hata oluştu:", error);
        }
    };

    return (
        <div>
            {/* Eğer token varsa (kullanıcı giriş yapmışsa) AddBook formunu göster */}
            {token && <AddBook onBookAdded={handleBookAdded} />}

            {/* BookList'e token bilgisini de prop olarak geçelim ki o da Sil butonunu buna göre göstersin */}
            <BookList books={books} onBookDeleted={handleBookDeleted} isLoggedIn={!!token} />
        </div>
    );
};

export default HomePage;