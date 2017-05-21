package com.ryanpmartz.kupload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration Class for all security-related Spring configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Resource
//    private UserDetailsService userDetailsService;
//
//    @Resource
//    private SimpleUrlAuthenticationSuccessHandler successHandler;
//
//    @Resource
//    private SimpleUrlAuthenticationFailureHandler authFalureHandler;

    @Bean
    public PasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // spring security automatically inserts ROLE_ before name of role when building intercept rules
                .antMatchers("/admin/**").hasRole("ADMIN")
                // home page
                .antMatchers("/").permitAll()
                // allow static resources
                .antMatchers("/static/**", "/register", "/register/**", "/login-failed").permitAll()
                .antMatchers("/account/locked", "/help").permitAll()

                // all other request paths are protected
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
//                .successHandler(successHandler)
//                .failureHandler(authFalureHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .clearAuthentication(true)
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll();

    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bcryptEncoder());
//    }
}
