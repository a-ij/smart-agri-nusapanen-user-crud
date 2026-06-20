# Tutorial Menambahkan CRUD User

Tutorial ini menambahkan fitur CRUD user pada project Smart Agri Fullstack Browser.

## Fitur yang ditambahkan

- GET `/api/users`
- GET `/api/users/{id}`
- POST `/api/users`
- PUT `/api/users/{id}`
- DELETE `/api/users/{id}`
- Halaman frontend untuk daftar, tambah, edit, dan hapus user
- Hanya `ROLE_ADMIN` yang bisa mengakses menu User & Role

## File backend yang ditambahkan

- `src/main/java/com/project/smartcommunity/dto/UserUpdateRequest.java`
- `src/main/java/com/project/smartcommunity/service/UserService.java`
- `src/main/java/com/project/smartcommunity/service/impl/UserServiceImpl.java`
- `src/main/java/com/project/smartcommunity/controller/UserController.java`

## File backend yang diubah

- `src/main/java/com/project/smartcommunity/repository/AppUserRepository.java`

Tambahkan method:

```java
boolean existsByUsernameAndIdNot(String username, Long id);
```

## File frontend yang diubah

- `src/main/resources/static/index.html`
- `src/main/resources/static/app.js`
- `src/main/resources/static/styles.css`

## Cara menjalankan

```bash
mvn spring-boot:run
```

Buka browser:

```text
http://localhost:8080
```

Login:

```text
username: admin
password: admin123
```

Masuk ke menu **User & Role**. Di sana kamu bisa tambah, edit, dan hapus user.
