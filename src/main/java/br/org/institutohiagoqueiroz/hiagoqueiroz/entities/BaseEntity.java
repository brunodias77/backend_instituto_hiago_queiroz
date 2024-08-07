package br.org.institutohiagoqueiroz.hiagoqueiroz.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder  // Lombok: Gera um padrão Builder para a classe
@AllArgsConstructor  // Lombok: Gera um construtor com todos os argumentos
@NoArgsConstructor  // Lombok: Gera um construtor sem argumentos
@MappedSuperclass  // JPA: Indica que esta classe não é uma entidade por si só, mas suas informações de mapeamento serão aplicadas às entidades que a estendem
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private UUID id;

    @CreatedDate  // Spring Data JPA: Marca o campo com a data de criação da entidade
    @Column(nullable = false, updatable = false)  // JPA: Mapeia o campo para uma coluna no banco de dados, não permitindo nulo e não atualizável
    private LocalDateTime createdDate;

    @LastModifiedDate  // Spring Data JPA: Marca o campo com a data da última modificação da entidade
    @Column(insertable = false)  // JPA: Mapeia o campo para uma coluna no banco de dados, apenas para inserção, não atualizável
    private LocalDateTime lastModifiedDate;
}
