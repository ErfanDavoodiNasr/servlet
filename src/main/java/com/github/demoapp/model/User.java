package com.github.demoapp.model;


import jakarta.persistence.*;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_image",length = 10_000)
    private String profileImage;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "national_code", unique = true)
    private String nationalCode;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    //مثلا برای اینکه بتونیم برای کاربر ادمین  یک رول اضافه تر هم اگر دوست داشتیم اضافه کنیم
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;

    @Column(name = "birth_date")
    private String birthDate;
}
