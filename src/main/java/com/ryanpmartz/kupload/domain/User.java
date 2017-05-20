package com.ryanpmartz.kupload.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    private String email;
    private String emailVerificationKey = UUID.randomUUID().toString();
    private boolean enabled;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private Integer failedLoginAttempts = 0;

    private Set<UserRole> roles;

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column
    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "email_verification_key", nullable = false, unique = true)
    public String getEmailVerificationKey() {
        return emailVerificationKey;
    }

    public void setEmailVerificationKey(String emailVerificationKey) {
        this.emailVerificationKey = emailVerificationKey;
    }

    @Column(name = "failed_login_attempts")
    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    @Override
    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToMany(mappedBy = "user")
    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    @Transient
    public String getUsername() {
        return email;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return enabled;
    }
}
