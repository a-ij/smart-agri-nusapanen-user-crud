# Tutorial Mengganti Frontend ke Tampilan NusaPanen

Frontend ini dibuat sebagai alternatif tampilan yang berbeda dari versi AgriVista, tetapi tetap rapi, responsif, dan mempertahankan semua fitur sebelumnya.

## Fitur yang dipertahankan

- Login JWT
- Dashboard monitoring pangan
- CRUD Kelompok Tani
- CRUD Petani
- CRUD Komoditas
- CRUD Stok Pangan
- CRUD Distribusi
- CRUD User & Role untuk ROLE_ADMIN
- Role-based menu: User & Role hanya tampil untuk admin

## File frontend yang diganti

File utama berada di:

```text
src/main/resources/static/index.html
src/main/resources/static/styles.css
src/main/resources/static/app.js
```

## Perbedaan desain

- Nama tampilan: NusaPanen
- Navigasi menggunakan top navigation, bukan sidebar gelap
- Ikon memakai inline SVG lokal sehingga tidak hilang walaupun internet mati
- Login page tidak menampilkan keterangan akun demo
- Dashboard dibuat lebih clean dengan kartu statistik, hero panel, dan monitoring cards
- CRUD user tetap tersedia di menu User & Role

## Cara menjalankan

1. Stop server lama dengan `Ctrl + C`.
2. Jalankan ulang project:

```bash
mvn spring-boot:run
```

3. Buka browser:

```text
http://localhost:8080
```

4. Kalau tampilan lama masih muncul, tekan:

```text
Ctrl + F5
```

atau hapus cache browser.

## Cara ambil frontend saja

Kalau backend kamu sudah berjalan dan hanya ingin mengganti tampilan, cukup copy tiga file ini dari ZIP:

```text
src/main/resources/static/index.html
src/main/resources/static/styles.css
src/main/resources/static/app.js
```

Lalu paste ke project lama pada folder yang sama.
