package com.mrsoftware.MRFinanceiro.modelo.builder;

import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.lancamentos.LancamentoRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Conta;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Lancamento;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Pessoa;
import com.mrsoftware.MRFinanceiro.modelo.entidade.TipoPagamento;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.ETipoLancamento;
import com.mrsoftware.MRFinanceiro.util.DataUtil;
import org.springframework.stereotype.Component;

@Component
public class LancamentoBuilder {
  private LancamentoEntradaDTO lancamentoEntradaDTO;
  private Lancamento lancamento;
  private TipoPagamento tipoPagamento;
  private Pessoa pessoa;
  private Conta conta;

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

  public Lancamento buildDtoParaEntidade() {
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
        .valorPagamento(lancamentoEntradaDTO.getValorPagamento())
        .dataPagamento(
            DataUtil.converterStringParaLocalDate(lancamentoEntradaDTO.getDataPagamento()))
        .observacao(lancamentoEntradaDTO.getObservacao())
        .tipoPagamento(tipoPagamento)
        .pessoa(pessoa)
        .conta(conta)
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
        .conta(null)
        .build();
  }
}
