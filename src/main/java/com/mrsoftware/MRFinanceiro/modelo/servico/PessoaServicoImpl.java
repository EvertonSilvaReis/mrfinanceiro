package com.mrsoftware.MRFinanceiro.modelo.servico;

import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaEntradaPaginadaDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaRetornoPaginadoDTO;
import com.mrsoftware.MRFinanceiro.exception.BadRequestException;
import com.mrsoftware.MRFinanceiro.exception.ExceptionAbstractImpl;
import com.mrsoftware.MRFinanceiro.exception.InternalServerErrorException;
import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import com.mrsoftware.MRFinanceiro.modelo.builder.PessoaBuilder;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Pessoa;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.repositorios.PessoaRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.ConfiguracaoServico;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.PessoaServico;
import com.mrsoftware.MRFinanceiro.util.IdUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PessoaServicoImpl implements PessoaServico {

  @Autowired private PessoaRepositorio pessoaRepositorio;
  @Autowired private ConfiguracaoServico configuracaoServico;

  private static final String MENSAGEM_ERRO = "Ocorreu um erro ao {} pessoa.";
  private static final String ULTIMO_CODIGO = "ultimo-codigo-pessoa";

  @Override
  public PessoaRetornoDTO cadastrar(PessoaEntradaDTO pessoaEntradaDTO) {
    try {
      validaSePessoaJaCadastrada(pessoaEntradaDTO);
      Pessoa pessoa =
          new PessoaBuilder().addPessoaEntrada(pessoaEntradaDTO).buildDtoEntradaParaPessoa();
      adicionaCodigoPessoa(pessoa);

      return new PessoaBuilder()
          .addPessoa(pessoaRepositorio.save(pessoa))
          .buildPessoaParaPessoaRetorno();
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception ex) {
      log.error(MENSAGEM_ERRO, "cadastrar", ex);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public PessoaRetornoDTO atualizar(String id, PessoaEntradaDTO pessoaEntradaDTO) {
    try {
      Pessoa pessoa =
          new PessoaBuilder()
              .addPessoa(obterPessoaPorId(IdUtil.obterUUID(id)))
              .addPessoaEntrada(pessoaEntradaDTO)
              .buildAtualizarPessoa();
      return new PessoaBuilder()
          .addPessoa(pessoaRepositorio.save(pessoa))
          .buildPessoaParaPessoaRetorno();
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception ex) {
      log.error(MENSAGEM_ERRO, "atualizar", ex);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public PessoaRetornoDTO retornarPessoaPorId(String id) {
    try {
      return new PessoaBuilder()
          .addPessoa(obterPessoaPorId(IdUtil.obterUUID(id)))
          .buildPessoaParaPessoaRetorno();
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception ex) {
      log.error(MENSAGEM_ERRO, "retornar", ex);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public PessoaRetornoPaginadoDTO retornarPorFiltro(
      PessoaEntradaPaginadaDTO pessoaEntradaPaginadaDTO) {
    try {
      Pageable paginacao =
          PageRequest.of(
              pessoaEntradaPaginadaDTO.getPagina() > 0
                  ? (pessoaEntradaPaginadaDTO.getPagina() - 1)
                  : 0,
              pessoaEntradaPaginadaDTO.getItensPorPagina());
      Page<Pessoa> pessoas =
          pessoaRepositorio.retornarListaPessoasPaginadas(
              pessoaEntradaPaginadaDTO.getNome(), pessoaEntradaPaginadaDTO.getCpfCnpj(), paginacao);
      log.info("Encontradas {} pessoas", pessoas.getTotalElements());
      List<PessoaRetornoDTO> pessoaRetornoDTOList = new ArrayList<>();
      pessoas
          .getContent()
          .forEach(
              pessoa ->
                  pessoaRetornoDTOList.add(
                      new PessoaBuilder().addPessoa(pessoa).buildPessoaParaPessoaRetorno()));
      return new PessoaRetornoPaginadoDTO(
          pessoas.getNumberOfElements(), pessoas.getTotalPages(), pessoaRetornoDTOList);
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception ex) {
      log.error(MENSAGEM_ERRO, "retornar", ex);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  @Override
  public void excluir(String id) {
    try {
      Pessoa pessoa = obterPessoaPorId(IdUtil.obterUUID(id));
      pessoa.setDataExclusao(LocalDateTime.now());
      pessoaRepositorio.save(pessoa);
    } catch (ExceptionAbstractImpl e) {
      throw e;
    } catch (Exception ex) {
      log.error(MENSAGEM_ERRO, "excluir", ex);
      throw new InternalServerErrorException(EValidacao.NAO_IDENTIFICADO);
    }
  }

  private void adicionaCodigoPessoa(Pessoa pessoa) {
    int codigo = configuracaoServico.obterUltimoCodigo(ULTIMO_CODIGO) + 1;
    pessoa.setCodigo(String.format("%06d", codigo));
  }

  private Pessoa obterPessoaPorId(UUID id) {
    return pessoaRepositorio
        .findById(id)
        .orElseThrow(() -> new NotFoundException(EValidacao.PESSOA_NAO_ENCONTRADA, id.toString()));
  }

  private void validaSePessoaJaCadastrada(PessoaEntradaDTO pessoaEntradaDTO) {
    Optional<Pessoa> pessoa =
        pessoaRepositorio.findByNomeOrCpfCnpj(
            pessoaEntradaDTO.getNome(), pessoaEntradaDTO.getCpfCnpj());

    if (pessoa.isPresent()) throw new BadRequestException(EValidacao.PESSOA_JA_CADASTRADA);
  }
}
