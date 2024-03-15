package br.edu.utfpr.espjava.crudcidades.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findByNome(String nome);

}
