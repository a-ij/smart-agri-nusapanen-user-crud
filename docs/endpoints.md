# Rancangan REST API Smart Agri

Semua endpoint kecuali login wajib memakai header:

```http
Authorization: Bearer <TOKEN>
```

| Method | URL | Role | Deskripsi |
|---|---|---|---|
| POST | /api/auth/login | public | Login dan mendapat JWT |
| POST | /api/auth/register | ADMIN | Membuat user baru |
| GET | /api/groups | ADMIN, PETUGAS, PETANI | Ambil semua kelompok tani |
| GET | /api/groups/{id} | ADMIN, PETUGAS, PETANI | Detail kelompok tani |
| POST | /api/groups | ADMIN, PETUGAS | Tambah kelompok tani |
| PUT | /api/groups/{id} | ADMIN, PETUGAS | Update kelompok tani |
| DELETE | /api/groups/{id} | ADMIN | Hapus kelompok tani |
| POST | /api/groups/{groupId}/commodities/{commodityId} | ADMIN, PETUGAS | Assign komoditas ke kelompok tani |
| GET | /api/farmers | ADMIN, PETUGAS | Ambil semua petani |
| GET | /api/farmers/{id} | ADMIN, PETUGAS, PETANI | Detail petani |
| POST | /api/farmers | ADMIN, PETUGAS | Tambah petani |
| PUT | /api/farmers/{id} | ADMIN, PETUGAS | Update petani |
| DELETE | /api/farmers/{id} | ADMIN | Hapus petani |
| GET | /api/commodities | ADMIN, PETUGAS, PETANI | Ambil semua komoditas |
| POST | /api/commodities | ADMIN, PETUGAS | Tambah komoditas |
| GET | /api/stocks | ADMIN, PETUGAS, PETANI | Ambil data stok |
| POST | /api/stocks | ADMIN, PETUGAS, PETANI | Catat stok pangan |
| GET | /api/distributions | ADMIN, PETUGAS, PETANI | Ambil data distribusi |
| POST | /api/distributions | ADMIN, PETUGAS | Catat distribusi pangan |
| GET | /api/monitoring/availability | ADMIN, PETUGAS, PETANI | Monitoring ketersediaan pangan |
