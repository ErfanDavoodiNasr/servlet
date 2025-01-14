package com.github.demoapp.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @GeneratedValue
    private Integer id;

    private String permission;
}
