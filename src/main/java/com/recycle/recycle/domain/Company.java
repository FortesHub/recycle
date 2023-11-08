package com.recycle.recycle.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="company")
@Entity(name="company")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(of = "id")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String companyId;
    @OneToOne
    @JoinColumn(name="person_id")
    private Person person;
    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;
}
