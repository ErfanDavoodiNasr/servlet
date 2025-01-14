package com.github.demoapp.model;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private Integer id;

    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;
}
