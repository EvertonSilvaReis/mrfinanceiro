package com.mrsoftware.MRFinanceiro.modelo.entidade;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
@Entity
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String nome;
  private String codigo;
  private String email;
  private String senha;

  @Column(name = "data_exclusao")
  private LocalDateTime dataExclusao;

  @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
  private Set<UsuarioPerfil> usuarioPerfis;
}
