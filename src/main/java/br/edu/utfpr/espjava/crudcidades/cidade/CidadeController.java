package br.edu.utfpr.espjava.crudcidades.cidade;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CidadeController {
	
	private final CidadeRepository repository;
	
	public CidadeController(CidadeRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/")
	public String listar(Model memoria, Principal usuario, HttpSession sessao, HttpServletResponse response) {
		
		response.addCookie(new Cookie("listar", LocalDateTime.now().toString()));
		
		memoria.addAttribute("listaCidades", converteCidade(repository.findAll()));
		
		sessao.setAttribute("usuarioAtual", usuario.getName());
		    
		return "/crud";
	}
	
	@PostMapping("/criar")
	public String criar(@Valid Cidade cidade, BindingResult validacao, Model memoria, HttpServletResponse response){
		
		response.addCookie(new Cookie("criar", LocalDateTime.now().toString()));
		
		if(validacao.hasErrors()) {
			
			validacao.getFieldErrors()
			.forEach(
					error -> memoria.addAttribute(error.getField(), error.getDefaultMessage())
			);
			
			memoria.addAttribute("nomeInformado", cidade.getNome());
			memoria.addAttribute("estadoInformado", cidade.getEstado());
			memoria.addAttribute("listaCidades", converteCidade(repository.findAll()));
			
			return "/crud";
					
		} else {
					
			repository.save(cidade.convertParaCidadeEntidade());
		}
				
		return "redirect:/";
	}
	
	@GetMapping("/excluir")
	public String excluir(@RequestParam String nome, @RequestParam String estado, HttpServletResponse response) {
		
		response.addCookie(new Cookie("excluir", LocalDateTime.now().toString()));
		
		Optional<CidadeEntidade> cidadeEstadoEncontrada = repository.findByNomeAndEstado(nome, estado);
		
		cidadeEstadoEncontrada.ifPresent(repository::delete);
		
		return "redirect:/";
		
	}
	
	@GetMapping("/preparaAlterar")
	public String preparaAlterar(@RequestParam String nome, @RequestParam String estado, Model memoria){
		
		Optional<CidadeEntidade> cidadeAtual = repository.findByNomeAndEstado(nome, estado);
		
		cidadeAtual.ifPresent(cidadeEncontrada -> {
			memoria.addAttribute("cidadeAtual", cidadeEncontrada);
			memoria.addAttribute("listaCidades", converteCidade(repository.findAll()));
		});
		
		return "/crud";
		
	}
	
	@PostMapping("/alterar")
	public String alterar(@RequestParam String nomeAtual, @RequestParam String estadoAtual, Cidade cidade, BindingResult validacao, Model memoria, HttpServletResponse response) {
		
		response.addCookie(new Cookie("alterar", LocalDateTime.now().toString()));
		
		Optional<CidadeEntidade> cidadeAtual = repository.findByNomeAndEstado(nomeAtual, estadoAtual);
		
		if(cidadeAtual.isPresent()) {
			CidadeEntidade cidadeEncontrada = cidadeAtual.get();
			cidadeEncontrada.setNome(cidade.getNome());
			cidadeEncontrada.setEstado(cidade.getEstado());
			
			repository.saveAndFlush(cidadeEncontrada);
		}
		
		return "redirect:/";
	}
	
	private List<Cidade> converteCidade(List<CidadeEntidade> cidadesEntidade){
//		return cidadesEntidade.stream()
//				.map(cidade -> new Cidade(cidade.getNome(), cidade.getEstado()))
//				.collect(Collectors.toList());
		List<Cidade> cidades = new ArrayList<>();
		cidadesEntidade.forEach(cidade -> {
			cidades.add(new Cidade(cidade.getNome(), cidade.getEstado()));
		});
		return cidades;
	}
	
	@GetMapping("/mostrar")
	@ResponseBody
	public String mostraCookie(@CookieValue String listar) {
		return "Ultimo acesso ao método listar(): " + listar; 
	}
	

}
