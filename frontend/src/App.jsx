import React from 'react';
import { Routes, Route } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';

// Navbar component'ini import ediyoruz
import Navbar from './components/Navbar';

function App() {
    return (
        <> {/* React Fragment: Gereksiz bir div oluşturmamak için kullanılır */}
            <Navbar /> {/* Navbar'ı en üste, Rotaların dışına koyuyoruz */}

            <div className="container">
                {/* Rotaların tanımlandığı alan */}
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register" element={<RegisterPage />} />
                </Routes>
            </div>
        </>
    );
}

export default App;