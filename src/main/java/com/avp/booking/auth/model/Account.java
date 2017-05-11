package com.avp.booking.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
public class Account
{
    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String role;

    public Account(String email, String password, String role)
    {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
