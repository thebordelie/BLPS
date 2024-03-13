package com.example.lab2bpls.repository;

import com.example.lab2bpls.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);

    Optional<User> findById(Long id);

    @Modifying
    @Query(value = "update users_sub set sub_id = 2 where user_id = :user_id", nativeQuery = true)
    void updateSub(@Param("user_id") Long id);

    @Modifying
    @Query(value = "insert into subscription_date_purchase values (:user_id, :date)", nativeQuery = true)
    void addLog(@Param("user_id") Long id, @Param("date") Date date);
}
