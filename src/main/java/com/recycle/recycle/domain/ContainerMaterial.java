package com.recycle.recycle.domain;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.Valid;

@Table(name = "containerMaterial")
@Entity(name = "containerMaterial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "containerId")
public class ContainerMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String containerId;
    @Valid
    @ManyToOne
    private Material material;
    private String volume;
    private String value;
}
