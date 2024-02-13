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
@EqualsAndHashCode(of = "machineId")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String machineId;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Boolean isFull;
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "material_id"),
            inverseJoinColumns = @JoinColumn(name = "machine_id")
    )
    private List<Material> containers;
}
