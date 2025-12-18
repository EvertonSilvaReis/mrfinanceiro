package com.mrsoftware.MRFinanceiro.unitario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaEntradaPaginadaDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaRetornoDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaRetornoPaginadoDTO;
import com.mrsoftware.MRFinanceiro.exception.BadRequestException;
import com.mrsoftware.MRFinanceiro.exception.InternalServerErrorException;
import com.mrsoftware.MRFinanceiro.exception.NotFoundException;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Pessoa;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.ETipoPessoa;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.repositorios.PessoaRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.servico.PessoaServicoImpl;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.ConfiguracaoServico;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class PessoaServicoTest {
  @InjectMocks private PessoaServicoImpl pessoaServico;
  @Mock private PessoaRepositorio pessoaRepositorio;
  @Mock private ConfiguracaoServico configuracaoServico;

  @Test
  void cadastrar_DeveRetornarPessoaRetornoDTO_QuandoDadosValidos() {
    PessoaEntradaDTO entrada = criarPessoaEntradaDTO();
    Pessoa pessoaSalva = criarPessoa();

    when(pessoaRepositorio.findByNomeOrCpfCnpjAndDataExclusaoIsNull(anyString(), anyString()))
        .thenReturn(Optional.empty());
    when(configuracaoServico.obterUltimoCodigo("ultimo-codigo-pessoa")).thenReturn(0);
    when(pessoaRepositorio.save(any(Pessoa.class))).thenReturn(pessoaSalva);

    PessoaRetornoDTO resultado = pessoaServico.cadastrar(entrada);

    assertNotNull(resultado);
    assertEquals("João Silva", resultado.getNome());
    verify(pessoaRepositorio).save(any(Pessoa.class));
  }

  @Test
  void cadastrar_DeveLancarBadRequestException_QuandoPessoaJaCadastrada() {
    PessoaEntradaDTO entrada = criarPessoaEntradaDTO();

    when(pessoaRepositorio.findByNomeOrCpfCnpjAndDataExclusaoIsNull(anyString(), anyString()))
        .thenReturn(Optional.of(criarPessoa()));

    assertThrows(BadRequestException.class, () -> pessoaServico.cadastrar(entrada));
  }

  @Test
  void cadastrar_DeveLancarInternalServerErrorException_QuandoErroInesperado() {
    PessoaEntradaDTO entrada = criarPessoaEntradaDTO();

    when(pessoaRepositorio.findByNomeOrCpfCnpjAndDataExclusaoIsNull(anyString(), anyString()))
        .thenThrow(new RuntimeException());

    assertThrows(InternalServerErrorException.class, () -> pessoaServico.cadastrar(entrada));
  }

  @Test
  void DeveAtualizarQuandoDadosValidos() {
    String id = UUID.randomUUID().toString();
    PessoaEntradaDTO entrada = criarPessoaEntradaDTO();
    Pessoa pessoaExistente = criarPessoa();

    when(pessoaRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenReturn(Optional.of(pessoaExistente));
    when(pessoaRepositorio.save(any(Pessoa.class))).thenReturn(pessoaExistente);

    PessoaRetornoDTO resultado = pessoaServico.atualizar(id, entrada);

    assertNotNull(resultado);
    verify(pessoaRepositorio).save(any(Pessoa.class));
  }

  @Test
  void DeveLancarNotFoundExceptionQuandoPessoaNaoEncontrada() {
    String id = UUID.randomUUID().toString();
    PessoaEntradaDTO entrada = criarPessoaEntradaDTO();

    when(pessoaRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> pessoaServico.atualizar(id, entrada));
  }

  @Test
  void DeveRetornarPessoaPorId() {
    String id = UUID.randomUUID().toString();
    Pessoa pessoa = criarPessoa();

    when(pessoaRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenReturn(Optional.of(pessoa));

    PessoaRetornoDTO resultado = pessoaServico.retornarPessoaPorId(id);

    assertNotNull(resultado);
    assertEquals("João Silva", resultado.getNome());
  }

  @Test
  void DeveLancarNotFoundExceptionQuandoNaoEncontradaAoRetornarPorId() {
    String id = UUID.randomUUID().toString();

    when(pessoaRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> pessoaServico.retornarPessoaPorId(id));
  }

  @Test
  void DeveRetornarListaPaginada() {
    PessoaEntradaPaginadaDTO filtro = criarPessoaEntradaPaginadaDTO();
    Page<Pessoa> pagePessoas = criarPagePessoas();

    when(pessoaRepositorio.retornarListaPessoasPaginadas(
            anyString(), anyString(), any(Pageable.class)))
        .thenReturn(pagePessoas);

    PessoaRetornoPaginadoDTO resultado = pessoaServico.retornarPorFiltro(filtro);

    assertNotNull(resultado);
    assertEquals(1, resultado.getItensPorPagina());
    assertEquals(1, resultado.getPagina());
    assertFalse(resultado.getPessoas().isEmpty());
  }

  @Test
  void DeveLancarInternalServerErrorExceptionQuandoErroInesperadoAoRetornarPorFiltro() {
    PessoaEntradaPaginadaDTO filtro = criarPessoaEntradaPaginadaDTO();

    when(pessoaRepositorio.retornarListaPessoasPaginadas(
            anyString(), anyString(), any(Pageable.class)))
        .thenThrow(new RuntimeException());

    assertThrows(InternalServerErrorException.class, () -> pessoaServico.retornarPorFiltro(filtro));
  }

  @Test
  void DeveRealizarExclusaoLogicaDeUmaPessoa() {
    String id = UUID.randomUUID().toString();
    Pessoa pessoa = criarPessoa();

    when(pessoaRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenReturn(Optional.of(pessoa));
    when(pessoaRepositorio.save(any(Pessoa.class))).thenReturn(pessoa);

    assertDoesNotThrow(() -> pessoaServico.excluir(id));

    verify(pessoaRepositorio).save(argThat(p -> p.getDataExclusao() != null));
  }

  @Test
  void DeveLancarNotFoundExceptionAoNaoEncontrarPessoaAoExcluir() {
    String id = UUID.randomUUID().toString();

    when(pessoaRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> pessoaServico.excluir(id));
  }

  @Test
  void DeveLancarBadRequestQuandoPessoaJaCadastrada() {
    PessoaEntradaDTO entrada = criarPessoaEntradaDTO();

    when(pessoaRepositorio.findByNomeOrCpfCnpjAndDataExclusaoIsNull(anyString(), anyString()))
        .thenThrow(new BadRequestException(EValidacao.PESSOA_JA_CADASTRADA));

    assertThrows(BadRequestException.class, () -> pessoaServico.cadastrar(entrada));
  }

  @Test
  void DeveLancarNotFoundQuandoPessoaNaoEncontradaAoAtualizar() {
    String id = UUID.randomUUID().toString();
    PessoaEntradaDTO entrada = criarPessoaEntradaDTO();

    when(pessoaRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenThrow(new NotFoundException(EValidacao.PESSOA_NAO_ENCONTRADA, id));

    assertThrows(NotFoundException.class, () -> pessoaServico.atualizar(id, entrada));
  }

  @Test
  void DeveLancarBadRequestQuandoPessoaNaoEncontradaAoRetornarPorId() {
    String id = UUID.randomUUID().toString();

    when(pessoaRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenThrow(new NotFoundException(EValidacao.PESSOA_NAO_ENCONTRADA, id));

    assertThrows(NotFoundException.class, () -> pessoaServico.retornarPessoaPorId(id));
  }

  @Test
  void DeveLancarBadRequestAoValidarEntradaAoRetornarPorFiltro() {
    PessoaEntradaPaginadaDTO filtro = criarPessoaEntradaPaginadaDTO();

    when(pessoaRepositorio.retornarListaPessoasPaginadas(
            anyString(), anyString(), any(Pageable.class)))
        .thenThrow(new BadRequestException(EValidacao.ENTRADA_DADOS_INVALIDA));

    assertThrows(BadRequestException.class, () -> pessoaServico.retornarPorFiltro(filtro));
  }

  @Test
  void DeveLancarNotFoundQuandoPessoaNaoEncontradaAoExcluir() {
    String id = UUID.randomUUID().toString();

    when(pessoaRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenThrow(new NotFoundException(EValidacao.PESSOA_NAO_ENCONTRADA, id));

    assertThrows(NotFoundException.class, () -> pessoaServico.excluir(id));
  }

  private PessoaEntradaDTO criarPessoaEntradaDTO() {
    PessoaEntradaDTO dto = new PessoaEntradaDTO();
    dto.setNome("João Silva");
    dto.setCpfCnpj("12345678901");
    dto.setTipoPessoa(0);
    return dto;
  }

  private Pessoa criarPessoa() {
    return Pessoa.builder()
        .id(UUID.randomUUID())
        .nome("João Silva")
        .cpfCnpj("12345678901")
        .codigo("000001")
        .dataCadastro(LocalDate.now())
        .tipoPessoa(ETipoPessoa.FISICA)
        .build();
  }

  private PessoaEntradaPaginadaDTO criarPessoaEntradaPaginadaDTO() {
    PessoaEntradaPaginadaDTO dto = new PessoaEntradaPaginadaDTO();
    dto.setNome("João");
    dto.setCpfCnpj("123");
    dto.setPagina(1);
    dto.setItensPorPagina(10);
    return dto;
  }

  private Page<Pessoa> criarPagePessoas() {
    List<Pessoa> pessoas = Arrays.asList(criarPessoa());
    return new PageImpl<>(pessoas, PageRequest.of(0, 10), 1);
  }
}
