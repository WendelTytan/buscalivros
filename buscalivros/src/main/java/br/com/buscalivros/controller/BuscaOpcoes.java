package br.com.buscalivros.controller;

import br.com.buscalivros.entrada.Entrada;
import br.com.buscalivros.model.Book;
import br.com.buscalivros.repository.BookRepository;
import br.com.buscalivros.service.ApiService;
import br.com.buscalivros.service.Mapper;
import br.com.buscalivros.utils.CheckNullResponseBody;
import br.com.buscalivros.utils.Menu;
import br.com.buscalivros.utils.ScreenClear;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Scanner;

public class BuscaOpcoes {
    private final Scanner scanner = new Scanner(System.in);
    private final ApiService apiService = new ApiService();
    private final Mapper mapper = new Mapper();
    private final String opcoesMenu;
    private final BookRepository livroRepositorio;
    private final List<Book> livros;

    public BuscaOpcoes(String opcoesMenu, BookRepository livroRepositorio, List<Book> livros) {
        this.opcoesMenu = opcoesMenu;
        this.livroRepositorio = livroRepositorio;
        this.livros = livros;
    }

    public void checagem() {
        switch (opcoesMenu) {
            case "1":
                ScreenClear.clear();
                Menu.saving();

                try {
                    livroRepositorio.saveAll(livros);
                    Menu.saved();
                } catch (DataIntegrityViolationException e) {
                    Menu.alreadySaved();
                }

                Entrada.setEntradaUsuario("");
                break;
            case "2":
                ScreenClear.clear();
                Menu.askId();
                String livrosID = scanner.nextLine();
                ScreenClear.clear();
                Menu.connecting();
                String responseBody = apiService
                        .getResponseBody("https://gutendex.com/books/" + livrosID + "/");
                CheckNullResponseBody.check(responseBody, mapper, livroRepositorio);
                Entrada.setEntradaUsuario("");
                break;
            case "0":
                ScreenClear.clear();
                Entrada.setEntradaUsuario("");
                break;
            default:
                ScreenClear.clear();
                Menu.invalidOption();
                break;
        }
    }
}
