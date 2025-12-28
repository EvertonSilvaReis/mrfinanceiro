package com.mrsoftware.MRFinanceiro.unitario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.mrsoftware.MRFinanceiro.dtos.tipoPagamento.*;
import com.mrsoftware.MRFinanceiro.exception.*;
import com.mrsoftware.MRFinanceiro.modelo.entidade.TipoPagamento;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EValidacao;
import com.mrsoftware.MRFinanceiro.modelo.repositorios.TipoPagamentoRepositorio;
import com.mrsoftware.MRFinanceiro.modelo.servico.TipoPagamentoServicoImpl;
import com.mrsoftware.MRFinanceiro.modelo.servico.interfaces.ConfiguracaoServico;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

@ExtendWith(MockitoExtension.class)
public class TipoPagamentoServicoTest {
  @InjectMocks private TipoPagamentoServicoImpl tipoPagamentoServico;
  @Mock private TipoPagamentoRepositorio tipoPagamentoRepositorio;
  @Mock private ConfiguracaoServico configuracaoServico;

  @Test
  void DeveCadastrarTipoPagamento() {
    TipoPagamentoEntradaDTO entrada = criarTipoPagamentoEntradaDTO();
    TipoPagamento tipoPagamentoSalvo = criarTipoPagamento();

    when(tipoPagamentoRepositorio.findByDescricaoAndDataExclusaoIsNull(anyString()))
        .thenReturn(Optional.empty());
    when(configuracaoServico.obterCodigo("ultimo-codigo-tipo-pagamento")).thenReturn(0);
    when(tipoPagamentoRepositorio.save(any(TipoPagamento.class))).thenReturn(tipoPagamentoSalvo);

    TipoPagamentoRetornoDTO resultado = tipoPagamentoServico.cadastrar(entrada);

    assertNotNull(resultado);
    assertEquals("Cartão de Crédito", resultado.getDescricao());
    verify(tipoPagamentoRepositorio).save(any(TipoPagamento.class));
  }

  @Test
  void DeveGerarBadReqeustQuandoTipoPagamentoJaExistitr() {
    TipoPagamentoEntradaDTO entrada = criarTipoPagamentoEntradaDTO();

    when(tipoPagamentoRepositorio.findByDescricaoAndDataExclusaoIsNull(anyString()))
        .thenReturn(Optional.of(criarTipoPagamento()));

    assertThrows(BadRequestException.class, () -> tipoPagamentoServico.cadastrar(entrada));
  }

  @Test
  void DeveLancarInternalServerErrorExceptionQuandoErroNaoEsperado() {
    TipoPagamentoEntradaDTO entrada = criarTipoPagamentoEntradaDTO();

    when(tipoPagamentoRepositorio.findByDescricaoAndDataExclusaoIsNull(anyString()))
        .thenThrow(new RuntimeException());

    assertThrows(InternalServerErrorException.class, () -> tipoPagamentoServico.cadastrar(entrada));
  }

