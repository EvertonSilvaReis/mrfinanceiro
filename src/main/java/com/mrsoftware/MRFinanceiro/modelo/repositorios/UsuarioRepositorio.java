package com.mrsoftware.MRFinanceiro.modelo.repositorios;

import com.mrsoftware.MRFinanceiro.modelo.entidade.Usuario;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {

  @Query("SELECT u FROM Usuario u JOIN FETCH u.usuarioPerfis WHERE u.email = :email")
  Optional<Usuario> findByEmailWithPerfis(String email);
}
