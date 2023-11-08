package com.recycle.recycle.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "machine")
@Entity(name = "machine")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String machineId;
    @ManyToOne
    private Company company;
    @ManyToOne
    private Establishment establishment;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(
            orphanRemoval = true,
            mappedBy = "machine",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL})
    private List<ContainerMaterial> containers;

}
