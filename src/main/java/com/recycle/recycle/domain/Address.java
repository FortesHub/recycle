package com.recycle.recycle.domain;

import jakarta.persistence.*;

import lombok.*;

import javax.validation.constraints.NotNull;


@Table(name = "address")
@Entity(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String complement;
    private String postalCode;
    private String city;
    private String pays;

}
