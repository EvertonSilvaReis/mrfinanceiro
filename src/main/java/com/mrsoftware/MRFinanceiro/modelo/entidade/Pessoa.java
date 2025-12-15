package com.mrsoftware.MRFinanceiro.modelo.entidade;

import com.mrsoftware.MRFinanceiro.modelo.enumeradores.ETipoPessoa;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pessoa")
@Entity
@Builder
public class Pessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String nome;

  @Column(name = "cpf_cnpj")
  private String cpfCnpj;

  private String codigo;

  @Column(name = "data_cadastro")
  private LocalDate dataCadastro;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_pessoa")
  private ETipoPessoa tipoPessoa;

  @OneToMany(mappedBy = "pessoa")
  private List<Lancamento> lancamentos;

  @Column(name = "data_exclusao")
  private LocalDateTime dataExclusao;
}
