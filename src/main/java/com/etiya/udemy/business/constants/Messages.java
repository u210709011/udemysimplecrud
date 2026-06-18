package com.etiya.udemy.business.constants;

public final class Messages {

    private Messages() {
    }

    public static final class User {
        public static final String NOT_FOUND = "Kullanıcı bulunamadı.";
        public static final String MAIL_ALREADY_EXISTS = "Bu e-posta adresi zaten kayıtlı.";
        public static final String MUST_BE_TEACHER = "Bu işlem yalnızca eğitmen (TEACHER) profili için yapılabilir.";

        private User() {
        }
    }

    public static final class ProfileType {
        public static final String NOT_FOUND = "Profil tipi bulunamadı.";

        private ProfileType() {
        }
    }

    public static final class InstructorProfile {
        public static final String NOT_FOUND = "Eğitmen profili bulunamadı.";
        public static final String ALREADY_EXISTS = "Bu kullanıcının zaten bir eğitmen profili var.";

        private InstructorProfile() {
        }
    }

    public static final class Category {
        public static final String NOT_FOUND = "Kategori bulunamadı.";
        public static final String NAME_ALREADY_EXISTS = "Bu isimde bir kategori aynı üst kategori altında zaten var.";
        public static final String CANNOT_BE_OWN_PARENT = "Bir kategori kendisini üst kategori olarak gösteremez.";

        private Category() {
        }
    }

    public static final class Course {
        public static final String NOT_FOUND = "Kurs bulunamadı.";
        public static final String CODE_ALREADY_EXISTS = "Bu kurs kodu zaten kullanılıyor.";

        private Course() {
        }
    }

    public static final class Enrollment {
        public static final String NOT_FOUND = "Kayıt (enrollment) bulunamadı.";
        public static final String ALREADY_ENROLLED = "Öğrenci bu kursa zaten kayıtlı.";
        public static final String PAYMENT_REQUIRED = "Öğrenci ödemesini yapmadığı kursa kaydolamaz.";
        public static final String INSTRUCTOR_CANNOT_ENROLL_OWN_COURSE = "Eğitmen kendi kursuna öğrenci olarak kaydolamaz.";

        private Enrollment() {
        }
    }

    public static final class Wishlist {
        public static final String NOT_FOUND = "İstek listesi kaydı bulunamadı.";
        public static final String ALREADY_IN_WISHLIST = "Bu kurs zaten istek listesinde.";

        private Wishlist() {
        }
    }

    public static final class Payment {
        public static final String NOT_FOUND = "Ödeme bulunamadı.";
        public static final String ALREADY_PAID = "Bu kullanıcı bu kurs için zaten ödeme yapmış.";
        public static final String AMOUNT_MISMATCH = "Ödeme tutarı kurs fiyatı ile eşleşmiyor.";

        private Payment() {
        }
    }

    public static final class Review {
        public static final String NOT_FOUND = "Yorum bulunamadı.";
        public static final String INSTRUCTOR_CANNOT_REVIEW_OWN_COURSE = "Eğitmen kendi kursuna yorum yapamaz.";
        public static final String MUST_BE_ENROLLED = "Yalnızca kursa kayıtlı öğrenci yorum yapabilir.";
        public static final String ALREADY_REVIEWED = "Bu kursa zaten bir yorum yaptınız.";
        public static final String INVALID_RATING = "Puan 1 ile 5 arasında olmalıdır.";

        private Review() {
        }
    }

    public static final class Certificate {
        public static final String NOT_FOUND = "Sertifika bulunamadı.";
        public static final String MUST_BE_ENROLLED = "Sertifika yalnızca kursa kayıtlı kullanıcıya verilir.";
        public static final String ALREADY_ISSUED = "Bu kullanıcı-kurs için sertifika zaten oluşturulmuş.";

        private Certificate() {
        }
    }
}
