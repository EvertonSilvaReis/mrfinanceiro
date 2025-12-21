package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoRetornoDTO;
import com.mrsoftware.MRFinanceiro.exception.ExceptionAbstractImpl;
import com.mrsoftware.MRFinanceiro.exception.InternalServerErrorException;
import com.mrsoftware.MRFinanceiro.modelo.builder.LancamentoBuilder;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Conta;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Lancamento;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Pessoa;
import com.mrsoftware.MRFinanceiro.modelo.entidade.TipoPagamento;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.repositorios.LancamentoRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.ContaServico;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.LancamentoServico;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.PessoaServico;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.TipoPagamentoServico;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LancamentoServicoImpl implements LancamentoServico {

  @Autowired private LancamentoRepositorio lancamentoRepositorio;
  @Autowired private PessoaServico pessoaServico;
  @Autowired private TipoPagamentoServico tipoPagamentoServico;
  @Autowired private ContaServico contaServico;

  private static final String MENSAGEM_ERRO = "Ocorreu um erro ao {} pessoa.";

  @Override
  public LancamentoRetornoDTO cadastrar(LancamentoEntradaDTO lancamentoEntradaDTO) {
    try {
      Pessoa pessoa = pessoaServico.obterPessoaPorId(lancamentoEntradaDTO.getPessoa());
      TipoPagamento tipoPagamento =
          tipoPagamentoServico.obterTipoPagamentoPorId(lancamentoEntradaDTO.getTipoPagamento());
      Conta conta = contaServico.obterContaPorId(lancamentoEntradaDTO.getConta());
      Lancamento lancamento =
          lancamentoRepositorio.save(
              new LancamentoBuilder()
                  .addLancamentoEntrada(lancamentoEntradaDTO)
                  .addPessoa(pessoa)
                  .addTipoPagamento(tipoPagamento)
                  .addConta(conta)
                  .buildDtoParaEntidade());

      return new LancamentoBuilder().addLancamento(lancamento).buildEntidadeParaDto();
    } catch (ExceptionAbstractImpl ex) {
      throw ex;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "cadastrar", e);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }
}
