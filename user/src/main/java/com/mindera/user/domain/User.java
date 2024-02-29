package com.mindera.user.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mindera.user.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Embedded
    private Contacts contacts;
    @Column
    private String fiscalNumber;
    @Column
    private Role role;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<Address> addresses;
}
