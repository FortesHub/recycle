package com.recycle.recycle.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="establishment")
@Entity(name="establishment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Establishment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @JoinColumn(name="person_id")
    @OneToOne
    private Person person;
    @ManyToOne
    @JoinColumn(name="address_id")
    private Address address;
}
