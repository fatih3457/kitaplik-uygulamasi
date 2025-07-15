import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const RegisterPage = () => {
    // Form verilerini tutmak için state'ler
    const [name, setName] = useState('');
    const [username, setUsername] =  useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);

    // Başarılı kayıt sonrası yönlendirme yapmak için
    const navigate = useNavigate();

    const handleRegister = async (event) => {
        event.preventDefault(); // Formun sayfayı yenilemesini engelle
        setError(null); // Önceki hataları temizle

        // Backend'in register endpoint'inin beklediği nesneyi oluştur
        const registerRequest = {
            name: name,
            username: username,
            password: password
        };

        try {
            // Backend'e POST isteği at
            await axios.post('http://localhost:8081/api/auth/register', registerRequest);

            // Başarılı olursa, login sayfasına yönlendir
            navigate('/login');

        } catch (err) {
            // Hata olursa, kullanıcıya göster
            if (err.response && err.response.data) {
                // Backend'den gelen spesifik bir hata mesajı varsa onu kullan
                setError(err.response.data.message || 'Kayıt sırasında bir hata oluştu.');
            } else {
                // Genel bir hata mesajı
                setError('Kayıt başarısız. Lütfen daha sonra tekrar deneyin.');
            }
            console.error("Kayıt hatası:", err);
        }
    };

    return (
        <div className="row justify-content-center">
            <div className="col-md-6">
                <h2>Kayıt Ol</h2>
                <form onSubmit={handleRegister}>
                    <div className="mb-3">
                        <label htmlFor="name" className="form-label">İsim</label>
                        <input
                            type="text"
                            className="form-control"
                            id="name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="username" className="form-label">Kullanıcı Adı</label>
                        <input
                            type="text"
                            className="form-control"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="password" className="form-label">Şifre</label>
                        <input
                            type="password"
                            className="form-control"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    {error && <div className="alert alert-danger">{error}</div>}

                    <button type="submit" className="btn btn-primary">Kayıt Ol</button>
                </form>
            </div>
        </div>
    );
};

export default RegisterPage;