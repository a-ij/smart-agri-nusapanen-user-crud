# Tutorial Lengkap Smart Agri Fullstack

Project ini adalah sistem informasi pertanian berbasis Spring Boot yang sudah memiliki backend REST API, login JWT, role-based access, dan frontend dashboard yang langsung bisa dibuka lewat browser.

## 1. Fitur Sistem

### Backend
- Java 17+
- Spring Boot
- Spring Web REST API
- Spring Data JPA
- Spring Security + JWT
- MySQL
- Layered architecture: Controller, Service interface, Service implementation, Repository, Model, DTO, Exception, Config
- OOP: encapsulation, inheritance melalui `BaseEntity`, polymorphism melalui `getDisplayName()`, interface service, abstract class, custom exception

### Frontend
- HTML, CSS, JavaScript murni
- Tidak perlu Node.js, Vite, atau React
- Login page cantik
- Dashboard statistik
- Monitoring ketersediaan pangan
- CRUD kelompok tani
- CRUD petani
- CRUD komoditas
- CRUD stok pangan
- CRUD distribusi pangan
- Form pembuatan user dan role

## 2. Persiapan Software

Install software berikut:

1. JDK 17 atau lebih baru
2. Maven
3. MySQL atau XAMPP MySQL
4. Browser
5. Postman opsional untuk testing API

Cek Java:

```bash
java -version
```

Cek Maven:

```bash
mvn -version
```

## 3. Persiapan Database

Masuk ke MySQL lalu buat database:

```sql
CREATE DATABASE smart_agri_db;
```

File konfigurasi database ada di:

```text
src/main/resources/application.properties
```

Default konfigurasi:

```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/smart_agri_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

Kalau MySQL kamu punya password, isi bagian berikut:

```properties
spring.datasource.password=password_mysql_kamu
```

## 4. Menjalankan Project

Masuk ke folder project:

```bash
cd smart-agri-api
```

Jalankan:

```bash
mvn spring-boot:run
```

Tunggu sampai muncul log Tomcat aktif. Setelah berhasil, buka browser:

```text
http://localhost:8080
```

## 5. Akun Login Default

Saat pertama kali dijalankan, sistem otomatis membuat admin default:

```text
username: admin
password: admin123
role: ROLE_ADMIN
```

Selain admin, sistem juga membuat data demo awal seperti komoditas, kelompok tani, petani, stok, dan distribusi.

## 6. Cara Menggunakan Aplikasi dari Browser

1. Buka `http://localhost:8080`.
2. Login dengan `admin / admin123`.
3. Masuk ke Dashboard.
4. Buka menu Komoditas untuk menambah jenis pangan.
5. Buka menu Kelompok Tani untuk mengelola kelompok tani.
6. Buka menu Petani untuk mengelola data petani.
7. Buka menu Stok Pangan untuk mencatat hasil panen.
8. Buka menu Distribusi untuk mencatat penyaluran pangan.
9. Lihat menu Dashboard untuk monitoring ketersediaan pangan.

## 7. Role-Based Access

### ROLE_ADMIN
- Melihat semua data
- Menambah semua data
- Mengubah semua data
- Menghapus data
- Membuat user baru

### ROLE_PETUGAS
- Melihat data operasional
- Menambah dan mengubah kelompok tani, petani, komoditas, stok, dan distribusi
- Tidak bisa menghapus data tertentu yang dibatasi admin

### ROLE_PETANI
- Melihat data tertentu
- Mencatat stok pangan
- Melihat monitoring ketersediaan pangan

## 8. Struktur Folder Penting

```text
src/main/java/com/project/smartcommunity
├── config
├── controller
├── dto
├── exception
├── model
├── repository
└── service

src/main/resources/static
├── index.html
├── styles.css
└── app.js
```

Frontend berada di folder `src/main/resources/static`, sehingga Spring Boot langsung menyajikannya di browser tanpa server frontend terpisah.

## 9. Endpoint API Utama

| Method | Endpoint | Fungsi |
|---|---|---|
| POST | /api/auth/login | Login dan mendapatkan JWT |
| POST | /api/auth/register | Membuat user baru |
| GET | /api/groups | Melihat kelompok tani |
| POST | /api/groups | Menambah kelompok tani |
| PUT | /api/groups/{id} | Mengubah kelompok tani |
| DELETE | /api/groups/{id} | Menghapus kelompok tani |
| GET | /api/farmers | Melihat petani |
| POST | /api/farmers | Menambah petani |
| GET | /api/commodities | Melihat komoditas |
| POST | /api/commodities | Menambah komoditas |
| GET | /api/stocks | Melihat stok pangan |
| POST | /api/stocks | Mencatat stok pangan |
| GET | /api/distributions | Melihat distribusi |
| POST | /api/distributions | Mencatat distribusi |
| GET | /api/monitoring/availability | Monitoring ketersediaan pangan |

## 10. Troubleshooting

### Port 8080 sudah dipakai

Cari PID:

```powershell
netstat -ano | findstr :8080
```

Matikan proses:

```powershell
taskkill /PID NOMOR_PID /F
```

Atau ubah port di `application.properties`:

```properties
server.port=8081
```

### Browser menampilkan 403

Pastikan memakai versi fullstack ini. File `SecurityConfig.java` sudah mengizinkan akses ke `/`, `/index.html`, `/styles.css`, dan `/app.js`.

### Login gagal

Pastikan database berjalan dan admin default sudah dibuat. Cek log terminal untuk pesan:

```text
Default admin dibuat: username=admin, password=admin123
```

### Maven tidak dikenali

Install Maven atau jalankan project melalui IntelliJ IDEA dengan membuka class:

```text
SmartCommunityApplication.java
```

lalu klik Run.
