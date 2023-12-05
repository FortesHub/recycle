package com.recycle.recycle.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Table(name = "person")
@Entity(name = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String telephone;
    @Column(unique = true)
    private String email;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @Valid
    private Address address;
}