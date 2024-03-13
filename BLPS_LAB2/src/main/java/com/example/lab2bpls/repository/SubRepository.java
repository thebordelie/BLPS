package com.example.lab2bpls.repository;

import com.example.lab2bpls.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubRepository extends JpaRepository<Subscription, Long> {
    Subscription findBySubscription(String name);
}
