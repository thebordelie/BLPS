package com.example.bpls_lab1.repository;

import com.example.bpls_lab1.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubRepository extends JpaRepository<Subscription, Long> {
    Subscription findBySubscription(String name);
}
