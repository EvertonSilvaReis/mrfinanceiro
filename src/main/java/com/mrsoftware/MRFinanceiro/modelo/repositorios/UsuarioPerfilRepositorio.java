package com.mrsoftware.MRFinanceiro.modelo.repositorios;

import com.mrsoftware.MRFinanceiro.modelo.entidade.UsuarioPerfil;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioPerfilRepositorio extends JpaRepository<UsuarioPerfil, UUID> {}
