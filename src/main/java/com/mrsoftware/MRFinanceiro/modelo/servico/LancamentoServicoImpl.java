package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoBaixaDTO;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoRetornoDTO;
import com.mrsoftware.MRFinanceiro.exception.BadRequestException;
import com.mrsoftware.MRFinanceiro.exception.ExceptionAbstractImpl;
import com.mrsoftware.MRFinanceiro.exception.InternalServerErrorException;
import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import com.mrsoftware.MRFinanceiro.modelo.builder.LancamentoBuilder;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Conta;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Lancamento;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Pessoa;
import com.mrsoftware.MRFinanceiro.modelo.entidade.TipoPagamento;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EStatusTitulo;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.repositorios.LancamentoRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.*;
import com.mrsoftware.MRFinanceiro.util.IdUtil;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LancamentoServicoImpl implements LancamentoServico {

  private LancamentoRepositorio lancamentoRepositorio;
  private PessoaServico pessoaServico;
  private TipoPagamentoServico tipoPagamentoServico;
  private ContaServico contaServico;
  private ConfiguracaoServico configuracaoServico;

  private static final String MENSAGEM_ERRO = "Ocorreu um erro ao {} lancamento.";
  private static final String ULTIMO_CODIGO = "ultimo-codigo-lancamento";

  @Transactional
  @Override
  public List<LancamentoRetornoDTO> cadastrar(LancamentoEntradaDTO lancamentoEntradaDTO) {
    try {
      Pessoa pessoa = pessoaServico.obterPessoaPorId(lancamentoEntradaDTO.getPessoa());
      TipoPagamento tipoPagamento =
          tipoPagamentoServico.obterTipoPagamentoPorId(lancamentoEntradaDTO.getTipoPagamento());
      Conta conta = contaServico.obterContaPorId(lancamentoEntradaDTO.getConta());

      List<Lancamento> lancamentos =
          gerarLancamentos(lancamentoEntradaDTO, pessoa, tipoPagamento, conta);

      return new LancamentoBuilder().addLancamento(lancamentos).buildEntidadeParaDtoCadastro();
    } catch (ExceptionAbstractImpl ex) {
      throw ex;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "cadastrar", e);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public LancamentoRetornoDTO atualizar(String id, LancamentoEntradaDTO lancamentoEntradaDTO) {
    try {
      Lancamento lancamento = obterLancamentoPorId(id);
      validaSeLancamentoPodeSerAtualizado(lancamento);
      lancamentoRepositorio.save(
          new LancamentoBuilder()
              .addLancamento(lancamento)
              .addLancamentoEntrada(lancamentoEntradaDTO)
              .addPessoa(
                  Objects.nonNull(lancamentoEntradaDTO.getPessoa())
                      ? pessoaServico.obterPessoaPorId(lancamentoEntradaDTO.getPessoa())
                      : lancamento.getPessoa())
              .addTipoPagamento(
                  Objects.nonNull(lancamentoEntradaDTO.getTipoPagamento())
                      ? tipoPagamentoServico.obterTipoPagamentoPorId(
                          lancamentoEntradaDTO.getTipoPagamento())
                      : lancamento.getTipoPagamento())
              .addConta(
                  Objects.nonNull(lancamentoEntradaDTO.getConta())
                      ? contaServico.obterContaPorId(lancamentoEntradaDTO.getConta())
                      : lancamento.getConta())
              .buildAtualizacaoDtoParaEntidade());

      return new LancamentoBuilder().addLancamento(lancamento).buildEntidadeParaDto();
    } catch (ExceptionAbstractImpl ex) {
      throw ex;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "atualizar", e);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public LancamentoRetornoDTO retornarPorId(String id) {
    try {
      return new LancamentoBuilder().addLancamento(obterLancamentoPorId(id)).buildEntidadeParaDto();
    } catch (ExceptionAbstractImpl ex) {
      throw ex;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "retornar por id", e);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public void excluir(String id) {
    try {
      Lancamento lancamento = obterLancamentoPorId(id);
      lancamento.setDataExclusao(LocalDateTime.now());
      lancamentoRepositorio.save(lancamento);
    } catch (ExceptionAbstractImpl ex) {
      throw ex;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "excluir", e);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public LancamentoRetornoDTO baixarTitulo(String id, LancamentoBaixaDTO lancamentoBaixaDTO) {
    try {
      Lancamento lancamento = obterLancamentoPorId(id);
      validaSeLancamentoPodeSerAtualizado(lancamento);
      boolean situacaoBaixa = validaSeTituloBaixadoIntegralmente(lancamento, lancamentoBaixaDTO);
      Lancamento lancamentoBaixado =
          lancamentoRepositorio.save(
              new LancamentoBuilder()
                  .addLancamento(lancamento)
                  .addLancamentoBaixaDTO(lancamentoBaixaDTO)
                  .buildBaixaLancamento(
                      situacaoBaixa ? EStatusTitulo.BAIXADO : EStatusTitulo.BAIXA_PARCIAL));
      return new LancamentoBuilder().addLancamento(lancamentoBaixado).buildEntidadeParaDto();
    } catch (ExceptionAbstractImpl ex) {
      throw ex;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "baixar titulo", e);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  private List<Lancamento> gerarLancamentos(
      LancamentoEntradaDTO lancamentoEntradaDTO,
      Pessoa pessoa,
      TipoPagamento tipoPagamento,
      Conta conta) {
    List<Lancamento> lancamentos = new ArrayList<>();

    for (int i = 1; i <= tipoPagamento.getParcelas(); i++) {
      Lancamento lancamento =
          new LancamentoBuilder()
              .addLancamentoEntrada(lancamentoEntradaDTO)
              .addPessoa(pessoa)
              .addTipoPagamento(tipoPagamento)
              .addConta(conta)
              .buildDtoParaEntidade();
      lancamento.setParcela(i);
      lancamento.setNumeroDocumento(lancamentoEntradaDTO.getNumeroDocumento() + "/" + i);
      lancamento.setValorDocumento(
          lancamentoEntradaDTO
              .getValorTitulo()
              .divide(BigDecimal.valueOf(tipoPagamento.getParcelas())));
      adicionaCodigoLancamento(lancamento);
      lancamentos.add(lancamento);
    }
    return lancamentoRepositorio.saveAll(lancamentos);
  }

  private boolean validaSeTituloBaixadoIntegralmente(
      Lancamento lancamento, LancamentoBaixaDTO lancamentoBaixaDTO) {
    if (lancamento.getValorPagamento().compareTo(lancamentoBaixaDTO.getValorPagamento()) != 0) {
      BigDecimal valorRestanteParaPagamento =
          lancamento.getValorPagamento().subtract(lancamentoBaixaDTO.getValorPagamento());
      Lancamento novoLancamento =
          new LancamentoBuilder()
              .addLancamento(lancamento)
              .buildLancamentoParcial(valorRestanteParaPagamento);
      lancamentoRepositorio.save(novoLancamento);
      return false;
    }
    return true;
  }

  private Lancamento obterLancamentoPorId(String id) {
    return lancamentoRepositorio
        .findByIdAndDataExclusaoIsNull(IdUtil.obterUUID(id))
        .orElseThrow(() -> new NotFoundException(EValidacao.LANCAMENTO_NAO_ENCONTRADO));
  }

  private void validaSeLancamentoPodeSerAtualizado(Lancamento lancamento) {
    if (EStatusTitulo.obterStatusTitulo(lancamento.getTipoLancamento())
            .equals(EStatusTitulo.BAIXADO)
        || EStatusTitulo.obterStatusTitulo(lancamento.getTipoLancamento())
            .equals(EStatusTitulo.BAIXA_PARCIAL)) {
      throw new BadRequestException(
          EValidacao.LANCAMENTO_BAIXADO_NAO_PODE_SER_ATUALIZADO, lancamento.getId().toString());
    }
  }

  private void adicionaCodigoLancamento(Lancamento lancamento) {
    String codigo = String.format("%06d", configuracaoServico.obterCodigo(ULTIMO_CODIGO));
    lancamento.setCodigo(codigo);
  }
}
