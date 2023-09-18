package br.senac.df.springbootvaadin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.senac.df.springbootvaadin.dao.PessoaRepositorio;
import br.senac.df.springbootvaadin.model.Pessoa;

@SpringBootApplication
public class SpringBootVaadinApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootVaadinApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner loadData(PessoaRepositorio repository) {
        return (args) -> {
            // Add some data to database
            repository.save(new Pessoa(1,"Rogerio", "Siqueira", "rogerio.siqueira@email.com"));
            repository.save(new Pessoa(2,"Dara", "Abreu", "dara.abreu@email.com"));
            repository.save(new Pessoa(3,"Maria", "Siqueira", "maria.siqueira@email.com"));
            repository.save(new Pessoa(4,"Marcos", "Vidal", "marcos.vidal@email.com"));
        };
    }


}
