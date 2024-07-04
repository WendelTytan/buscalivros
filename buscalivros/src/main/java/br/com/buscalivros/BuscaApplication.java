package br.com.buscalivros;

import br.com.buscalivros.entrada.Entrada;
import br.com.buscalivros.repository.AuthorRepository;
import br.com.buscalivros.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuscaApplication implements CommandLineRunner {
	@Autowired
	private BookRepository livroRepositorio;
	@Autowired
	private AuthorRepository autorRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(BuscaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Entrada entrada = new Entrada(livroRepositorio, autorRepositorio);
			entrada.init();
		} catch (Exception e) {
            throw new Exception(e);
        }
	}
}
