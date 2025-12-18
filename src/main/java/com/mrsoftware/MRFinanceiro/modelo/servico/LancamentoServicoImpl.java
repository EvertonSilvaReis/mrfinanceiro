package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoRetornoDTO;
import com.mrsoftware.MRFinanceiro.exception.ExceptionAbstractImpl;
import com.mrsoftware.MRFinanceiro.exception.InternalServerErrorException;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.LancamentoServico;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LancamentoServicoImpl implements LancamentoServico {

  private static final String MENSAGEM_ERRO = "Ocorreu um erro ao {} pessoa.";

  @Override
  public LancamentoRetornoDTO cadastrar(LancamentoEntradaDTO lancamentoEntradaDTO) {
    try {
      return null;
    } catch (ExceptionAbstractImpl ex) {
      throw ex;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "cadastrar", e);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }
}
