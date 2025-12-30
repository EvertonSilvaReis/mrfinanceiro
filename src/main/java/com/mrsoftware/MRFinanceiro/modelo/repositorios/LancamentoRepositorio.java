package com.mrsoftware.MRFinanceiro.modelo.repositorios;

import com.mrsoftware.MRFinanceiro.modelo.entidade.Lancamento;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepositorio extends JpaRepository<Lancamento, UUID> {
  Optional<Lancamento> findByIdAndDataExclusaoIsNull(UUID uuid);

  Optional<Lancamento> findByNumeroDocumentoAndDataExclusaoIsNull(String numeroDocumento);
}
