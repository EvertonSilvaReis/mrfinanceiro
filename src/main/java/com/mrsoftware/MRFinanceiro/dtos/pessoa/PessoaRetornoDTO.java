package com.mrsoftware.MRFinanceiro.dtos.pessoa;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoRetornoDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PessoaRetornoDTO {
  private UUID id;
  private String nome;
  private String cpfCnpj;
  private Integer tipoPessoa;
  private LocalDate dataCadastro;
  private List<LancamentoRetornoDTO> lancamentos;
}