  @Test
  void DeveAtualizarTipoPagamento() {
    String id = UUID.randomUUID().toString();
    TipoPagamentoEntradaDTO entrada = criarTipoPagamentoEntradaDTO();
    TipoPagamento tipoPagamentoExistente = criarTipoPagamento();

    when(tipoPagamentoRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenReturn(Optional.of(tipoPagamentoExistente));
    when(tipoPagamentoRepositorio.save(any(TipoPagamento.class)))
        .thenReturn(tipoPagamentoExistente);

    TipoPagamentoRetornoDTO resultado = tipoPagamentoServico.atualizar(id, entrada);

    assertNotNull(resultado);
    verify(tipoPagamentoRepositorio).save(any(TipoPagamento.class));
  }

  @Test
  void DeveLancarNotFoundQuandoTipoPagamentoNaoEncontrado() {
    String id = UUID.randomUUID().toString();
    TipoPagamentoEntradaDTO entrada = criarTipoPagamentoEntradaDTO();

    when(tipoPagamentoRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> tipoPagamentoServico.atualizar(id, entrada));
  }

  @Test
  void DeveRetornarTipoPagamentoPorId() {
    String id = UUID.randomUUID().toString();
    TipoPagamento tipoPagamento = criarTipoPagamento();

    when(tipoPagamentoRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenReturn(Optional.of(tipoPagamento));

    TipoPagamentoRetornoDTO resultado = tipoPagamentoServico.retornarTipoPagamentoPorId(id);

    assertNotNull(resultado);
    assertEquals("Cartão de Crédito", resultado.getDescricao());
  }

  @Test
  void DeveLancarNotFoundExceptionQuandoTipoPagamentoNaoEncontradoAoRetornarPorId() {
    String id = UUID.randomUUID().toString();

    when(tipoPagamentoRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenReturn(Optional.empty());

    assertThrows(
        NotFoundException.class, () -> tipoPagamentoServico.retornarTipoPagamentoPorId(id));
  }

  @Test
  void DeveRetornarTipoPagamentoComFiltro() {
    TipoPagamentoEntradaPaginadaDTO filtro = criarTipoPagamentoEntradaPaginadaDTO();
    Page<TipoPagamento> pageTipoPagamentos = criarPageTipoPagamentos();

    when(tipoPagamentoRepositorio.retornarListaTiposPagamentoPaginados(
            anyString(), any(Pageable.class)))
        .thenReturn(pageTipoPagamentos);

    TipoPagamentoRetornoPaginadoDTO resultado = tipoPagamentoServico.retornarPorFiltro(filtro);

    assertNotNull(resultado);
    assertEquals(1, resultado.getItensPorPagina());
    assertEquals(1, resultado.getPagina());
    assertFalse(resultado.getTipoPagamento().isEmpty());
  }

  @Test
  void DeveLancarInternalErrorServerAoRetornarPorFiltro() {
    TipoPagamentoEntradaPaginadaDTO filtro = criarTipoPagamentoEntradaPaginadaDTO();

    when(tipoPagamentoRepositorio.retornarListaTiposPagamentoPaginados(
            anyString(), any(Pageable.class)))
        .thenThrow(new RuntimeException());

    assertThrows(
        InternalServerErrorException.class, () -> tipoPagamentoServico.retornarPorFiltro(filtro));
  }

  @Test
  void DeveLancarBadRequestAoValidarPaginacaoNoRetornoPorFiltro() {
    TipoPagamentoEntradaPaginadaDTO filtro = criarTipoPagamentoEntradaPaginadaDTO();

    when(tipoPagamentoRepositorio.retornarListaTiposPagamentoPaginados(
            anyString(), any(Pageable.class)))
        .thenThrow(new BadRequestException(EValidacao.ENTRADA_DADOS_INVALIDA));

    assertThrows(BadRequestException.class, () -> tipoPagamentoServico.retornarPorFiltro(filtro));
  }

  @Test
  void DeveRealizarExclusaoLogicaDeTipoPagamento() {
    String id = UUID.randomUUID().toString();
    TipoPagamento tipoPagamento = criarTipoPagamento();

    when(tipoPagamentoRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenReturn(Optional.of(tipoPagamento));
    when(tipoPagamentoRepositorio.save(any(TipoPagamento.class))).thenReturn(tipoPagamento);

    assertDoesNotThrow(() -> tipoPagamentoServico.excluir(id));

    verify(tipoPagamentoRepositorio).save(argThat(tp -> tp.getDataExclusao() != null));
  }

  @Test
  void DeveLancarNotFoundAoNaoLocalizarTipoPagamentoParaExcluir() {
    String id = UUID.randomUUID().toString();

    when(tipoPagamentoRepositorio.findByIdAndDataExclusaoIsNull(any(UUID.class)))
        .thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> tipoPagamentoServico.excluir(id));
  }

  private TipoPagamentoEntradaDTO criarTipoPagamentoEntradaDTO() {
    TipoPagamentoEntradaDTO dto = new TipoPagamentoEntradaDTO();
    dto.setDescricao("Cartão de Crédito");
    dto.setParcelado(true);
    dto.setParcelas(12);
    return dto;
  }

  private TipoPagamento criarTipoPagamento() {
    return TipoPagamento.builder()
        .id(UUID.randomUUID())
        .descricao("Cartão de Crédito")
        .codigo("000001")
        .parcelado(true)
        .parcelas(12)
        .build();
  }

  private TipoPagamentoEntradaPaginadaDTO criarTipoPagamentoEntradaPaginadaDTO() {
    TipoPagamentoEntradaPaginadaDTO dto = new TipoPagamentoEntradaPaginadaDTO();
    dto.setDescricao("Cartão");
    dto.setPagina(1);
    dto.setItensPorPagina(10);
    return dto;
  }

  private Page<TipoPagamento> criarPageTipoPagamentos() {
    List<TipoPagamento> tipoPagamentos = Arrays.asList(criarTipoPagamento());
    return new PageImpl<>(tipoPagamentos, PageRequest.of(0, 10), 1);
  }
}
