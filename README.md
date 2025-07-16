# Kitaplık Uygulaması (Full-Stack)

Bu proje, Spring Boot (Backend) ve React (Frontend) kullanılarak geliştirilmiş, JWT tabanlı kimlik doğrulama sistemine sahip modern bir web uygulamasıdır. Kullanıcıların kayıt olup giriş yapabildiği ve ortak bir kitaplıkta kitap ekleme, listeleme, güncelleme ve silme (CRUD) işlemlerini gerçekleştirebildiği bir platform sunar.

## ✨ Özellikler

- **Kullanıcı Yönetimi:**
  - Güvenli kullanıcı kaydı (şifreler hash'lenerek saklanır).
  - JWT (JSON Web Token) tabanlı kullanıcı girişi ve oturum yönetimi.
- **Kitap Yönetimi (CRUD):**
  - Kitapları listeleme.
  - Sadece giriş yapmış kullanıcılar tarafından kitap ekleme.
  - Sadece giriş yapmış kullanıcılar tarafından kitap bilgilerini güncelleme (modal ile).
  - Sadece giriş yapmış kullanıcılar tarafından kitap silme.
- **Kalıcı Veri Depolama:** Tüm veriler (kullanıcılar ve kitaplar) PostgreSQL veritabanında kalıcı olarak saklanır.
- **Modern Arayüz:** React Router ile sayfa yönlendirme, React Context API ile merkezi durum yönetimi ve Bootstrap ile duyarlı tasarım.

## 🛠️ Teknolojiler

### Backend
- **Java 17+**
- **Spring Boot 3+**
  - **Spring Web:** RESTful API oluşturmak için.
  - **Spring Security:** JWT tabanlı kimlik doğrulama ve yetkilendirme için.
  - **Spring Data JPA:** Veritabanı işlemleri için.
- **Hibernate:** JPA implementasyonu olarak.
- **PostgreSQL:** İlişkisel veritabanı.
- **Maven:** Bağımlılık yönetimi ve proje derleme.
- **Lombok:** Boilerplate kodları (getter, setter vb.) azaltmak için.
- **jjwt:** JWT oluşturma ve doğrulama kütüphanesi.

### Frontend
- **React 18+**
- **Vite:** Hızlı geliştirme sunucusu ve proje yapılandırma aracı.
- **React Router DOM:** Sayfalar arası yönlendirme (routing) için.
- **React Context API:** Global durum yönetimi (kimlik doğrulama durumu) için.
- **Axios:** Backend API'sine HTTP istekleri yapmak için.
- **Bootstrap 5:** Duyarlı ve şık bir arayüz için.

### Veritabanı & Araçlar
- **PostgreSQL:** Projenin ana veritabanı.
- **DBeaver:** Veritabanı yönetim arayüzü.

## 🚀 Kurulum ve Çalıştırma

Projeyi yerel makinenizde çalıştırmak için aşağıdaki adımları izleyin.

### Gereksinimler
- **Java Development Kit (JDK) 17** veya üstü.
- **Apache Maven**.
- **Node.js** ve **npm**.
- **PostgreSQL** sunucusu.
- **DBeaver** veya benzeri bir veritabanı istemcisi.

### 1. Backend Kurulumu

```bash
# 1. Projeyi klonlayın
git clone https://github.com/kullanici-adin/kitaplik-uygulamasi.git
cd kitaplik-uygulamasi/backend

# 2. PostgreSQL Veritabanını Hazırlama
# DBeaver veya psql kullanarak aşağıdaki SQL komutlarını çalıştırın:

# Proje için özel bir veritabanı oluşturun
CREATE DATABASE kitaplikdb;

# Proje için özel bir kullanıcı ve şifre oluşturun
CREATE ROLE kitaplik_user WITH LOGIN PASSWORD 'password123';

# Yeni kullanıcıya yeni veritabanı üzerinde tüm yetkileri verin
GRANT ALL PRIVILEGES ON DATABASE kitaplikdb TO kitaplik_user;

# 3. application.properties dosyasını yapılandırın
# /src/main/resources/ klasöründeki application.properties dosyasının
# aşağıdaki gibi yapılandırıldığından emin olun:

spring.datasource.url=jdbc:postgresql://localhost:5432/kitaplikdb
spring.datasource.username=kitaplik_user
spring.datasource.password=password123
# ...diğer ayarlar...

# 4. Uygulamayı çalıştırın
# IDE üzerinden doğrudan çalıştırabilir veya terminalden aşağıdaki komutu kullanabilirsiniz:
mvn spring-boot:run
```
Backend sunucusu varsayılan olarak `http://localhost:8081` adresinde çalışacaktır.

### 2. Frontend Kurulumu

```bash
# 1. Frontend klasörüne gidin
cd ../frontend

# 2. Gerekli Node.js paketlerini yükleyin
npm install

# 3. Uygulamayı geliştirme modunda çalıştırın
npm run dev
```
Frontend geliştirme sunucusu varsayılan olarak `http://localhost:5173` adresinde çalışacaktır. Tarayıcınızda bu adresi açarak uygulamayı kullanmaya başlayabilirsiniz.

## 📝 API Endpoints

Aşağıda backend tarafından sunulan ana API uç noktaları listelenmiştir.

| Metot  | URL                   | Açıklama                                   | Yetki Gerekli mi? |
|--------|-----------------------|--------------------------------------------|:-----------------:|
| POST   | `/api/auth/register`  | Yeni kullanıcı kaydı yapar.                |        Hayır      |
| POST   | `/api/auth/login`     | Kullanıcı girişi yapar ve JWT döndürür.    |        Hayır      |
| GET    | `/api/books`          | Tüm kitapları listeler.                    |        Hayır      |
| POST   | `/api/books`          | Yeni bir kitap ekler.                      |        Evet       |
| PUT    | `/api/books/{id}`     | Belirtilen ID'li kitabı günceller.         |        Evet       |
| DELETE | `/api/books/{id}`     | Belirtilen ID'li kitabı siler.             |        Evet       |

## 🔮 Gelecekteki Geliştirmeler

- [ ] Arama ve filtreleme özelliği.
- [ ] Kitap listesi için sayfalama (pagination).
- [ ] Kullanıcı rolleri (Admin, User) ve rol bazlı yetkilendirme.
- [ ] Kitaplar için resim yükleme özelliği.
- [ ] Daha gelişmiş UI/UX iyileştirmeleri (bildirimler, yükleniyor animasyonları).

---

