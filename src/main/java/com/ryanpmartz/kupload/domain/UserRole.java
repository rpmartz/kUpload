package com.ryanpmartz.kupload.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "authorities")
public class UserRole extends BaseEntity implements GrantedAuthority {

    private static final long serialVersionUid = 1L;

    private UserRoleEnum userRole;
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false)
    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Transient
    @Override
    public String getAuthority() {
        return userRole.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserRole userRole1 = (UserRole) o;

        if (userRole != userRole1.userRole) {
            return false;
        }

        return user != null ? user.equals(userRole1.user) : userRole1.user == null;
    }

    @Override
    public int hashCode() {
        int result = userRole != null ? userRole.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
