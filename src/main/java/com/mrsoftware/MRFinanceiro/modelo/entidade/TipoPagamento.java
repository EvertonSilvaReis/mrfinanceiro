package com.mrsoftware.MRFinanceiro.modelo.entidade;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tipo_pagamento")
@Entity
public class TipoPagamento {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String descricao;
  private String codigo;
  private Boolean parcelado;

  @Column(name = "numero_parcela")
  private Integer parcelas;

  @Column(name = "data_exclusao")
  private LocalDateTime dataExclusao;
}
