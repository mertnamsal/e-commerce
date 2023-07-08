package com.ecommerce.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // Herkese açık olan endpointler
                .antMatchers("/customer/**").hasAnyRole("CUSTOMER", "VENDOR") // CUSTOMER veya VENDOR rolüne sahip kullanıcılar
                .antMatchers("/vendor/**").hasRole("VENDOR") // VENDOR rolüne sahip kullanıcılar
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("adminpassword"))
                .roles("ADMIN")
                .and()
                .withUser("vendor")
                .password(passwordEncoder().encode("vendorpassword"))
                .roles("VENDOR")
                .and()
                .withUser("customer")
                .password(passwordEncoder().encode("customerpassword"))
                .roles("CUSTOMER");
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
