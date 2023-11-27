package com.recycle.recycle.domain;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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