package com.mrsoftware.MRFinanceiro.modelo.repositorios;

import com.mrsoftware.MRFinanceiro.modelo.entidade.TipoPagamento;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPagamentoRepositorio extends JpaRepository<TipoPagamento, UUID> {
  Optional<TipoPagamento> findByDescricaoAndDataExclusaoIsNull(String descricao);

  @Query(
      value =
          """
              select * from tipoPagamento t
              where ( cast(?1 as text) is null or lower(t.nome) like concat('%', lower(?1),'%')) and
              t.data_exclusao is null
              """,
      nativeQuery = true)
  Page<TipoPagamento> retornarListaTiposPagamentoPaginados(String descricao, Pageable paginacao);

  Optional<TipoPagamento> findByIdAndDataExclusaoIsNull(UUID uuid);
}
