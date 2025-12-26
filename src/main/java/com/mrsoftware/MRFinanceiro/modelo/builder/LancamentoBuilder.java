package com.mrsoftware.MRFinanceiro.modelo.builder;

import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoBaixaDTO;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Conta;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Lancamento;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Pessoa;
import com.mrsoftware.MRFinanceiro.modelo.entidade.TipoPagamento;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.EStatusTitulo;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.ETipoLancamento;
import com.mrsoftware.MRFinanceiro.util.DataUtil;
import java.math.BigDecimal;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class LancamentoBuilder {
  private LancamentoEntradaDTO lancamentoEntradaDTO;
  private Lancamento lancamento;
  private TipoPagamento tipoPagamento;
  private Pessoa pessoa;
  private Conta conta;
  private LancamentoBaixaDTO lancamentoBaixaDTO;

  private static final String COMPLEMENTO_DESCRICAO = "Titulo complementar baixa parcial: ";
  private static final String COMPLEMENTO_OBSERVACAO =
      "Título emitido após baixa parcial do titulo: ";

  public LancamentoBuilder addLancamentoEntrada(LancamentoEntradaDTO lancamentoEntradaDTO) {
    this.lancamentoEntradaDTO = lancamentoEntradaDTO;
    return this;
  }

  public LancamentoBuilder addLancamento(Lancamento lancamento) {
    this.lancamento = lancamento;
    return this;
  }

  public LancamentoBuilder addTipoPagamento(TipoPagamento tipoPagamento) {
    this.tipoPagamento = tipoPagamento;
    return this;
  }

  public LancamentoBuilder addPessoa(Pessoa pessoa) {
    this.pessoa = pessoa;
    return this;
  }

  public LancamentoBuilder addConta(Conta conta) {
    this.conta = conta;
    return this;
  }

  public LancamentoBuilder addLancamentoBaixaDTO(LancamentoBaixaDTO lancamentoBaixaDTO) {
    this.lancamentoBaixaDTO = lancamentoBaixaDTO;
    return this;
  }

  public Lancamento buildDtoParaEntidade() {
    DataUtil.converterStringParaLocalDate(lancamentoEntradaDTO.getDataPagamento());
    return Lancamento.builder()
        .descricao(lancamentoEntradaDTO.getDescricao())
        .numeroDocumento(lancamentoEntradaDTO.getNumeroDocumento())
        .tipoLancamento(
            ETipoLancamento.obterETipoLancamento(lancamentoEntradaDTO.getTipoLancamento()))
        .valorTitulo(lancamentoEntradaDTO.getValorTitulo())
        .desconto(lancamentoEntradaDTO.getDesconto())
        .dataEmissao(DataUtil.converterStringParaLocalDate(lancamentoEntradaDTO.getDataEmissao()))
        .dataVencimento(
            DataUtil.converterStringParaLocalDate(lancamentoEntradaDTO.getDataVencimento()))
        .valorPagamento(
            Objects.nonNull(lancamentoEntradaDTO.getValorPagamento())
                ? lancamentoEntradaDTO.getValorPagamento()
                : null)
        .dataPagamento(
            DataUtil.converterStringParaLocalDate(lancamentoEntradaDTO.getDataPagamento()))
        .observacao(lancamentoEntradaDTO.getObservacao())
        .tipoPagamento(tipoPagamento)
        .pessoa(pessoa)
        .conta(conta)
        .situacao(EStatusTitulo.EM_ABERTO)
        .parcela(
            tipoPagamento
                .getParcelas()) // TODO deverá haver mais uma coluna para parcela atual, e deverá
        // ser criado futuramente um titulo para cada parcela
        .build();
  }

  public LancamentoRetornoDTO buildEntidadeParaDto() {
    return LancamentoRetornoDTO.builder()
        .id(lancamento.getId())
        .descricao(lancamento.getDescricao())
        .numeroDocumento(lancamento.getNumeroDocumento())
        .tipoLancamento(lancamento.getTipoLancamento().getDescricao())
        .valorTitulo(lancamento.getValorTitulo())
        .desconto(lancamento.getDesconto())
        .dataEmissao(lancamento.getDataEmissao())
        .dataVencimento(lancamento.getDataVencimento())
        .valorPagamento(lancamento.getValorPagamento())
        .dataPagamento(lancamento.getDataPagamento())
        .observacao(lancamento.getObservacao())
        .tipoPagamento(
            new TipoPagamentoBuilder()
                .addTipoPagamento(lancamento.getTipoPagamento())
                .buildRetornoTipoPagamento())
        .pessoa(
            new PessoaBuilder().addPessoa(lancamento.getPessoa()).buildPessoaParaPessoaRetorno())
        .conta(new ContaBuilder().addConta(lancamento.getConta()).buildEntidadeParaDto())
        .situacao(lancamento.getSituacao().getDescricao())
        .codigo(lancamento.getCodigo())
        .build();
  }

  public Lancamento buildAtualizacaoDtoParaEntidade() {
    return Lancamento.builder()
        .descricao(
            Objects.nonNull(lancamentoEntradaDTO.getDescricao())
                ? lancamentoEntradaDTO.getDescricao()
                : lancamento.getDescricao())
        .numeroDocumento(
            Objects.nonNull(lancamentoEntradaDTO.getNumeroDocumento())
                ? lancamentoEntradaDTO.getNumeroDocumento()
                : lancamento.getNumeroDocumento())
        .tipoLancamento(
            Objects.nonNull(lancamentoEntradaDTO.getTipoLancamento())
                ? ETipoLancamento.obterETipoLancamento(lancamentoEntradaDTO.getTipoLancamento())
                : lancamento.getTipoLancamento())
        .valorTitulo(
            Objects.nonNull(lancamentoEntradaDTO.getValorTitulo())
                ? lancamentoEntradaDTO.getValorTitulo()
                : lancamento.getValorTitulo())
        .desconto(
            Objects.nonNull(lancamentoEntradaDTO.getDesconto())
                ? lancamentoEntradaDTO.getDesconto()
                : lancamento.getDesconto())
        .dataEmissao(
            Objects.nonNull(lancamentoEntradaDTO.getDataEmissao())
                ? DataUtil.converterStringParaLocalDate(lancamentoEntradaDTO.getDataEmissao())
                : lancamento.getDataEmissao())
        .dataVencimento(
            Objects.nonNull(lancamentoEntradaDTO.getDataVencimento())
                ? DataUtil.converterStringParaLocalDate(lancamentoEntradaDTO.getDataVencimento())
                : lancamento.getDataVencimento())
        .observacao(
            Objects.nonNull(lancamentoEntradaDTO.getObservacao())
                ? lancamentoEntradaDTO.getObservacao()
                : lancamento.getObservacao())
        .tipoPagamento(tipoPagamento)
        .pessoa(pessoa)
        .conta(conta)
        .build();
  }

  public Lancamento buildBaixaLancamento(EStatusTitulo eStatusTitulo) {
    lancamento.setSituacao(eStatusTitulo);
    lancamento.setDataPagamento(
        DataUtil.converterStringParaLocalDate(lancamentoBaixaDTO.getDataPagamento()));
    lancamento.setValorPagamento(lancamentoBaixaDTO.getValorPagamento());
    return lancamento;
  }

  public Lancamento buildLancamentoParcial(BigDecimal valorRestanteParaPagamento) {
    return Lancamento.builder()
        .descricao(COMPLEMENTO_DESCRICAO + lancamento.getDescricao())
        .numeroDocumento(lancamento.getNumeroDocumento())
        .tipoLancamento(lancamento.getTipoLancamento())
        .valorTitulo(valorRestanteParaPagamento)
        .desconto(lancamento.getDesconto())
        .dataEmissao(lancamento.getDataEmissao())
        .dataVencimento(lancamento.getDataVencimento())
        .observacao(COMPLEMENTO_OBSERVACAO + lancamento.getCodigo())
        .tipoPagamento(lancamento.getTipoPagamento())
        .pessoa(lancamento.getPessoa())
        .conta(lancamento.getConta())
        .lancamentoOriginal(lancamento.getId())
        .build();
  }
}
