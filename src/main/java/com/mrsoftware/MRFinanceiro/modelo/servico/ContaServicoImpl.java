package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.dtos.conta.ContaEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.conta.ContaEntradaPaginadaDTO;
import com.mrsoftware.MRFinanceiro.dtos.conta.ContaRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.conta.ContaRetornoPaginadoDTO;
import com.mrsoftware.MRFinanceiro.exception.ExceptionAbstractImpl;
import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import com.mrsoftware.MRFinanceiro.modelo.builder.ContaBuilder;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Conta;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.repositorios.ContaRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.ContaServico;
import com.mrsoftware.MRFinanceiro.util.IdUtil;
import jakarta.transaction.Transactional;
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
public class ContaServicoImpl implements ContaServico {
  @Autowired private ContaRepositorio contaRepositorio;

  private static final String MENSAGEM_ERRO = "Ocorreu um erro ao {} conta.";

  @Transactional
  @Override
  public ContaRetornoDTO cadastrar(ContaEntradaDTO contaEntradaDTO) {
    try {
      validaSeContaJaCadastrada(contaEntradaDTO);
      Conta conta = new ContaBuilder().addContaEntradaDTO(contaEntradaDTO).buildDtoParaEntidade();
      return new ContaBuilder().addConta(contaRepositorio.save(conta)).buildEntidadeParaDto();
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "cadastrar", e);
      throw new ExceptionAbstractImpl(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public ContaRetornoDTO atualizar(String id, ContaEntradaDTO contaEntradaDTO) {
    try {
      Conta conta =
          new ContaBuilder()
              .addConta(obterContaPorId(IdUtil.obterUUID(id)))
              .addContaEntradaDTO(contaEntradaDTO)
              .buildAtualizarConta();
      return new ContaBuilder().addConta(contaRepositorio.save(conta)).buildEntidadeParaDto();
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "cadastrar", e);
      throw new ExceptionAbstractImpl(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public ContaRetornoPaginadoDTO retornarPorFiltro(
      ContaEntradaPaginadaDTO contaEntradaPaginadaDTO) {
    try {
      Pageable paginacao =
          PageRequest.of(
              contaEntradaPaginadaDTO.getPagina() > 0
                  ? (contaEntradaPaginadaDTO.getPagina() - 1)
                  : 0,
              contaEntradaPaginadaDTO.getItensPorPagina());
      Page<Conta> contas =
          contaRepositorio.retornarListaContasPaginadas(
              contaEntradaPaginadaDTO.getDescricao(),
              contaEntradaPaginadaDTO.getBanco(),
              paginacao);
      List<ContaRetornoDTO> contaRetornoDTOList = new ArrayList<>();
      contas
          .getContent()
          .forEach(
              conta ->
                  contaRetornoDTOList.add(
                      new ContaBuilder().addConta(conta).buildEntidadeParaDto()));
      return new ContaRetornoPaginadoDTO(
          contas.getNumberOfElements(), contas.getTotalPages(), contaRetornoDTOList);
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "cadastrar", e);
      throw new ExceptionAbstractImpl(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public ContaRetornoDTO retornarContaPorId(String id) {
    try {
      return new ContaBuilder()
          .addConta(obterContaPorId(IdUtil.obterUUID(id)))
          .buildEntidadeParaDto();
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "cadastrar", e);
      throw new ExceptionAbstractImpl(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public void excluir(String id) {
    try {
      Conta conta = obterContaPorId(IdUtil.obterUUID(id));
      conta.setDataExclusao(LocalDateTime.now());
      contaRepositorio.save(conta);
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception e) {
      log.error(MENSAGEM_ERRO, "cadastrar", e);
      throw new ExceptionAbstractImpl(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public Conta obterContaPorId(UUID id) {
    return contaRepositorio
        .findByIdAndDataExclusaoIsNull(id)
        .orElseThrow(() -> new NotFoundException(EValidacao.NAO_IDENTIFICADO));
  }

  private void validaSeContaJaCadastrada(ContaEntradaDTO contaEntradaDTO) {
    if (contaRepositorio.existsByAgenciaAndNumeroContaAndBanco(
        contaEntradaDTO.getAgencia(),
        contaEntradaDTO.getNumeroConta(),
        contaEntradaDTO.getBanco())) {
      throw new ExceptionAbstractImpl(
          EValidacao.CONTA_JA_CADASTRADA,
          contaEntradaDTO.getNumeroConta().toString(),
          contaEntradaDTO.getAgencia().toString(),
          contaEntradaDTO.getBanco().toString());
    }
  }
}
