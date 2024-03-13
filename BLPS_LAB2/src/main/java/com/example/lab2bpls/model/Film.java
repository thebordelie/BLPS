package com.example.lab2bpls.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "film", schema = "public")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Film(String name, String author, String link, String description, Subscription subscription) {
        this.name = name;
        this.author = author;
        this.link = link;
        this.description = description;
        this.subscription = subscription;
    }
}
