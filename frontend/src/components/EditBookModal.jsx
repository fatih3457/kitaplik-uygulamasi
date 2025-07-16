import React, { useState, useEffect } from 'react';

const EditBookModal = ({ book, onClose, onSave }) => {
    // Formun state'ini, dışarıdan gelen 'book' prop'u ile başlatıyoruz.
    const [formData, setFormData] = useState({ ...book });

    // Dışarıdan gelen 'book' prop'u her değiştiğinde (yeni bir düzenle butonuna basıldığında)
    // formun state'ini de güncelle. Bu, modal'ın her zaman doğru kitapla açılmasını sağlar.
    useEffect(() => {
        setFormData({ ...book });
    }, [book]);


    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const updatedBookData = {
            ...formData,
            publicationYear: parseInt(formData.publicationYear, 10)
        };
        onSave(book.id, updatedBookData);
    };

    // Eğer 'book' prop'u null ise, hiçbir şey render etme (modal kapalıdır).
    if (!book) {
        return null;
    }

    return (
        // Bu yapı Bootstrap Modal'ın standart yapısıdır.
        // Arka planı karartan overlay
        <div className="modal-backdrop show">
            <div className="modal show" style={{ display: 'block' }} tabIndex="-1">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">Kitabı Düzenle: {book.title}</h5>
                            <button type="button" className="btn-close" onClick={onClose}></button>
                        </div>
                        <div className="modal-body">
                            <form onSubmit={handleSubmit}>
                                <div className="mb-3">
                                    <label htmlFor="title" className="form-label">Başlık</label>
                                    <input type="text" className="form-control" name="title" value={formData.title} onChange={handleChange} />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="author" className="form-label">Yazar</label>
                                    <input type="text" className="form-control" name="author" value={formData.author} onChange={handleChange} />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="publicationYear" className="form-label">Yayın Yılı</label>
                                    <input type="number" className="form-control" name="publicationYear" value={formData.publicationYear} onChange={handleChange} />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="genre" className="form-label">Tür</label>
                                    <input type="text" className="form-control" name="genre" value={formData.genre} onChange={handleChange} />
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" onClick={onClose}>Kapat</button>
                            <button type="button" className="btn btn-primary" onClick={handleSubmit}>Değişiklikleri Kaydet</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default EditBookModal;