# Tutorial Frontend Baru AgriVista + CRUD User

Dokumen ini menjelaskan perubahan frontend agar tampilannya berbeda, lebih rapi, tidak menampilkan keterangan akun demo di halaman login, dan tetap memuat fitur CRUD User.

## 1. File yang Berubah

Frontend berada di folder:

```text
src/main/resources/static
```

File utama yang digunakan:

```text
index.html   -> struktur halaman login, dashboard, menu CRUD, dan CRUD User
styles.css   -> tampilan baru AgriVista
app.js       -> koneksi frontend ke REST API Spring Boot
```

## 2. Tampilan Login Baru

Halaman awal sekarang menggunakan konsep landing page bernama **AgriVista**.

Perubahan utama:

- Tidak ada tulisan akun demo.
- Tidak ada username dan password yang otomatis terisi.
- Form login dibuat lebih bersih dan profesional.
- Terdapat ringkasan fitur sistem: data terpusat, monitoring, dan role access.

Akun default tetap dibuat dari backend agar aplikasi mudah diuji, tetapi tidak ditampilkan di halaman frontend.

## 3. Dashboard Baru

Dashboard setelah login memiliki tampilan berbeda dari versi sebelumnya:

- Sidebar gelap dengan menu navigasi baru.
- Hero dashboard besar di halaman utama.
- Kartu statistik untuk kelompok tani, petani, komoditas, dan stok tersedia.
- Monitoring ketersediaan pangan menggunakan card grid.
- Layout form dan tabel dibuat lebih formal seperti dashboard admin.

## 4. CRUD User Sudah Tersedia

Menu **User & Role** hanya tampil untuk akun dengan role:

```text
ROLE_ADMIN
```

Fitur yang tersedia:

```text
Tambah user
Lihat semua user
Edit user
Hapus user
Hubungkan user dengan data petani
Pilih role ADMIN / PETUGAS / PETANI
```

Endpoint backend yang dipakai:

```http
GET    /api/users
GET    /api/users/{id}
POST   /api/users
PUT    /api/users/{id}
DELETE /api/users/{id}
```

## 5. Cara Menjalankan

Masuk ke folder project:

```bash
cd smart-agri-api
```

Pastikan database MySQL sudah tersedia:

```sql
CREATE DATABASE smart_agri_db;
```

Cek konfigurasi database di:

```text
src/main/resources/application.properties
```

Jalankan aplikasi:

```bash
mvn spring-boot:run
```

Buka browser:

```text
http://localhost:8080
```

## 6. Cara Login

Gunakan akun yang sudah ada di database. Jika masih memakai data awal dari project, backend membuat akun admin default saat aplikasi pertama kali dijalankan.

Setelah login sebagai admin, buka menu:

```text
User & Role
```

Coba tambah user baru, misalnya:

```text
username: petugas1
password: petugas123
role: ROLE_PETUGAS
```

Lalu logout dan login menggunakan user tersebut untuk menguji role.

## 7. Catatan Penting

Jika frontend belum berubah setelah file diganti, lakukan langkah berikut:

1. Stop server dengan `Ctrl + C`.
2. Jalankan ulang `mvn spring-boot:run`.
3. Tekan `Ctrl + F5` di browser untuk hard refresh.
4. Pastikan membuka alamat `http://localhost:8080`, bukan hanya `localhost` tanpa port.
