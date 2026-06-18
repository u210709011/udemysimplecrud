# 🧠 Project Brain — Udemy Clone (E-Learning Platform)

> Bu doküman projenin **kalıcı hafızasıdır** ve aynı zamanda yapay zekânın **Ajan Kuralları (Agent Instructions)** dosyasıdır.
> Her yeni adımda bu dosya referans alınır. Buradaki standartlar **tartışmasız** uygulanır; aksi yönde bir talep gelmedikçe değiştirilmez.

---

## 1. Proje Künyesi

| Alan | Değer |
|---|---|
| Proje Adı | Udemy Clone (Online Eğitim Platformu) |
| Grup / Artifact | `com.etiya` / `udemy` |
| Java | 17 |
| Spring Boot | 4.1.0 |
| Build Aracı | Maven (wrapper: `mvnw` / `mvnw.cmd`) |
| Mimari | N-Layered (Çok Katmanlı) Architecture |
| Veritabanı | H2 (file-based / kalıcı) |

---

## 2. Mimari Yapı (N-Layered Architecture)

Bağımlılık yönü her zaman **yukarıdan aşağıya** akar. Üst katman alt katmanı bilir, alt katman üst katmanı **asla** bilmez.

```
api  ──►  business  ──►  dataAccess  ──►  entities
                    └──►  core (cross-cutting: tüm katmanlar kullanabilir)
```

### 2.1. `entities` — Veri Modeli Katmanı
- **Sorumluluk:** Veritabanı tablolarını temsil eden JPA `@Entity` sınıfları.
- İçerik: `entities/concretes` altında somut entity'ler. Tümü `BaseEntity`'den türer.
- **Kural:** İçinde iş mantığı (business logic) bulunmaz. Sadece alanlar, ilişkiler (`@ManyToOne`, `@OneToMany` vb.) ve JPA anotasyonları.
- Entity sınıfları **asla** API'ye doğrudan açılmaz (DTO zorunluluğu — bkz. Bölüm 4).

### 2.2. `dataAccess` — Veri Erişim Katmanı
- **Sorumluluk:** Veritabanı CRUD işlemleri.
- İçerik: `dataAccess/abstracts` altında `JpaRepository<Entity, Id>` türevi interface'ler.
- İsimlendirme: `XxxRepository` (örn. `UserRepository`, `CourseRepository`).
- **Kural:** Özel sorgular `@Query` veya türetilmiş metot isimleriyle (derived query) tanımlanır. İş mantığı içermez.

### 2.3. `business` — İş Mantığı Katmanı
- **Sorumluluk:** Uygulamanın kalbi. İş kuralları, validasyon, DTO dönüşümü, orkestrasyon.
- Alt paketler:
  - `business/abstracts` → Servis interface'leri (`XxxService`).
  - `business/concretes` → Servis implementasyonları (`XxxManager`).
  - `business/dtos/requests` → İstek DTO'ları (`CreateXxxRequest`, `UpdateXxxRequest`).
  - `business/dtos/responses` → Yanıt DTO'ları (`GetXxxResponse`, `CreatedXxxResponse`, `XxxListItemResponse`).
  - `business/rules` → İş kuralı motorları (`XxxBusinessRules`).
  - `business/mappers` → MapStruct mapper interface'leri (`XxxMapper`).
  - `business/constants` → Mesaj sabitleri (`Messages`).
- **Kural:** Servis interface'i `XxxService`, implementasyonu `XxxManager` olur (Engin Demiroğ konvansiyonu).

### 2.4. `api` — Sunum / Web Katmanı
- **Sorumluluk:** HTTP isteklerini karşılamak. `@RestController` sınıfları.
- İçerik: `api/controllers` altında `XxxController`.
- **Kural:** Controller **ince (thin)** olmalı; iş mantığı içermez, yalnızca business katmanını çağırır. Yalnızca DTO alır ve DTO döner.

### 2.5. `core` — Çekirdek / Cross-Cutting Concerns
- **Sorumluluk:** Katmanlar arası ortak altyapı.
- Alt paketler:
  - `core/crosscuttingconcerns/exceptions/types` → Özel exception tipleri (`BusinessException`).
  - `core/crosscuttingconcerns/exceptions/handlers` → `@RestControllerAdvice` global handler + hata yanıt modelleri.
  - `core/configuration` → Spring config sınıfları (Security, OpenAPI/Swagger, ModelMapper/MapStruct ayarları).
  - `core/utilities/results` → (Opsiyonel) Standart sonuç sarmalayıcıları.

---

## 3. İsimlendirme Standartları (Java Conventions)

| Öğe | Konvansiyon | Örnek |
|---|---|---|
| Paket | tamamı küçük harf | `com.etiya.udemy.business.concretes` |
| Sınıf / Interface | PascalCase | `CourseManager`, `CourseService` |
| Metot / Değişken | camelCase | `getById`, `instructorId` |
| Sabit (constant) | UPPER_SNAKE_CASE | `COURSE_NOT_FOUND` |
| Entity | tekil isim | `Course`, `User`, `Category` |
| Tablo adı | çoğul, snake_case | `courses`, `user_course_assignments` |
| Kolon adı | snake_case | `first_name`, `registration_date` |
| Repository | `XxxRepository` | `CourseRepository` |
| Service interface | `XxxService` | `CourseService` |
| Service impl | `XxxManager` | `CourseManager` |
| Controller | `XxxController` | `CoursesController` |
| Request DTO | `VerbXxxRequest` | `CreateCourseRequest` |
| Response DTO | `VerbXxxResponse` | `GetCourseResponse` |
| Business Rules | `XxxBusinessRules` | `CourseBusinessRules` |
| Mapper | `XxxMapper` | `CourseMapper` |

