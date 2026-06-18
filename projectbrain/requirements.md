# 📋 Gereksinim Analizi — Udemy Clone (E-Learning Platform)

> Rol: Sistem Analisti. Bu doküman; spreadsheet'teki veri modeline sadık kalır, kurumsal mantıkla zenginleştirilir.
> Burada her modül için **fonksiyonel gereksinimler**, **entity yapıları**, **validasyon kuralları** ve **iş kuralları (Business Rules)** tanımlanır.
> Standartlar için `README.md` (Project Brain) referans alınır.

---

## 0. Genel Kabuller

- Tüm entity'ler `BaseEntity`'den türer: `id`, `createdDate`, `updatedDate`, `deletedDate`, `isActive`.
- Silme işlemleri **soft delete** (deletedDate + isActive=false).
- Para alanları `BigDecimal`, tarih alanları `LocalDate`/`LocalDateTime`.
- Tüm dış iletişim DTO ile yapılır; entity API'ye açılmaz.

---

## 1. User & Profile Modülü

### 1.1. Fonksiyonel Gereksinimler
- Sistemde 3 profil tipi vardır: **ADMIN, TEACHER, STUDENT** (`profile_types`).
- Kullanıcı kaydı (register), listeleme, id ile getirme, güncelleme, soft-delete.
- Bir kullanıcı tek bir profile tipine sahiptir (FK: `profile_id`).
- TEACHER tipindeki kullanıcılar için ek **InstructorProfile** (eğitmen biyografisi) tutulur.

### 1.2. Entity Yapıları

**ProfileType**
| Alan | Tip | Açıklama |
|---|---|---|
| id | Long | PK |
| name | enum/String | ADMIN, TEACHER, STUDENT |

