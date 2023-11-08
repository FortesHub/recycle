package com.recycle.recycle.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name="material")
@Entity(name="material")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Material {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String MaterialId;
    @Column(nullable=false)
    private String type;

}
