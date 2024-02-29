package com.mindera.user.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(unique = true)
    private String email;
    @Column
    private String phoneNumber;
}
