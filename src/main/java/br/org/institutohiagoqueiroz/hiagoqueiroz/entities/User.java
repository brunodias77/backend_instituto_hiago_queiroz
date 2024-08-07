package br.org.institutohiagoqueiroz.hiagoqueiroz.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity implements UserDetails {

    @NotBlank(message = "O campo nome não pode ficar em branco")
    private String name;

    @NotBlank(message = "O campo nome de usuário não pode ficar em branco")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "O campo de email não pode ficar em branco")
    @Column(unique = true)
    @Email(message = "Por favor, digite o e-mail no formato correto!")
    private String email;

    @NotBlank(message = "O campo de senha não pode ficar em branco")
    @Size(min = 5, message = "A senha deve ter pelo menos 5 caracteres")
    private String password;

//    @OneToOne(mappedBy = "user")
//    private ForgotPassword forgotPassword;
//
//    @OneToOne(mappedBy = "user")
//    private RefreshToken refreshToken;
//
    @Enumerated(EnumType.STRING) // especificar como um campo do tipo enum deve ser mapeado para uma coluna em um banco de dados.
    private UserRole role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
