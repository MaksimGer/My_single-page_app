package org.example.config;

import org.example.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configurable
@EnableWebSecurity
// Modifying or overriding the default spring boot security.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    public void configure(WebSecurity web){

        web.ignoring()
                .antMatchers("/", "/index.html", "/app/**", "/register", "/authenticate", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // starts authorizing configurations
                .authorizeRequests()
                // authenticate all remaining URLS
                .anyRequest().fullyAuthenticated().and()
                // adding JWT filter
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                // enabling the basic authentication
                .httpBasic().and()
                // configuring the session as state less. Which means there is
                // no session in the server
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // disabling the CSRF - Cross Site Request Forgery
                .csrf().disable();
    }
}