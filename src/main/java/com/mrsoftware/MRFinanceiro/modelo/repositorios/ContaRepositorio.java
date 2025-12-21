package com.mrsoftware.MRFinanceiro.modelo.repositorios;

import com.mrsoftware.MRFinanceiro.modelo.entidade.Conta;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepositorio extends JpaRepository<Conta, UUID> {

  Optional<Conta> findByIdAndDataExclusaoIsFalse(UUID id);

  boolean existsByAgenciaAndNumeroContaAndBanco(
      Integer agencia, Integer numeroConta, Integer banco);

  @Query(
      value =
          """
                              select * from conta c
                              where ( cast(?1 as text) is null or lower(c.descricao) like concat('%',lower(?1),'%')) and
                              ( cast(?2 as text) is null or lower(c.banco) like concat('%',lower(?2),'%')) and
                              p.data_exclusao is null
                              """,
      nativeQuery = true)
  Page<Conta> retornarListaContasPaginadas(String descricao, String banco, Pageable paginacao);
}
