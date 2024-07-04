package br.com.buscalivros.controller;

import br.com.buscalivros.model.Author;
import br.com.buscalivros.repository.AuthorRepository;
import br.com.buscalivros.utils.Menu;
import br.com.buscalivros.utils.ScreenClear;
import org.springframework.data.domain.Page;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AutorOpcoesArquivo {
    private final Scanner scanner = new Scanner(System.in);
    private final AuthorRepository autorRepositorio;
    private String autorEntrada;
    private Integer paginaNumero;
    private Page<Author> pagina;

    public AutorOpcoesArquivo(AuthorRepository autorRepositorio, String autorEntrada, Page<Author> pagina, Integer paginaNumero) {
        this.autorRepositorio = autorRepositorio;
        this.autorEntrada = autorEntrada;
        this.pagina = pagina;
        this.paginaNumero = paginaNumero;
    }

    public void checagem() {
        switch (autorEntrada) {
            case "1":
                if (!pagina.isFirst()) {
                    paginaNumero--;
                    AutorArquivo.setNumeroPagina(paginaNumero);
                }
                break;
            case "2":
                if (!pagina.isLast()) {
                    paginaNumero++;
                    AutorArquivo.setNumeroPagina(paginaNumero);
                }
                break;
            case "3":
                boolean checagem = false;

                while (!checagem) {
                    Menu.askPage();

                    try {
                        paginaNumero = scanner.nextInt();
                        scanner.nextLine();

                        if (paginaNumero >= 1 && paginaNumero <= pagina.getTotalPages()) {
                            AutorArquivo.setNumeroPagina(paginaNumero);
                            checagem = true;
                            ScreenClear.clear();
                        } else {
                            ScreenClear.clear();
                            Menu.invalidPage(pagina);
                        }

                    } catch (InputMismatchException e) {
                        Menu.invalidPage(pagina);
                        break;
                    }

                }
                break;
            case "4":
                boolean anoBusca = false;

                while (!anoBusca) {
                    System.out.println("Informe o ano para busca: ");

                    try {
                        Integer ano = scanner.nextInt();
                        scanner.nextLine();
                        List<Author> authorVivo =
                                autorRepositorio.findAuthorsAliveInYear(ano);
                        if (!authorVivo.isEmpty()) {
                            authorVivo.forEach(Author::printAuthor);
                        } else {
                            System.out.println("Nenhum autor armazenado vivo em " + ano + "!");
                        }
                        AutorArquivo.setAutorEntrada("0");
                        anoBusca = true;
                    } catch (InputMismatchException e) {
                        Menu.invalidOption();
                        break;
                    }

                }

                break;
            case "0":
                AutorArquivo.setNumeroPagina(1);
                autorEntrada = "0";
                break;
            default:
                Menu.invalidOption();
                break;
        }
    }
}
