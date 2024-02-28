package com.example.bpls_lab1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "film", schema = "public")
public class Film {

    @Id
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "link")
    private String link;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinTable(
            name = "film_sub",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_id")
    )
    private Subscription subscription;


}
