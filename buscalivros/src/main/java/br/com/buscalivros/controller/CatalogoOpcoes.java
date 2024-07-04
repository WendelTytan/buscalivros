package br.com.buscalivros.controller;

import br.com.buscalivros.entrada.Entrada;
import br.com.buscalivros.model.Book;
import br.com.buscalivros.model.DataIndex;
import br.com.buscalivros.repository.BookRepository;
import br.com.buscalivros.service.ApiService;
import br.com.buscalivros.service.Mapper;
import br.com.buscalivros.utils.CheckNullResponseBody;
import br.com.buscalivros.utils.Menu;
import br.com.buscalivros.utils.ScreenClear;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CatalogoOpcoes {
    private final Scanner scanner = new Scanner(System.in);
    private final ApiService apiService = new ApiService();
    private final Mapper mapper = new Mapper();
    private final DataIndex dadosIndex;
    private final String usuarioOpcoes;
    private final List<Book> livros;
    private final BookRepository livroRepositorio;
    private String apiPagina;
    private Integer numeroPaginaApi;


    public CatalogoOpcoes(String usuarioOpcoes, List<Book> livros, BookRepository livroRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.dadosIndex = Entrada.getIndexDados();
        this.usuarioOpcoes = usuarioOpcoes;
        this.livros = livros;
        this.apiPagina = Entrada.getPaginaApi();
        this.numeroPaginaApi = Entrada.getNumeroPaginaApi();
    }

    public void checagem() {
        switch (this.usuarioOpcoes) {
            case "1":
                if (dadosIndex.previousPage() != null) {
                    numeroPaginaApi--;
                    apiPagina = String.valueOf(numeroPaginaApi);
                    Entrada.setNumeroPaginaApi(numeroPaginaApi);
                    Entrada.setPaginaApi(apiPagina);
                }
                break;
            case "2":
                if (dadosIndex.nextPage() != null) {
                    numeroPaginaApi++;
                    apiPagina = String.valueOf(numeroPaginaApi);
                    Entrada.setNumeroPaginaApi(numeroPaginaApi);
                    Entrada.setPaginaApi(apiPagina);
                }
                break;
            case "3":
                Menu.askPage();

                try {
                    numeroPaginaApi = scanner.nextInt();
                    scanner.nextLine();
                    apiPagina = String.valueOf(numeroPaginaApi).trim();
                    Entrada.setNumeroPaginaApi(numeroPaginaApi);
                    Entrada.setPaginaApi(apiPagina);
                } catch (InputMismatchException e) {
                    System.out.println("A busca deve ser por número.");
                    break;
                }
                break;
            case "4":
                ScreenClear.clear();
                Menu.saving();

                try {
                    livroRepositorio.saveAll(livros);
                    Menu.saved();
                } catch (DataIntegrityViolationException e) {
                    Menu.alreadySaved();
                }

                break;
            case "5":
                Menu.askId();
                String livroID = scanner.nextLine();
                ScreenClear.clear();
                Menu.connecting();
                String responseBody = apiService
                        .getResponseBody("https://gutendex.com/books/" + livroID + "/");
                CheckNullResponseBody.check(responseBody, mapper, livroRepositorio);
                break;
            case "6":
                Menu.askOption();
                Menu.askLanguage();
                String linguaOpcao = scanner.nextLine();
                ScreenClear.clear();

                switch (linguaOpcao) {
                    case "1":
                        Entrada.setIngles("en");
                        Entrada.setPortugues("");
                        break;
                    case "2":
                        Entrada.setIngles("");
                        Entrada.setPortugues("pt");
                        break;
                    case "3":
                        Entrada.setIngles("en");
                        Entrada.setPortugues("pt");
                        break;
                    case "0":
                        Menu.backToCatalogue();
                        break;
                    default:
                        Entrada.setIngles("en");
                        Entrada.setPortugues("pt");
                        Menu.invalidOption();
                        break;
                }

                break;
            case "0":
                Entrada.setEntradaUsuario("");
                break;
            default:
                Menu.invalidOption();
                break;
        }
    }
}
