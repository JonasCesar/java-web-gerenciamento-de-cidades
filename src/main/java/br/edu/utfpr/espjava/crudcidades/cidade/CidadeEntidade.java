package br.edu.utfpr.espjava.crudcidades.cidade;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "cidade")
public class CidadeEntidade implements Serializable {
	
	private static final long serialVersionUID = 9196758125272340728L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String estado;
	
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getEstado() {
		return estado;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
