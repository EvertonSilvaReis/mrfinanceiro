package com.mrsoftware.MRFinanceiro.modelo.repositorios;

import com.mrsoftware.MRFinanceiro.modelo.entidade.Pessoa;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepositorio extends JpaRepository<Pessoa, UUID> {
  Optional<Pessoa> findByNomeOrCpfCnpjAndDataExclusaoIsNull(String nome, String cpfCnpj);

  @Query(
      value =
          """
                    select * from pessoa p
                    where ( cast(?1 as text) is null or lower(p.nome) like concat('%',lower(?1),'%')) and
                    ( cast(?2 as text) is null or lower(p.cpf_cnpj) like concat('%',lower(?2),'%')) and
                    p.data_exclusao is null
                    """,
      nativeQuery = true)
  Page<Pessoa> retornarListaPessoasPaginadas(String nome, String cpfCnpj, Pageable paginacao);

  Optional<Pessoa> findByIdAndDataExclusaoIsNull(UUID id);
}
