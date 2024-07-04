package br.com.buscalivros.controller;

import br.com.buscalivros.entrada.Entrada;
import br.com.buscalivros.model.Book;
import br.com.buscalivros.model.DataIndex;
import br.com.buscalivros.repository.BookRepository;
import br.com.buscalivros.utils.Menu;
import br.com.buscalivros.utils.ScreenClear;

import java.util.List;
import java.util.Scanner;

public class Busca {
    private final Scanner scanner = new Scanner(System.in);
    private final DataIndex indexDados;
    private final BookRepository livroRepositorio;

    public Busca(BookRepository livroRepositorio) {
        this.indexDados = Entrada.getIndexDados();
        this.livroRepositorio = livroRepositorio;
    }

    public void load() {
        if (!indexDados.books().isEmpty()) {
            ScreenClear.clear();
            List<Book> livros = indexDados.books().stream().map(Book::new).toList();
            livros.forEach(Book::printBook);
            Menu.searchMenu();
            String opcoesMenu = scanner.nextLine();
            BuscaOpcoes buscaOpcoes = new BuscaOpcoes(opcoesMenu, livroRepositorio, livros);
            buscaOpcoes.checagem();
        } else {
            ScreenClear.clear();
            Menu.notFound();
        }
    }
}
