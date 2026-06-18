# 🚀 RUN GUIDE — Udemy Clone (E-Learning Platform)

Bu doküman projeyi yerelde ayağa kaldırmak ve test etmek için gereken her şeyi içerir.

---

## 1. Önkoşullar

| Araç | Sürüm |
|---|---|
| JDK | 17+ |
| Maven | Wrapper ile gelir (`mvnw` / `mvnw.cmd`), ayrıca kurmaya gerek yok |
| İşletim Sistemi | Windows / macOS / Linux |

> Not: H2 dosya tabanlı çalıştığı için ayrı bir veritabanı kurulumuna gerek yoktur. Veriler proje kökündeki `./data/` klasöründe saklanır ve uygulama kapansa bile kalıcıdır.

---

## 2. Projeyi Derleme

Windows (PowerShell / CMD):

```bash
.\mvnw.cmd clean compile
```

macOS / Linux:

```bash
./mvnw clean compile
```

---

## 3. Uygulamayı Çalıştırma

Windows:

```bash
.\mvnw.cmd spring-boot:run
```

macOS / Linux:

```bash
./mvnw spring-boot:run
```

Uygulama varsayılan olarak **http://localhost:8080** üzerinde ayağa kalkar.

Alternatif olarak jar üreterek çalıştırma:

```bash
.\mvnw.cmd clean package -DskipTests
java -jar target\udemy-0.0.1-SNAPSHOT.jar
```

---

## 4. Swagger UI (API Dokümantasyonu)

Uygulama çalışırken aşağıdaki adreslerden erişilebilir:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

Swagger üzerinden tüm endpoint'leri (Users, Courses, Categories, Enrollments, Payments, Reviews, Certificates, Wishlists, InstructorProfiles, ProfileTypes) deneyebilirsiniz.

---

## 5. H2 Console (Veritabanı Erişimi)

- **H2 Console adresi:** http://localhost:8080/h2-console

Bağlantı bilgileri:

| Alan | Değer |
|---|---|
| **JDBC URL** | `jdbc:h2:file:./data/udemydb` |
| **User Name** | `sa` |
| **Password** | *(boş bırakın)* |
| Driver Class | `org.h2.Driver` |

> Önemli: H2 Console'a giriş yaparken JDBC URL'i tam olarak yukarıdaki gibi yazdığınızdan emin olun.

---

## 6. API Endpoint Özeti

Tüm endpoint'ler `/api/v1` prefix'i altındadır.

| Modül | Base Path | Operasyonlar |
|---|---|---|
| Profile Types | `/api/v1/profile-types` | POST, GET, GET/{id} |
| Users | `/api/v1/users` | POST, PUT, GET, GET/{id}, DELETE/{id} |
| Instructor Profiles | `/api/v1/instructor-profiles` | POST, GET, GET/{id}, DELETE/{id} |
| Categories | `/api/v1/categories` | POST, PUT, GET, GET/{id}, DELETE/{id} |
| Courses | `/api/v1/courses` | POST, PUT, GET, GET/{id}, DELETE/{id} |
| Payments | `/api/v1/payments` | POST, GET, GET/{id} |
| Enrollments | `/api/v1/enrollments` | POST, GET, GET/{id}, DELETE/{id} |
| Wishlists | `/api/v1/wishlists` | POST, GET, GET/{id}, DELETE/{id} |
| Reviews | `/api/v1/reviews` | POST, GET, GET/{id}, DELETE/{id} |
| Certificates | `/api/v1/certificates` | POST, GET, GET/{id}, DELETE/{id} |

---

## 7. Örnek Uçtan Uca Akış (Happy Path)

İş kuralları nedeniyle kayıtlar belirli bir sırayla oluşturulmalıdır:

1. **Profil tipi oluştur** → `POST /api/v1/profile-types` body: `{ "name": "TEACHER" }` (ADMIN, TEACHER, STUDENT için tekrarlayın)
2. **Eğitmen kullanıcı oluştur** → `POST /api/v1/users` (profileId = TEACHER id)
3. **Öğrenci kullanıcı oluştur** → `POST /api/v1/users` (profileId = STUDENT id)
4. **Kategori oluştur** → `POST /api/v1/categories` body: `{ "name": "Software" }`
5. **Kurs oluştur** → `POST /api/v1/courses` (categoryId + instructorId = eğitmen id)
6. **Ödeme yap** → `POST /api/v1/payments` (öğrenci + kurs; `amount` kurs fiyatına eşit olmalı)
7. **Kursa kaydol** → `POST /api/v1/enrollments` (ödeme yapılmadan kayıt reddedilir)
8. **Yorum yap** → `POST /api/v1/reviews` (yalnızca kayıtlı öğrenci, eğitmen kendi kursuna yapamaz)
9. **Sertifika al** → `POST /api/v1/certificates` (yalnızca kayıtlı kullanıcıya)

---

## 8. Test Çalıştırma

```bash
.\mvnw.cmd test
```

---

## 9. Proje Hafızası

Mimari standartlar, isimlendirme kuralları ve iş kuralları için:

- `projectbrain/README.md` — Project Brain (mimari + standartlar)
- `projectbrain/requirements.md` — Gereksinim analizi (modüller, validasyon, business rules)
