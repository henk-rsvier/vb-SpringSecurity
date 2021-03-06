/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.springsecurity.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author hwkei
 */
@Entity
@Table(name = "ACCOUNT",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"USERNAME"})})
public class Account implements Serializable, UserDetails {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "USERNAME")
    @NotNull(message="Account naam mag niet leeg zijn")
    @Size(min=3, max=16, message="Account naam moet minimaal 3 tot maximaal 16 tekens bevatten")
    private String username;
    
    @Column(name = "PASSWORD")
    @NotNull(message="Wachtwoord mag niet leeg zijn")
    @Size(min=5, max=250, message="Wachtwoord moet minimaal 5 tot maximaal 250 tekens bevatten")
    private String password;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ACCOUNT_TYPE")
    private AccountType accountType;
    
    public Account() {}
    
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    
    @Override
    public String toString(){
        return String.format("%-5d%-20s%-20s%-20s", this.getId(), this.getUsername(), "********", this.getAccountType());
    }
    
    public String toStringNoId(){
        return String.format("%-20s%-20s%-20s", this.getUsername(), "********", this.getAccountType());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + accountType));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    
}