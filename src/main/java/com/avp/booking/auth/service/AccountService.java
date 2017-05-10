package com.avp.booking.auth.service;

import com.avp.booking.auth.model.Account;
import com.avp.booking.auth.model.AccountRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccountService implements UserDetailsService
{
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct    // TODO remove
    protected void initialize()
    {
        save(new Account("user@mail.ru", "demo", "ROLE_USER"));
        save(new Account("admin@admin.ru", "admin", "ROLE_ADMIN"));
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Account save(Account account)
    {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return account;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Account account = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email = " + username + " was not found"));

        if (account.getRole() == null || account.getRole() == "")
        {
            // No Roles assigned to user...
            throw new UsernameNotFoundException("User with email = " + username + " was not authorized");
        }

        return createUser(account);
    }

    public void signin(Account account)
    {
        SecurityContextHolder.getContext().setAuthentication(authenticate(account));
    }

    private Authentication authenticate(Account account)
    {
        return new UsernamePasswordAuthenticationToken(
                createUser(account), null, ImmutableList.of(createAuthority(account)));
    }

    private User createUser(Account account)
    {
        return new User(account.getEmail(), account.getPassword(), ImmutableList.of(createAuthority(account)));
    }

    private GrantedAuthority createAuthority(Account account)
    {
        return new SimpleGrantedAuthority(account.getRole());
    }
}
