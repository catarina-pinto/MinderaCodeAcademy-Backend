package com.mindera.user.domain;

import com.mindera.user.domain.Address;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
public class Contacts {
    @Column
    private String email;
    @Column
    private String phoneNumber;
    @OneToMany(mappedBy = "user")
    private Set<Address> addresses;
}