**User**
| Alan | Tip | Açıklama |
|---|---|---|
| id | Long | PK |
| firstName | String | Ad |
| lastName | String | Soyad |
| mail | String | Benzersiz e-posta |
| password | String | Parola (hash'lenmeli) |
| registrationDate | LocalDate | Kayıt tarihi |
| profileType | ProfileType (ManyToOne) | Profil tipi |
| instructorProfile | InstructorProfile (OneToOne) | (yalnız TEACHER) |

**InstructorProfile**
| Alan | Tip | Açıklama |
|---|---|---|
| id | Long | PK |
| user | User (OneToOne) | Eğitmen kullanıcı |
| description | String | Eğitmen tanıtımı |

### 1.3. Validasyon Kuralları
- `firstName`, `lastName`: `@NotBlank`, `@Size(min=2, max=50)`.
- `mail`: `@NotBlank`, `@Email`.
- `password`: `@NotBlank`, `@Size(min=5, max=100)`.
- `profileId`: `@NotNull`.
- `description` (InstructorProfile): `@NotBlank`, `@Size(max=1000)`.

### 1.4. İş Kuralları (Business Rules)
- **BR-U1:** Aynı `mail` ile ikinci bir kullanıcı kaydedilemez (e-posta benzersizdir).
- **BR-U2:** Var olmayan bir `profileId` ile kayıt yapılamaz.
- **BR-U3:** Güncelleme/getirme öncesi kullanıcı id'si var olmalıdır; yoksa `BusinessException`.
- **BR-U4:** InstructorProfile yalnızca profili **TEACHER** olan kullanıcıya eklenebilir.
- **BR-U5:** Bir kullanıcının en fazla bir InstructorProfile'ı olabilir.

---

## 2. Course & Category Modülü

### 2.1. Fonksiyonel Gereksinimler
- Kategoriler hiyerarşik olabilir (alt-üst kategori — `parent_id`).
- Kurs oluşturma, listeleme, id ile getirme, güncelleme, soft-delete.
- Her kurs bir kategoriye ve bir eğitmene (TEACHER) bağlıdır.

### 2.2. Entity Yapıları

**Category**
| Alan | Tip | Açıklama |
|---|---|---|
| id | Long | PK |
| name | String | Kategori adı |
| parent | Category (ManyToOne, self) | Üst kategori (opsiyonel) |

**Course**
| Alan | Tip | Açıklama |
|---|---|---|
| id | Long | PK |
| code | String | Benzersiz kurs kodu (örn. PY, JV) |
| name | String | Kurs adı |
| description | String | Açıklama |
| price | BigDecimal | Fiyat |
| category | Category (ManyToOne) | Kategori |
| instructor | User (ManyToOne) | Eğitmen (TEACHER) |

### 2.3. Validasyon Kuralları
- `code`: `@NotBlank`, `@Size(min=2, max=10)`.
- `name`: `@NotBlank`, `@Size(min=2, max=150)`.
- `price`: `@NotNull`, `@DecimalMin("0.0")`.
- `categoryId`, `instructorId`: `@NotNull`.
- Category `name`: `@NotBlank`, `@Size(min=2, max=100)`.

### 2.4. İş Kuralları (Business Rules)
- **BR-C1:** Aynı `code` ile iki kurs olamaz (kurs kodu benzersiz).
- **BR-C2:** Var olmayan `categoryId` veya `instructorId` ile kurs açılamaz.
- **BR-C3:** Kursun eğitmeni profili **TEACHER** olan bir kullanıcı olmalıdır.
- **BR-C4:** Bir kategori kendisini parent olarak gösteremez (döngü engeli).
- **BR-C5:** Kategori adı aynı parent altında benzersiz olmalıdır.

---

## 3. Enrollment & Wishlist Modülü

### 3.1. Fonksiyonel Gereksinimler
- Öğrenci bir kursa kaydolur (`user_course_assignments`).
- Öğrenci ilgilendiği kursları istek listesine ekler (`wishlists`).
- Kayıt/wishlist listeleme ve kaldırma.

### 3.2. Entity Yapıları

**UserCourseAssignment** (Enrollment)
| Alan | Tip | Açıklama |
|---|---|---|
| id | Long | PK |
| user | User (ManyToOne) | Öğrenci |
| course | Course (ManyToOne) | Kurs |
| assignmentDate | LocalDate | Kayıt tarihi |

**Wishlist**
| Alan | Tip | Açıklama |
|---|---|---|
| id | Long | PK |
| user | User (ManyToOne) | Kullanıcı |
| course | Course (ManyToOne) | Kurs |

### 3.3. Validasyon Kuralları
- `userId`, `courseId`: `@NotNull`.

### 3.4. İş Kuralları (Business Rules)
- **BR-E1:** Bir öğrenci, **ödemesini yapmadığı** kursa kaydolamaz (payment kontrolü).
- **BR-E2:** Bir öğrenci aynı kursa iki kez kaydolamaz (mükerrer enrollment engeli).
- **BR-E3:** Eğitmen kendi kursuna öğrenci olarak kaydolamaz.
- **BR-E4:** Bir kullanıcı aynı kursu wishlist'e iki kez ekleyemez.
- **BR-E5:** Zaten kayıtlı (enrolled) olduğu kurs wishlist'e eklenmemelidir (mantıksal uyarı).

---

## 4. Payment Modülü

### 4.1. Fonksiyonel Gereksinimler
- Kullanıcı bir kurs için ödeme yapar; ödeme geçmişi tutulur.
- Ödeme yöntemi: **DEBIT, CREDIT** (genişletilebilir enum).
- Ödeme listeleme ve id ile getirme.

### 4.2. Entity Yapısı

**Payment**
| Alan | Tip | Açıklama |
|---|---|---|
| id | Long | PK |
| user | User (ManyToOne) | Ödeyen |
| course | Course (ManyToOne) | Ödenen kurs |
| amount | BigDecimal | Tutar |
| paymentMethod | enum | DEBIT / CREDIT |
| paymentDate | LocalDateTime | Ödeme tarihi |

### 4.3. Validasyon Kuralları
- `userId`, `courseId`: `@NotNull`.
- `amount`: `@NotNull`, `@DecimalMin("0.0", inclusive=false)`.
- `paymentMethod`: `@NotNull`.

### 4.4. İş Kuralları (Business Rules)
- **BR-P1:** Ödeme tutarı kursun fiyatı ile eşleşmelidir (eksik ödeme kabul edilmez).
- **BR-P2:** Aynı kullanıcı aynı kurs için tekrar ödeme yapamaz (zaten ödenmiş).
- **BR-P3:** Var olmayan kullanıcı/kurs için ödeme oluşturulamaz.

---

## 5. Review & Certificate Modülü

### 5.1. Fonksiyonel Gereksinimler
- Öğrenci kayıtlı olduğu kursa puan (1-5) ve yorum bırakır (`reviews`).
- Kursu tamamlayan/kayıtlı öğrenciye sertifika verilir (`certificates`).

### 5.2. Entity Yapıları

**Review**
| Alan | Tip | Açıklama |
|---|---|---|
| id | Long | PK |
| rating | int | 1-5 arası puan |
| comment | String | Yorum |
| reviewDate | LocalDate | Yorum tarihi |
| user | User (ManyToOne) | Yorumlayan |
| course | Course (ManyToOne) | Kurs |

**Certificate**
| Alan | Tip | Açıklama |
|---|---|---|
| id | Long | PK |
| user | User (ManyToOne) | Sahibi |
| course | Course (ManyToOne) | Kurs |
| certificateNo | String | Benzersiz sertifika no |
| issueDate | LocalDate | Veriliş tarihi |

### 5.3. Validasyon Kuralları
- Review `rating`: `@NotNull`, `@Min(1)`, `@Max(5)`.
- Review `comment`: `@Size(max=1000)`.
- `userId`, `courseId`: `@NotNull`.
- Certificate `certificateNo`: `@NotBlank` (sistem üretir).

### 5.4. İş Kuralları (Business Rules)
- **BR-R1:** Bir eğitmen **kendi kursuna** yorum yapamaz.
- **BR-R2:** Yalnızca kursa **kayıtlı (enrolled)** öğrenci yorum yapabilir.
- **BR-R3:** Bir öğrenci aynı kursa birden fazla yorum yapamaz.
- **BR-R4:** `rating` 1-5 aralığı dışında olamaz.
- **BR-CR1:** Sertifika yalnızca kursa kayıtlı kullanıcıya verilir.
- **BR-CR2:** Aynı kullanıcı-kurs için ikinci sertifika üretilemez.
- **BR-CR3:** `certificateNo` benzersizdir (sistem otomatik üretir).

---

## 6. İş Kuralları Özet Matrisi

| Kod | Kural | HTTP |
|---|---|---|
| BR-U1 | E-posta benzersiz | 400 |
| BR-U4 | InstructorProfile sadece TEACHER'a | 400 |
| BR-C1 | Kurs kodu benzersiz | 400 |
| BR-C3 | Kurs eğitmeni TEACHER olmalı | 400 |
| BR-E1 | Ödemesiz kursa kayıt yok | 400 |
| BR-E2 | Mükerrer kayıt yok | 400 |
| BR-R1 | Eğitmen kendi kursuna yorum yapamaz | 400 |
| BR-R2 | Sadece kayıtlı öğrenci yorum yapar | 400 |
| BR-CR1 | Sertifika sadece kayıtlıya | 400 |

> Tüm ihlaller `BusinessException` ile fırlatılır ve Global Exception Handler tarafından **400 Bad Request** olarak yanıtlanır.
