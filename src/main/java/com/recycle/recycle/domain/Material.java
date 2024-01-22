package com.recycle.recycle.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "material")
@Entity(name = "material")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "materialId")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String materialId;
    private String type;
}
