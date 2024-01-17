package com.recycle.recycle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.Valid;
import java.util.List;

@Table(name = "establishment")
@Entity(name = "establishment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "establishmentId")
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String establishmentId;
    private String name;
    private String telephone;
    @Column(unique = true)
    private String email;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "street", referencedColumnName = "street")
    @JoinColumn(name = "complement", referencedColumnName = "complement")
    @JoinColumn(name = "postalCode", referencedColumnName = "postalCode")
    @Valid
    private Address address;
    @ManyToMany(mappedBy = "establishments")
    private List<Person> employees;
}
