package br.com.senior.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.senior.domain.model.Pedido;
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	Optional<Pedido> findByCodigo(String codigo);

}
