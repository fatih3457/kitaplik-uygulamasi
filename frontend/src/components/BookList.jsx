import React from 'react';

// Yeni 'isLoggedIn' prop'unu al
const BookList = ({ books, onBookDeleted, isLoggedIn }) => {
    if (!books) {
        return <p>Yükleniyor...</p>;
    }

    return (
        <div>
            <h2 className="my-4">Kitaplar</h2>
            {books.length === 0 ? (
                <p>Gösterilecek kitap bulunamadı.</p>
            ) : (
                <ul className="list-group">
                    {books.map(book => (
                        <li key={book.id} className="list-group-item d-flex justify-content-between align-items-center">
                            <div>
                                {book.title} - {book.author} ({book.publicationYear})
                                <br />
                                <small className="text-muted">Tür: {book.genre}</small>
                            </div>

                            {/* Eğer kullanıcı giriş yapmışsa Sil butonunu göster */}
                            {isLoggedIn && (
                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => onBookDeleted(book.id)}>
                                    Sil
                                </button>
                            )}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default BookList;