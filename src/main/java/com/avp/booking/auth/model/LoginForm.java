package com.avp.booking.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginForm
{
    private String username;
    private String password;
}
