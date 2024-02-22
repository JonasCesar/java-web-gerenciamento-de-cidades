package br.edu.utfpr.espjava.crudcidades.cidade;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CidadeRepository extends JpaRepository<CidadeEntidade, Long> {
	
	Optional<CidadeEntidade> findByNomeAndEstado(String nome, String estado);
	
}
