import React from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext'; // useAuth hook'unu import et

const Navbar = () => {
    // Context'ten ihtiyacımız olan token ve logout fonksiyonunu alıyoruz
    const { token, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout(); // Context'teki logout fonksiyonunu çağır
        navigate('/login'); // Kullanıcıyı login sayfasına yönlendir
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
            <div className="container">
                <Link className="navbar-brand" to="/">
                    Kitaplık
                </Link>
                <button
                    className="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarNav"
                    aria-controls="navbarNav"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                >
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav me-auto">
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/">
                                Ana Sayfa
                            </NavLink>
                        </li>
                    </ul>
                    <ul className="navbar-nav">
                        {/* KOŞULLU RENDER ETME: Token var mı, yok mu? */}
                        {token ? (
                            // Token VARSA (kullanıcı giriş yapmışsa)
                            <li className="nav-item">
                                <button className="btn btn-link nav-link" onClick={handleLogout}>
                                    Çıkış Yap
                                </button>
                            </li>
                        ) : (
                            // Token YOKSA (kullanıcı giriş yapmamışsa)
                            <>
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/login">
                                        Giriş Yap
                                    </NavLink>
                                </li>
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/register">
                                        Kayıt Ol
                                    </NavLink>
                                </li>
                            </>
                        )}
                    </ul>
                </div>
            </div>
        </nav>
    );
};

export default Navbar;