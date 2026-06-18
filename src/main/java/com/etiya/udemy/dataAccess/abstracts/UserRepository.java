package com.etiya.udemy.dataAccess.abstracts;

import com.etiya.udemy.entities.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByMail(String mail);

    Optional<User> findByMail(String mail);
}
