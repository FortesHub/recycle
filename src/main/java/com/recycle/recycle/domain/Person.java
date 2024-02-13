package com.recycle.recycle.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "person")
@Entity(name = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "personId")
public class Person{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String personId;
    private String name;
    private String telephone;
    @Column(unique = true)
    private String email;
}