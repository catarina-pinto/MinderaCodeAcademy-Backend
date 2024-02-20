package com.mindera.user.domain;

import com.mindera.user.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

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
    @Column
    private String username;
    @Column
    private String password;
    @Embedded
    private Contacts contacts;
    @Column
    private String fiscalNumber;
    @Column
    private UserType type;
}
