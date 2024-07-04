package br.com.buscalivros.controller;

import br.com.buscalivros.entrada.Entrada;
import br.com.buscalivros.model.Book;
import br.com.buscalivros.model.DataIndex;
import br.com.buscalivros.repository.BookRepository;
import br.com.buscalivros.utils.Menu;
import br.com.buscalivros.utils.ScreenClear;

import java.util.List;
import java.util.Scanner;

public class Catalogo {
    private final Scanner scanner = new Scanner(System.in);
    private final DataIndex indexDados;
    private final BookRepository repositorioLivros;
    private Integer numeroPaginaApi;
    private String paginaApi;

    public Catalogo(DataIndex indexDados, BookRepository repositorioLivros) {
        this.indexDados = indexDados;
        this.repositorioLivros = repositorioLivros;
        this.numeroPaginaApi = Entrada.getNumeroPaginaApi();
        this.paginaApi = Entrada.getPaginaApi();
    }

    public void load() {
        if (this.indexDados.invalidPage() == null) {
            List<Book> livros = this.indexDados.books().stream().map(Book::new).toList();
            livros.forEach(Book::printBook);
            Menu.pageAndLang();
            Menu.catalogueMenu();
            String userOption = scanner.nextLine();
            ScreenClear.clear();
            CatalogoOpcoes catalogoOpcoes = new CatalogoOpcoes(userOption, livros,
                    repositorioLivros);
            catalogoOpcoes.checagem();
        } else {
            ScreenClear.clear();
            Menu.pageNotFound();
            Menu.backToCatalogue();
            numeroPaginaApi = 1;
            paginaApi = String.valueOf(numeroPaginaApi);
            Entrada.setNumeroPaginaApi(numeroPaginaApi);
            Entrada.setPaginaApi(paginaApi);
        }
    }
}
