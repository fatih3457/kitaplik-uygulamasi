import React from 'react';

// Yeni 'onEditClick' prop'unu da alıyoruz.
const BookList = ({ books, onBookDeleted, isLoggedIn, onEditClick }) => {

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

                            {/* Sadece giriş yapıldıysa butonları göster */}
                            {isLoggedIn && (
                                <div className="btn-group">
                                    <button
                                        className="btn btn-warning btn-sm"
                                        // onClick olayı artık HomePage'deki handleEditClick'i tetikliyor
                                        // Parametre olarak tüm 'book' nesnesini gönderiyoruz
                                        onClick={() => onEditClick(book)}
                                    >
                                        Düzenle
                                    </button>
                                    <button
                                        className="btn btn-danger btn-sm"
                                        onClick={() => onBookDeleted(book.id)}>
                                        Sil
                                    </button>
                                </div>
                            )}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default BookList;