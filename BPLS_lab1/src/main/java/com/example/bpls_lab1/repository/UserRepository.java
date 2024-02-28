package com.example.bpls_lab1.repository;

import com.example.bpls_lab1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);

    @Modifying
    @Transactional
    @Query(value = "update users_sub set sub_id = 2 where user_id = :user_id", nativeQuery = true)
    void updateSub(@Param("user_id") Long id);
}
