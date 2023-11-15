package com.abo2.recode.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long userId);
<<<<<<< HEAD
    void deleteById(Long userId);
=======

    Optional<User> deleteById(String userId);
>>>>>>> d912916acbc2aa92876a003f580a60b0a583b328
}
