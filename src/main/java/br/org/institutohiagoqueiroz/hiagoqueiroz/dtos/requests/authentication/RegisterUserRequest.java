package br.org.institutohiagoqueiroz.hiagoqueiroz.dtos.requests.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserRequest {
    @NotEmpty(message = "O primeiro nome é obrigatorio")
    @NotNull(message = "O primeiro nome é obrigatorio")
    private String firstName;
    @NotEmpty(message = "O sobrenome é obrigatorio")
    @NotNull(message = "O sobrenome é obrigatorio")
    private String lastName;
    @Email(message = "E-mail esta em um formato invalido")
    @NotEmpty(message = "O e-mail e obrigatorio")
    @NotNull(message = "O e-mail e obrigatorio")
    private String email;
    @NotEmpty(message = "Senha é obrigatória")
    @NotNull(message = "Senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String password;
}
