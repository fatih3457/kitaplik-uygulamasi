import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext'; // Doğru import yolu

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);

    const navigate = useNavigate();
    // AuthContext'ten ihtiyacımız olan 'login' fonksiyonunu alıyoruz.
    const { login } = useAuth();

    const handleLogin = async (event) => {
        event.preventDefault();
        setError(null);

        try {
            // Artık axios'u doğrudan burada kullanmıyoruz.
            // Sadece context'ten aldığımız login fonksiyonunu çağırıyoruz.
            await login({ username, password });

            // Başarılı girişten sonra kullanıcıyı ana sayfaya yönlendiriyoruz.
            navigate('/');

        } catch (err) {
            // Context'teki login fonksiyonu hata fırlatırsa, onu burada yakalıyoruz.
            setError('Kullanıcı adı veya şifre hatalı.');
            console.error("Giriş hatası:", err);
        }
    };

    return (
        <div className="row justify-content-center">
            <div className="col-md-6">
                <h2>Giriş Yap</h2>
                <form onSubmit={handleLogin}>
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

                    <button type="submit" className="btn btn-success">Giriş Yap</button>
                </form>
            </div>
        </div>
    );
};

export default LoginPage;