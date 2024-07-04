package br.com.buscalivros.controller;

import br.com.buscalivros.model.Book;
import br.com.buscalivros.model.Language;
import br.com.buscalivros.repository.BookRepository;
import br.com.buscalivros.utils.Menu;
import br.com.buscalivros.utils.ScreenClear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Scanner;

public class Arquivo {
    private final Scanner scanner = new Scanner(System.in);
    private final BookRepository livroRepository;
    private Page<Book> pagina;
    private String arquivoEntrada = "";
    private static Integer numeroPagina = 1;
    private static String lingua = "all";

    public Arquivo(BookRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void load() {
        List<Book> livros = livroRepository.findAll();

        while (!arquivoEntrada.equals("0")) {
            Pageable pageable = PageRequest.of(numeroPagina - 1, 10);

            switch (lingua) {
                case "all":
                    pagina = livroRepository.findAll(pageable);
                    break;
                case "en":
                    pagina = livroRepository.findBooksByLanguage(Language.ENGLISH, pageable);
                    break;
                case "pt":
                    pagina = livroRepository.findBooksByLanguage(Language.PORTUGUESE, pageable);
                    break;
            }

            pagina.forEach(Book::printBook);
            Menu.archiveMenu();
            Menu.archiveMenuInfo(numeroPagina, pagina, livros);
            Menu.askOption();
            arquivoEntrada = scanner.nextLine();
            ScreenClear.clear();
            ArquivoOpcoes arquivoOpcoes = new ArquivoOpcoes(arquivoEntrada, pagina, numeroPagina);
            arquivoOpcoes.checagem();
        }

        arquivoEntrada = "";
    }

    public static String getLingua() { return lingua; }

    public static void setLingua(String lingua) { Arquivo.lingua = lingua; }

    public static void setNumeroPagina(Integer numeroPagina) {
        Arquivo.numeroPagina = numeroPagina;
    }
}
