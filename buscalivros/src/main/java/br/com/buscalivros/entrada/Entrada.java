package br.com.buscalivros.entrada;

import br.com.buscalivros.controller.Arquivo;
import br.com.buscalivros.controller.AutorArquivo;
import br.com.buscalivros.controller.Catalogo;
import br.com.buscalivros.controller.Busca;
import br.com.buscalivros.model.DataIndex;
import br.com.buscalivros.repository.AuthorRepository;
import br.com.buscalivros.repository.BookRepository;
import br.com.buscalivros.utils.Menu;
import br.com.buscalivros.service.ApiService;
import br.com.buscalivros.service.Mapper;
import br.com.buscalivros.utils.ScreenClear;

import java.util.Scanner;

public class Entrada {
    private final Scanner scanner = new Scanner(System.in);
    private final ApiService apiService = new ApiService();
    private final Mapper mapper = new Mapper();
    private final BookRepository livroRepositorio;
    private final AuthorRepository autorRepositorio;
    private static DataIndex indexDados;
    private static Integer numeroPaginaApi = 1;
    private static String portugues = "pt";
    private static final String virgula = ",";
    private static String ingles = "en";
    private static String paginaApi = String.valueOf(numeroPaginaApi);
    private static String entradaUsuario = "";
    private String responseBody;

    public Entrada(BookRepository livroRepositorio, AuthorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void init() {
        ScreenClear.clear();
        boolean sair = false;
        
        while (!sair) {
            
            if (entradaUsuario.isEmpty()) {
                Menu.mainMenu();
                entradaUsuario = scanner.nextLine();
                ScreenClear.clear();
            }

            switch (entradaUsuario) {
                case "1":
                    Menu.connecting();
                    responseBody = apiService.getResponseBody(
                                    "https://gutendex.com/books/?languages=" +
                                            portugues + virgula + ingles +
                                        "&page=" + paginaApi
                                    );
                    
                    if (responseBody != null) {
                        indexDados = mapper.getClassFromJson(responseBody, DataIndex.class);
                        Catalogo catalogo = new Catalogo(indexDados, livroRepositorio);
                        catalogo.load();
                    }
                    
                    break;
                case "2":
                    Menu.askName();
                    String buscaNomeApi =
                            scanner.nextLine().replace(" ", "%20").toLowerCase();
                    ScreenClear.clear();
                    Menu.connecting();
                    responseBody = apiService
                            .getResponseBody("https://gutendex.com/books/?languages=pt,en&search=" + buscaNomeApi);
                    indexDados = mapper.getClassFromJson(responseBody, DataIndex.class);
                    Busca busca = new Busca(livroRepositorio);
                    busca.load();
                    break;
                case "3":
                    Arquivo arquivo = new Arquivo(livroRepositorio);
                    arquivo.load();
                    entradaUsuario = "";
                    break;
                case "4":
                    AutorArquivo autorArquivo = new AutorArquivo(autorRepositorio);
                    autorArquivo.load();
                    entradaUsuario = "";
                    break;
                case "0":
                    Menu.exit();
                    sair = true;
                    break;
                default:
                    Menu.invalidOption();
                    entradaUsuario = "";
                    break;
            }
        }
    }

    public static String getPortugues() {
        return portugues;
    }

    public static void setPortugues(String portugues) {
        Entrada.portugues = portugues;
    }

    public static String getIngles() {
        return ingles;
    }

    public static void setIngles(String ingles) {
        Entrada.ingles = ingles;
    }

    public static String getPaginaApi() {
        return paginaApi;
    }

    public static void setPaginaApi(String paginaApi) {
        Entrada.paginaApi = paginaApi;
    }

    public static Integer getNumeroPaginaApi() {
        return numeroPaginaApi;
    }

    public static void setNumeroPaginaApi(Integer numeroPaginaApi) {
        Entrada.numeroPaginaApi = numeroPaginaApi;
    }

    public static DataIndex getIndexDados() {
        return indexDados;
    }

    public static void setEntradaUsuario(String entradaUsuario) {
        Entrada.entradaUsuario = entradaUsuario;
    }
}
