package com.mrsoftware.MRFinanceiro.modelo.entidade;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "conta")
@Entity
public class Conta {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String descricao;

  @Column(name = "numero_conta")
  private Integer numeroConta;

  private Integer agencia;

  @Column(name = "saldo_inicial")
  private BigDecimal saldoInicial;

  @Column(name = "saldo_atual")
  private BigDecimal saldoAtual;

  @OneToMany(mappedBy = "conta")
  private List<Lancamento> lancamentos;

  private Boolean ativo;

  @Column(name = "data_exclusao")
  private LocalDateTime dataExclusao;
}
