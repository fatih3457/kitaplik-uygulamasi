import React, { createContext, useState, useEffect, useContext } from 'react';
import axios from 'axios';

// 1. Context'i oluşturuyoruz. Başlangıç değeri null.
const AuthContext = createContext(null);

// 2. Provider Component'ini oluşturuyoruz. Bu component tüm uygulamayı sarmalayacak.
export const AuthProvider = ({ children }) => {
    // state'lerimizi tanımlıyoruz. Token'ı localStorage'dan okuyarak başlatıyoruz.
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(localStorage.getItem('token'));

    // Bu useEffect, token state'i her değiştiğinde çalışır.
    useEffect(() => {
        console.log("AuthContext: Token değişti. Yeni token:", token);
        if (token) {
            const bearerToken = `Bearer ${token}`;
            console.log("AuthContext: Axios header'ı ayarlanıyor:", bearerToken); // <-- YENİ LOG

            // Axios varsayılan header'ını ayarla
            axios.defaults.headers.common['Authorization'] = bearerToken;

            // localStorage'ı güncelle
            localStorage.setItem('token', token);
        } else {
            console.log("AuthContext: Token yok. Axios header'ı ve localStorage temizleniyor."); // <-- YENİ LOG
            delete axios.defaults.headers.common['Authorization'];
            localStorage.removeItem('token');
        }
    }, [token]);

    // ... login, logout fonksiyonları aynı ...// Bağımlılık dizisinde [token] olduğu için sadece token değiştiğinde çalışır.


    // Giriş yapma fonksiyonu
    const login = async (loginRequest) => {
        // LoginPage'den gelen isteği alıp backend'e gönderiyoruz.
        const response = await axios.post('http://localhost:8081/api/auth/login', loginRequest);
        if (response.data && response.data.token) {
            // Backend'den token gelirse, setToken state'ini güncelliyoruz.
            setToken(response.data.token);
            // TODO: Gelen token'dan kullanıcı bilgilerini decode edip setUser ile state'i güncelleyebiliriz.
            return response;
        }
        throw new Error("Geçersiz cevap alındı");
    };

    // Çıkış yapma fonksiyonu
    const logout = () => {
        setUser(null);
        setToken(null); // setToken'ı null yapmak yukarıdaki useEffect'i tetikler ve her şey temizlenir.
    };

    // Context aracılığıyla paylaşacağımız değerler ve fonksiyonlar
    const value = {
        user,
        token,
        login,
        logout
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

// 3. Bu custom hook, başka component'lerin context'e kolayca erişmesini sağlar.
export const useAuth = () => {
    return useContext(AuthContext);
};