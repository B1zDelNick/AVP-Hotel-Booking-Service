package com.avp.booking.auth.model;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AccountRepository extends Repository<Account, Integer>
{
    Optional<Account> findByEmail(String email);
    void save(Account account);
}
