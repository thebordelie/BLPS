package com.example.bpls_lab1.model;

import jakarta.persistence.*;
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