---

## 4. DTO Sistemi (ZORUNLU)

> **Altın Kural:** Entity sınıfları **asla** dışarı (API'ye) açılmaz. Her giriş/çıkış DTO ile yapılır.

- Her **istek** için ayrı bir Request DTO bulunur: `CreateCourseRequest`, `UpdateCourseRequest`.
- Her **yanıt** için ayrı bir Response DTO bulunur: `GetCourseResponse`, `CreatedCourseResponse`, `CourseListItemResponse`.
- Liste dönüşleri için `XxxListItemResponse` tercih edilir (gereksiz alan taşımamak için).
- Entity ↔ DTO dönüşümü **MapStruct** ile yapılır; manuel kopyalama yapılmaz.
- DTO'larda validasyon anotasyonları (`@NotNull`, `@NotBlank`, `@Size`, `@Email`, `@Min`, `@Max`) bulunur.

---

## 5. Global Hata Yönetimi (ZORUNLU)

- Merkezi exception handling **`@RestControllerAdvice`** ile kurulur (`core/.../handlers`).
- Özel iş hatası: `BusinessException` (`RuntimeException` türevi).
- İş kuralı ihlalinde `BusinessException` fırlatılır → HTTP **400 Bad Request**.
- Validasyon hataları (`MethodArgumentNotValidException`) → **400** + alan bazlı hata listesi.
- Beklenmeyen hatalar → **500 Internal Server Error**.
- Standart hata yanıt modeli: `{ message, timestamp }` (validasyon için `{ validationErrors }`).

---

## 6. İş Kuralları Motoru (Business Rules)

- Her aggregate için `XxxBusinessRules` sınıfı (`@Service`).
- Kurallar **private/public void** metotlar halinde yazılır; ihlalde `BusinessException` fırlatır.
- Servis metotları, asıl işten **önce** ilgili kural metotlarını çağırır.
- Örnek kurallar:
  - Aynı e-posta ile iki kullanıcı kaydolamaz.
  - Bir öğrenci ödemesini yapmadığı kursa kaydolamaz.
  - Bir eğitmen kendi kursuna yorum (review) yapamaz.
  - Bir kullanıcı aynı kursa iki kez kaydolamaz / aynı kursu iki kez wishlist'e ekleyemez.
  - Sertifika yalnızca kursa kayıtlı kullanıcıya verilir.

---

## 7. Veritabanı Standartları

- H2 **file-based** (kalıcı): veriler `./data/` altında dosyada tutulur, uygulama kapanınca silinmez.
- Audit alanları `BaseEntity`'de: `createdDate`, `updatedDate`, `deletedDate`, `isActive`.
- **Soft delete** yaklaşımı: silme işlemi `deletedDate` set eder + `isActive=false` yapar (hard delete yerine tercih edilir).
- Tüm PK'lar `Long id`, `GenerationType.IDENTITY`.

---

## 8. Veri Modeli (Spreadsheet referanslı + mantıksal zenginleştirmeler)

| Tablo | Alanlar (zenginleştirilmiş) |
|---|---|
| `profile_types` | id, name (ADMIN/TEACHER/STUDENT) |
| `users` | id, first_name, last_name, mail, password, registration_date, profile_id (FK) |
| `instructor_profiles` | id, user_id (FK→users), description |
| `categories` | id, name, **parent_id** (FK→categories, kendine referans / hiyerarşi) |
| `courses` | id, code, name, **description, price**, category_id (FK), instructor_id (FK→users) |
| `user_course_assignments` | id, user_id (FK), course_id (FK), **assignment_date** |
| `wishlists` | id, user_id (FK), course_id (FK) |
| `payments` | id, user_id (FK), payment_method (enum), payment_date, **amount, course_id (FK)** |
| `reviews` | id, rating (1-5), comment, review_date, user_id (FK), course_id (FK) |
| `certificates` | id, user_id (FK), course_id (FK), **certificate_no, issue_date** |

> Not: Spreadsheet'teki alanlar temel alınmış; kurumsal mantıkla `parent_id`, `price`, `amount`, `assignment_date`, `certificate_no` gibi alanlar eklenmiştir. Detaylı gerekçeler `requirements.md` içindedir.

---

## 9. Teknoloji Yığını

- Spring Web (MVC) — REST API
- Spring Data JPA — ORM & repository
- Spring Validation — DTO doğrulama
- Spring Security — kimlik doğrulama (geliştirmede Swagger/H2 Console açık)
- H2 Database (file-based)
- Lombok — boilerplate azaltma
- MapStruct — entity ↔ DTO dönüşümü
- springdoc-openapi — Swagger UI / OpenAPI 3 dokümantasyonu

---

## 10. Ajan (AI) Çalışma Kuralları

1. Her adımda **önce bu dokümanı** referans al.
2. Standartlardan sapma yapma; gerekirse önce bu dokümanı güncelle, sonra kod yaz.
3. Entity'leri asla API'ye açma — DTO zorunlu.
4. Tüm iş kuralları `BusinessRules` sınıflarında; servisler bunları çağırır.
5. Katman bağımlılık yönüne sadık kal (api → business → dataAccess → entities).
6. İsimlendirme tablosuna (Bölüm 3) birebir uy.
7. Öğrenilen her yeni kural bu dosyaya eklenir; **asla unutulmaz**.
