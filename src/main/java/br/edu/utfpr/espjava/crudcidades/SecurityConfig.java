package br.edu.utfpr.espjava.crudcidades;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	// criar os usuários programaticamente
	@Bean
	public InMemoryUserDetailsManager configure() throws Exception {
		UserDetails john = User.withUsername("john").password(cifrador().encode("test123")).roles("listar").build();

		UserDetails anna = User.withUsername("anna").password(cifrador().encode("test123")).roles("admin").build();

		return new InMemoryUserDetailsManager(john, anna);
	}

	// configurando autorização
	@Bean
	public SecurityFilterChain filter(HttpSecurity http) throws Exception {
		
		return http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/").hasAnyRole("listar", "admin")
				.requestMatchers("/criar", "/excluir", "/alterar", "/preparaAlterar").hasRole("admin")
				.anyRequest().denyAll())
				.formLogin(login -> login
					.loginPage("/login.html").permitAll())	
				.logout(logout -> logout.permitAll())
				.build();
		
	}

	// código para cifrar as senhas
	@Bean
	public PasswordEncoder cifrador() {
		return new BCryptPasswordEncoder();
	}

}
