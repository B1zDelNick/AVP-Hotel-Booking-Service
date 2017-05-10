package com.avp.booking.auth.controll;

import com.avp.booking.auth.model.Account;
import com.avp.booking.auth.model.LoginForm;
import com.avp.booking.auth.model.RegisterForm;
import com.avp.booking.auth.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@Slf4j
public class AuthController
{
    private final AccountService accountService;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public AuthController(AccountService accountService)
    {
        this.accountService = accountService;
    }

    @PostMapping("register")
    public String register(@ModelAttribute RegisterForm registerForm)
    {
        if (registerForm.getEmail().isEmpty() || registerForm.getPassword().isEmpty())
        {
            return "redirect:/register?error=1";
        }

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(registerForm.getEmail());

        if (!matcher.matches())
        {
            return "redirect:/register?error=2";
        }

        Account account = accountService.save(registerForm.createAccount());
        accountService.signin(account);
        return "redirect:/";
    }

    @PostMapping("isLogged")
    public ResponseEntity<Message> isLogged(Principal principal)
    {
        log.debug("Is Logged requested with result:" + principal);

        Message message = new Message();
        message.body = principal == null ? "none" : principal.getName();

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private class Message
    {
        public String body;
    }
}
