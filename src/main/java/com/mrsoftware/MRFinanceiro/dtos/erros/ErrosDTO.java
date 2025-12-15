package com.mrsoftware.MRFinanceiro.dtos.erros;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrosDTO {
  private List<ErroDTO> erros;
}
