package br.edu.utfpr.espjava.crudcidades.visao;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CidadeController {
	
	@GetMapping("/")
	public String listar(Model memoria) {
		Set<Cidade> cidades = Set.of(
		        new Cidade("Vitória da Conquista", "BA"),
		        new Cidade("Itacaré", "BA"),
		        new Cidade("Arujá", "SP")
		    );
		    
		    memoria.addAttribute("listaCidades", cidades);
		    
		    return "/crud";
	}

}
