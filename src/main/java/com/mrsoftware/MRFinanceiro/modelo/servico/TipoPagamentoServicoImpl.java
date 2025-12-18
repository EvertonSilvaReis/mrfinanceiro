package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoEntradaPaginadaDTO;
import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.TipoPagamentoRetornoPaginadoDTO;
import com.mrsoftware.MRFinanceiro.exception.BadRequestException;
import com.mrsoftware.MRFinanceiro.exception.ExceptionAbstractImpl;
import com.mrsoftware.MRFinanceiro.exception.InternalServerErrorException;
import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import com.mrsoftware.MRFinanceiro.modelo.builder.TipoPagamentoBuilder;
import com.mrsoftware.MRFinanceiro.modelo.entidade.TipoPagamento;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.repositorios.TipoPagamentoRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.ConfiguracaoServico;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.TipoPagamentoServico;
import com.mrsoftware.MRFinanceiro.util.IdUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TipoPagamentoServicoImpl implements TipoPagamentoServico {

  @Autowired private TipoPagamentoRepositorio tipoPagamentoRepositorio;
  @Autowired private ConfiguracaoServico configuracaoServico;

  private static final String MENSAGEM_ERRO = "Erro ao {} tipo de pagamento: {}";
  private static final String ULTIMO_CODIGO = "ultimo-codigo-tipo-pagamento";

  @Override
  public TipoPagamentoRetornoDTO cadastrar(TipoPagamentoEntradaDTO tipoPagamentoEntradaDTO) {
    try {
      validaSeTipoPagamentoJaCadastrado(tipoPagamentoEntradaDTO);
      TipoPagamento tipoPagamento =
          new TipoPagamentoBuilder()
              .addTipoPagamentoEntradaDTO(tipoPagamentoEntradaDTO)
              .buildCadastrarTipoPagamento();
      adicionaCodigoTipoPagamento(tipoPagamento);

      return new TipoPagamentoBuilder()
          .addTipoPagamento(tipoPagamentoRepositorio.save(tipoPagamento))
          .buildRetornoTipoPagamento();
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception ex) {
      log.error(MENSAGEM_ERRO, "cadastrar", ex);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public TipoPagamentoRetornoDTO atualizar(
      String id, TipoPagamentoEntradaDTO tipoPagamentoEntradaDTO) {
    try {
      TipoPagamento tipoPagamento =
          new TipoPagamentoBuilder()
              .addTipoPagamento(obterTipoPagamentoPorId(IdUtil.obterUUID(id)))
              .addTipoPagamentoEntradaDTO(tipoPagamentoEntradaDTO)
              .buildAtualizarPessoa();
      return new TipoPagamentoBuilder()
          .addTipoPagamento(tipoPagamentoRepositorio.save(tipoPagamento))
          .buildRetornoTipoPagamento();
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception ex) {
      log.error(MENSAGEM_ERRO, "atualizar", ex);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public TipoPagamentoRetornoDTO retornarTipoPagamentoPorId(String id) {
    try {
      return new TipoPagamentoBuilder()
          .addTipoPagamento(obterTipoPagamentoPorId(IdUtil.obterUUID(id)))
          .buildRetornoTipoPagamento();
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception ex) {
      log.error(MENSAGEM_ERRO, "retornar", ex);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public TipoPagamentoRetornoPaginadoDTO retornarPorFiltro(
      TipoPagamentoEntradaPaginadaDTO tipoPagamentoEntradaDTO) {
    try {
      Pageable paginacao =
          PageRequest.of(
              tipoPagamentoEntradaDTO.getPagina() > 0
                  ? (tipoPagamentoEntradaDTO.getPagina() - 1)
                  : 0,
              tipoPagamentoEntradaDTO.getItensPorPagina());
      Page<TipoPagamento> tipoPagamentos =
          tipoPagamentoRepositorio.retornarListaTiposPagamentoPaginados(
              tipoPagamentoEntradaDTO.getDescricao(), paginacao);
      List<TipoPagamentoRetornoDTO> tipoPagamentoRetornoDTOS = new ArrayList<>();
      tipoPagamentos
          .getContent()
          .forEach(
              tipoPagamento ->
                  tipoPagamentoRetornoDTOS.add(
                      new TipoPagamentoBuilder()
                          .addTipoPagamento(tipoPagamento)
                          .buildRetornoTipoPagamento()));
      return new TipoPagamentoRetornoPaginadoDTO(
          tipoPagamentos.getNumberOfElements(),
          tipoPagamentos.getTotalPages(),
          tipoPagamentoRetornoDTOS);
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception ex) {
      log.error(MENSAGEM_ERRO, "retornar com filtro", ex);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public void excluir(String id) {
    try {
      TipoPagamento tipoPagamento = obterTipoPagamentoPorId(IdUtil.obterUUID(id));
      tipoPagamento.setDataExclusao(LocalDateTime.now());
      tipoPagamentoRepositorio.save(tipoPagamento);
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception ex) {
      log.error(MENSAGEM_ERRO, "excluir", ex);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  private void validaSeTipoPagamentoJaCadastrado(TipoPagamentoEntradaDTO tipoPagamentoEntradaDTO) {
    tipoPagamentoRepositorio
        .findByDescricaoAndDataExclusaoIsNull(tipoPagamentoEntradaDTO.getDescricao())
        .ifPresent(
            tipoPagamento -> {
              throw new BadRequestException(EValidacao.TIPO_PAGAMENTO_JA_CADASTRADO);
            });
  }

  private void adicionaCodigoTipoPagamento(TipoPagamento tipoPagamento) {
    int codigo = configuracaoServico.obterUltimoCodigo(ULTIMO_CODIGO) + 1;
    tipoPagamento.setCodigo(String.format("%06d", codigo));
  }

  private TipoPagamento obterTipoPagamentoPorId(UUID uuid) {
    return tipoPagamentoRepositorio
        .findByIdAndDataExclusaoIsNull(uuid)
        .orElseThrow(() -> new NotFoundException(EValidacao.TIPO_PAGAMENTO_NAO_ENCONTRADO));
  }
}
