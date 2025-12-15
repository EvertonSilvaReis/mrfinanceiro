package com.mrsoftware.MRFinanceiro.modelo.entidade;

import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EStatusTitulo;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.ETipoLancamento;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lancamento")
@Entity
public class Lancamento {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String descricao;

  private String codigo;

  @Column(name = "numero_documento")
  private String numeroDocumento;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_lancamento")
  private ETipoLancamento tipoLancamento;

  @Column(name = "valor_titulo")
  private BigDecimal valorTitulo;

  private BigDecimal desconto;

  @Column(name = "data_emissao")
  private LocalDate dataEmissao;

  @Column(name = "data_vencimento")
  private LocalDate dataVencimento;

  @Column(name = "valor_pagamento")
  private BigDecimal valorPagamento;

  @Column(name = "data_pagamento")
  private LocalDate dataPagamento;

  @Enumerated(EnumType.STRING)
  private EStatusTitulo situacao;

  private String observacao;

  @ManyToOne
  @JoinColumn(name = "id_tipo_pagamento")
  private TipoPagamento tipoPagamento;

  private Integer parcela;

  @ManyToOne
  @JoinColumn(name = "id_pessoa")
  private Pessoa pessoa;

  @Column(name = "data_exclusao")
  private LocalDateTime dataExclusao;
}
