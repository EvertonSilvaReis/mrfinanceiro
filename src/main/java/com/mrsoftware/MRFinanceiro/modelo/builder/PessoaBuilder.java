package com.mrsoftware.MRFinanceiro.modelo.builder;

import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaEntradaDTO;
import com.mrsoftware.MRFinanceiro.dtos.pessoa.PessoaRetornoDTO;
import com.mrsoftware.MRFinanceiro.modelo.entidade.Pessoa;
import com.mrsoftware.MRFinanceiro.modelo.enumeradores.ETipoPessoa;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class PessoaBuilder {
  private PessoaEntradaDTO pessoaEntradaDTO;
  private Pessoa pessoa;
  private List<Pessoa> pessoas;

  public PessoaBuilder addPessoaEntrada(PessoaEntradaDTO pessoaEntradaDTO) {
    this.pessoaEntradaDTO = pessoaEntradaDTO;
    return this;
  }

  public PessoaBuilder addPessoa(Pessoa pessoa) {
    this.pessoa = pessoa;
    return this;
  }

  public PessoaBuilder addListaPessoas(List<Pessoa> pessoas) {
    this.pessoas = pessoas;
    return this;
  }

  public Pessoa buildDtoEntradaParaPessoa() {
    return Pessoa.builder()
        .nome(pessoaEntradaDTO.getNome())
        .tipoPessoa(ETipoPessoa.obterTipoPessoa(pessoaEntradaDTO.getTipoPessoa()))
        .dataCadastro(LocalDate.now())
        .cpfCnpj(pessoaEntradaDTO.getCpfCnpj())
        .build();
  }

  public PessoaRetornoDTO buildPessoaParaPessoaRetorno() {
    return PessoaRetornoDTO.builder()
        .codigo(pessoa.getCodigo())
        .nome(pessoa.getNome())
        .cpfCnpj(pessoa.getCpfCnpj())
        .dataCadastro(pessoa.getDataCadastro())
        .tipoPessoa(pessoa.getTipoPessoa().getCodigo())
        .build();
  }

  public Pessoa buildAtualizarPessoa() {
    return Pessoa.builder()
        .id(pessoa.getId())
        .nome(
            Objects.nonNull(pessoaEntradaDTO.getNome())
                ? pessoaEntradaDTO.getNome()
                : pessoa.getNome())
        .cpfCnpj(
            Objects.nonNull(pessoaEntradaDTO.getCpfCnpj())
                ? pessoaEntradaDTO.getCpfCnpj()
                : pessoa.getCpfCnpj())
        .tipoPessoa(
            Objects.nonNull(pessoaEntradaDTO.getTipoPessoa())
                ? ETipoPessoa.obterTipoPessoa(pessoaEntradaDTO.getTipoPessoa())
                : pessoa.getTipoPessoa())
        .dataCadastro(pessoa.getDataCadastro())
        .codigo(pessoa.getCodigo())
        .build();
  }

  //falta fazer teste unitário
    public List<PessoaRetornoDTO> buildRetornoListaPessoas() {
      List<PessoaRetornoDTO> pessoaRetornoDTOS = new ArrayList<>();
      this.pessoas.forEach(
              p -> {
                  PessoaRetornoDTO pessoaRetornoDTO =
                          PessoaRetornoDTO.builder()
                                  .codigo(p.getCodigo())
                                  .nome(p.getNome())
                                  .cpfCnpj(p.getCpfCnpj())
                                  .dataCadastro(p.getDataCadastro())
                                  .tipoPessoa(p.getTipoPessoa().getCodigo())
                                  .build();
                  pessoaRetornoDTOS.add(pessoaRetornoDTO);
              }
      );

      return pessoaRetornoDTOS;
    }
}
