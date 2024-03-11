package com.springecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = {@UniqueConstraint(
                name = "email_unique",
                columnNames = {"email"}
        )}
)
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long customerId;

    @NotBlank(message = "please add First name")
    private String firstName;

    @NotBlank(message = "please add Last name")
    private String lastName;

    @Email(message = "please add valid mail")
    @NotBlank(message = "please add email")
    private String email;

    @NotBlank(message = "please add password")
    @Length(max = 255, min = 4)
//    @Column(name = "password", length = 255)
    private String password;


    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL}
    )
    private List<Order> orders;

    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
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