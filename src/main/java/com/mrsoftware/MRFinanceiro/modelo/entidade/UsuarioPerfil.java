package com.mrsoftware.MRFinanceiro.modelo.entidade;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario_perfil")
@Entity
public class UsuarioPerfil {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "id_usuario")
  private Usuario usuario;

  @Column(name = "role")
  private String perfil;
}
