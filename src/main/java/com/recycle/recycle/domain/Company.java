package com.recycle.recycle.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Table(name = "company")
@Entity(name = "company")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "companyId")
public class Company{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String companyId;
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
    @ManyToMany(mappedBy = "companies")
    private List<Person> employees;
}
