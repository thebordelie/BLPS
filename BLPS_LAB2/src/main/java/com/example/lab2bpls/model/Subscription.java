package com.example.lab2bpls.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "subscription", schema = "public")
public class Subscription {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String subscription;
}
