package com.avp.booking.auth.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class RegisterForm
{
    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
    private static final String EMAIL_MESSAGE = "{email.message}";

    @NotBlank(message = RegisterForm.NOT_BLANK_MESSAGE)
    @Email(message = RegisterForm.EMAIL_MESSAGE)
    private String email;

    @NotBlank(message = RegisterForm.NOT_BLANK_MESSAGE)
    private String password;

    public Account createAccount()
    {
        return new Account(getEmail(), getPassword(), "ROLE_USER");
    }
}
