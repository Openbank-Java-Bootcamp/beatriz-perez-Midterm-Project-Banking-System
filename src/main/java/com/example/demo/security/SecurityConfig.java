package com.example.demo.security;

import com.example.demo.filter.CustomAuthenticationFilter;
import com.example.demo.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        // url to login:
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        // ----------------------------------------------------------------------------------------------------------------------
        // Anyone ca access:
        // LOG IN
        http.authorizeRequests().antMatchers("/api/login/**").permitAll();

        // ----------------------------------------------------------------------------------------------------------------------
        // You need to have an ADMIN role to :

        // ROLES:
        // get a list of all existing roles
        http.authorizeRequests().antMatchers(GET, "/api/roles").hasAnyAuthority("ROLE_ADMIN");
        // create a new role
        http.authorizeRequests().antMatchers(POST, "/api/roles").hasAnyAuthority("ROLE_ADMIN");
        // assign a role to a user manually
        http.authorizeRequests().antMatchers(POST, "/api/roles/assign").hasAnyAuthority("ROLE_ADMIN");

        // USERS:
        // get a list of all active users
        http.authorizeRequests().antMatchers(GET, "/api/users").hasAnyAuthority("ROLE_ADMIN");
        // get the details of a user by ID
        http.authorizeRequests().antMatchers(GET, "/api/users/{id}").hasAnyAuthority("ROLE_ADMIN");
        // get a list of all active third parties
        http.authorizeRequests().antMatchers(GET, "/api/users/third-party").hasAnyAuthority("ROLE_ADMIN");

        // create a new admin-user
        http.authorizeRequests().antMatchers(POST, "/api/users/admin-user").hasAnyAuthority("ROLE_ADMIN");
        // create a new AccountHolder user
        http.authorizeRequests().antMatchers(POST, "/api/users/account-holder").hasAnyAuthority("ROLE_ADMIN");
        // create a new ThirdParty user
        http.authorizeRequests().antMatchers(POST, "/api/users/third-party").hasAnyAuthority("ROLE_ADMIN");

        // update an admin-user
        http.authorizeRequests().antMatchers(PUT, "/api/users/admin-user/{id}").hasAnyAuthority("ROLE_ADMIN");
        // update an AccountHolder user
        http.authorizeRequests().antMatchers(PUT, "/api/users/account-holder/{id}").hasAnyAuthority("ROLE_ADMIN");
        // update a ThirdParty user
        http.authorizeRequests().antMatchers(PUT, "/api/users/third-party/{id}").hasAnyAuthority("ROLE_ADMIN");

        // delete a user by ID
        http.authorizeRequests().antMatchers(DELETE, "/api/users/{id}").hasAnyAuthority("ROLE_ADMIN");
        // delete a ThirdParty user by ID
        http.authorizeRequests().antMatchers(DELETE, "/api/users/third-party/{id}").hasAnyAuthority("ROLE_ADMIN");


        // ACCOUNTS:
        // get a list of all active accounts
        http.authorizeRequests().antMatchers(GET, "/api/accounts").hasAnyAuthority("ROLE_ADMIN");
        // get the details of an account by account number
        http.authorizeRequests().antMatchers(GET, "/api/accounts/{account-number}").hasAnyAuthority("ROLE_ADMIN");

        // create a new Checking Account
        http.authorizeRequests().antMatchers(POST, "/api/accounts/checking").hasAnyAuthority("ROLE_ADMIN");
        // create a new Credit Card Account
        http.authorizeRequests().antMatchers(POST, "/api/accounts/credit-card").hasAnyAuthority("ROLE_ADMIN");
        // create a new Savings Account
        http.authorizeRequests().antMatchers(POST, "/api/accounts/savings").hasAnyAuthority("ROLE_ADMIN");

        // freely modify an account's balance
        http.authorizeRequests().antMatchers(PATCH, "/api/accounts/{account-number}").hasAnyAuthority("ROLE_ADMIN");

        // delete an account by account number
        http.authorizeRequests().antMatchers(DELETE, "/api/accounts/{account-number}").hasAnyAuthority("ROLE_ADMIN");



        // For any request you should de authenticated (logged in):
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}





