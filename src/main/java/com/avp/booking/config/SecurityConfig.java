package com.avp.booking.config;

import com.avp.booking.auth.controll.RedirectAuthenticationSuccessHandler;
import com.avp.booking.auth.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private RedirectAuthenticationSuccessHandler successHandler;

    @Autowired
    private AccountService accountService;

    @Bean
    public TokenBasedRememberMeServices rememberMeServices()
    {
        return new TokenBasedRememberMeServices("remember-me-key", accountService);
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth
            .eraseCredentials(true)
            .userDetailsService(accountService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
            .authorizeRequests()
                .antMatchers()
                    .fullyAuthenticated()
                .anyRequest()
                    .permitAll()
            .and()
                .formLogin()
                    .loginPage("/login")
                    .successHandler(successHandler)
                    .failureUrl("/login?error")
                    .loginProcessingUrl("/login")
            .and()
                .logout()
                    .logoutUrl("/logout")
                    .permitAll()
                    .logoutSuccessUrl("/logout2")
            .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices())
                .key("remember-me-key")
            .and()
                .csrf().disable();
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
}
