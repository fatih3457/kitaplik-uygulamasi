import React, { useState } from 'react';

const AddBook = ({ onBookAdded }) => {
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [publicationYear, setPublicationYear] = useState('');
    // 1. State değişkeni 'isbn' -> 'genre' olarak değiştirildi
    const [genre, setGenre] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();

        const newBook = {
            title,
            author,
            publicationYear: parseInt(publicationYear, 10),
            // 2. Backend'e gönderilen nesnenin alanı 'isbn' -> 'genre' oldu
            genre: genre
        };

        onBookAdded(newBook);

        // Formu temizleme
        setTitle('');
        setAuthor('');
        setPublicationYear('');
        // 3. Temizlenen state de 'setIsbn' -> 'setGenre' oldu
        setGenre('');
    };

    return (
        <div className="my-4">
            <h3>Yeni Kitap Ekle</h3>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="title" className="form-label">Başlık</label>
                    <input type="text" className="form-control" id="title" value={title} onChange={(e) => setTitle(e.target.value)} required />
                </div>
                <div className="mb-3">
                    <label htmlFor="author" className="form-label">Yazar</label>
                    <input type="text" className="form-control" id="author" value={author} onChange={(e) => setAuthor(e.target.value)} required />
                </div>
                <div className="mb-3">
                    <label htmlFor="publicationYear" className="form-label">Yayın Yılı</label>
                    <input type="number" className="form-control" id="publicationYear" value={publicationYear} onChange={(e) => setPublicationYear(e.target.value)} required />
                </div>

                {/* 4. Buradaki tüm 'isbn' referansları 'genre' olarak değiştirildi */}
                <div className="mb-3">
                    <label htmlFor="genre" className="form-label">Tür</label>
                    <input
                        type="text"
                        className="form-control"
                        id="genre"
                        value={genre}
                        onChange={(e) => setGenre(e.target.value)}
                        placeholder="Örn: Roman, Bilim Kurgu, Tarih..."
                    />
                </div>

                <button type="submit" className="btn btn-primary">Ekle</button>
            </form>
        </div>
    );
};

export default AddBook;