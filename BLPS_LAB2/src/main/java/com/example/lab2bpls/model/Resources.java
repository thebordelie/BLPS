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
@Table(name = "resources", schema = "public")
public class Resources {
    @Id
    @Column(name = "film_id")
    private long id;

    @Column(name = "money")
    private int money;

    @Column(name = "income")
    private int income;
}
