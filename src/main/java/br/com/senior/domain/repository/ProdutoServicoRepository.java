package br.com.senior.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senior.domain.model.ProdutoServico;

@Repository
public interface ProdutoServicoRepository extends JpaRepository<ProdutoServico, Long> {

	Optional<ProdutoServico> findByCodigo(String codigo);

	void deleteByCodigo(String codigo);
	
}
