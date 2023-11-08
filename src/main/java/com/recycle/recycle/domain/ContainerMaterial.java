package com.recycle.recycle.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "containerMaterial")
@Entity(name = "containerMaterial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ContainerMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String containerId;
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
    @Column(nullable = false)
    private String volume;
    @Column(nullable = false)
    private String value;
    @ManyToOne
    @JoinColumn(
            name = "machine_id",
            nullable = false,
            referencedColumnName = "machineId",
            foreignKey = @ForeignKey(
                    name = "container_machine_fk"
            )
    )
    private Machine machine;


}
