package br.com.buscalivros.controller;

import br.com.buscalivros.model.Author;
import br.com.buscalivros.repository.AuthorRepository;
import br.com.buscalivros.utils.Menu;
import br.com.buscalivros.utils.ScreenClear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Scanner;

public class AutorArquivo {
    private final Scanner scanner = new Scanner(System.in);
    private final AuthorRepository autorRepositorio;
    private static String autorEntrada = "";
    private static Integer numeroPagina = 1;

    public AutorArquivo(AuthorRepository autorRepositorio) {
        this.autorRepositorio = autorRepositorio;
    }

    public void load () {
        while (!autorEntrada.equals("0")) {
            Pageable pageable = PageRequest.of(numeroPagina - 1, 10);
            Page<Author> pagina = autorRepositorio.findAll(pageable);
            pagina.forEach(Author::printAuthor);
            Menu.autorMenu();
            Menu.autorMenuInfo(numeroPagina, pagina);
            Menu.askOption();
            autorEntrada = scanner.nextLine();
            ScreenClear.clear();
            AutorOpcoesArquivo autorOpcoesArquivo = new AutorOpcoesArquivo(autorRepositorio, autorEntrada, pagina, numeroPagina);
            autorOpcoesArquivo.checagem();
        }

        autorEntrada = "";
    }

    public static void setNumeroPagina(Integer numeroPagina) {
        AutorArquivo.numeroPagina = numeroPagina;
    }

    public static void setAutorEntrada(String autorEntrada) {
        AutorArquivo.autorEntrada = autorEntrada;
    }
}
