package ru.job4j.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"url"})
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotBlank
    private String url;

    @Column(name = "hash_code")
    private String hashCode;

    @PositiveOrZero
    private int counter;

    public Url(String url, String hashCode) {
        this.url = url;
        this.hashCode = hashCode;
    }
}