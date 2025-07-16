import React, { useState, useEffect } from 'react';
import axios from 'axios';
import BookList from '../components/BookList';
import AddBook from '../components/AddBook';
import EditBookModal from '../components/EditBookModal';
import { useAuth } from '../context/AuthContext';

const API_URL = 'http://localhost:8081/api/books';

const HomePage = () => {
    const [books, setBooks] = useState([]);
    const [editingBook, setEditingBook] = useState(null);
    const { token } = useAuth();

    // --- TÜM FONKSİYONLARIN DOĞRU TANIMLAMALARI BURADA ---

    // 1. Kitapları backend'den çeken fonksiyon
    const fetchBooks = async () => {
        try {
            const response = await axios.get(API_URL);
            setBooks(response.data);
        } catch (error) {
            console.error("Kitaplar getirilirken hata oluştu:", error);
        }
    };

    // 2. Component ilk yüklendiğinde kitapları çekmek için useEffect
    useEffect(() => {
        fetchBooks();
    }, []);

    // 3. Yeni kitap ekleme fonksiyonu
    const handleBookAdded = async (newBook) => {
        if (!token) {
            console.error("HATA: Token bulunamadı. İstek gönderilmedi.");
            return;
        }
        try {
            await axios.post(API_URL, newBook);
            fetchBooks(); // Ekleme sonrası listeyi yenile
        } catch (error) {
            console.error("Kitap eklenirken hata oluştu:", error.response ? error.response.data : error.message);
        }
    };

    // 4. Kitap silme fonksiyonu
    const handleBookDeleted = async (id) => {
        try {
            await axios.delete(`${API_URL}/${id}`);
            setBooks(books.filter(book => book.id !== id));
        } catch (error) {
            console.error("Kitap silinirken hata oluştu:", error);
        }
    };

    // 5. Kitap güncelleme (kaydetme) fonksiyonu
    const handleBookUpdated = async (id, updatedBook) => {
        try {
            await axios.put(`${API_URL}/${id}`, updatedBook);
            setEditingBook(null); // Modal'ı kapat
            fetchBooks(); // Listeyi yenile
        } catch (error) {
            console.error("Kitap güncellenirken hata oluştu:", error);
        }
    };

    // 6. Düzenleme modal'ını açan fonksiyon
    const handleEditClick = (book) => {
        setEditingBook(book);
    };

    // 7. Düzenleme modal'ını kapatan fonksiyon
    const handleModalClose = () => {
        setEditingBook(null);
    };

    // -------------------------------------------------------------

    return (
        <div>
            {token && <AddBook onBookAdded={handleBookAdded} />}

            <BookList
                books={books}
                isLoggedIn={!!token}
                onBookDeleted={handleBookDeleted}
                onEditClick={handleEditClick}
            />

            <EditBookModal
                book={editingBook}
                onClose={handleModalClose}
                onSave={handleBookUpdated}
            />
        </div>
    );
};

export default HomePage;