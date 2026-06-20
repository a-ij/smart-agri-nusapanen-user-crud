# NusaPanen - Sistem Informasi Pertanian

Project fullstack sederhana berbasis Spring Boot untuk tugas OOP tema Ketahanan Pangan / Sistem Informasi Pertanian.

## Isi fitur

- Backend Spring Boot REST API
- Frontend browser langsung dari `src/main/resources/static`
- Login JWT
- Role-based access control
- CRUD Kelompok Tani
- CRUD Petani
- CRUD Komoditas
- CRUD Stok Pangan
- CRUD Distribusi
- CRUD User & Role
- Dashboard monitoring ketersediaan pangan

## Akun awal

Admin awal dibuat otomatis oleh `DataInitializer` saat aplikasi pertama kali dijalankan.

## Jalankan aplikasi

```bash
mvn spring-boot:run
```

Buka:

```text
http://localhost:8080
```

## Database

Default database MySQL:

```sql
CREATE DATABASE smart_agri_db;
```

Konfigurasi ada di:

```text
src/main/resources/application.properties
```

## Dokumentasi tambahan

- `docs/TUTORIAL_FULLSTACK_BROWSER.md`
- `docs/TUTORIAL_CRUD_USER.md`
- `docs/TUTORIAL_FRONTEND_NUSAPANEN.md`
- `docs/endpoints.md`
- `docs/dbdiagram.dbml`
