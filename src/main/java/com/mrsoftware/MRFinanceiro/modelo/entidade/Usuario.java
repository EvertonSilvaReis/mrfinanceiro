package com.mrsoftware.MRFinanceiro.modelo.entidade;

import com.mrsoftware.MRFinanceiro.modelo.enumeradores.ETipoUsuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "tipo_usuario")
  private ETipoUsuario tipoUsuario;

  @Column(name = "data_exclusao")
  private LocalDateTime dataExclusao;
}
