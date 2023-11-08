package com.recycle.recycle.domain;

import com.recycle.recycle.dto.PersonDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name="person")
@Entity(name="person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String telephone;
    @Column(unique = true)
    private String email;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    public Person(PersonDTO data) {
        this.name = data.name();
        this.telephone = data.telephone();
        this.email = data.email();
        this.address = data.address();
    }

}