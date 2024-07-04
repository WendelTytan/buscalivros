package br.com.buscalivros.controller;

import br.com.buscalivros.model.Book;
import br.com.buscalivros.utils.Menu;
import br.com.buscalivros.utils.ScreenClear;
import org.springframework.data.domain.Page;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ArquivoOpcoes {
    private final Scanner scanner = new Scanner(System.in);
    private String arquivoEntrada;
    private Integer numeroPagina;
    private Page<Book> pagina;

    public ArquivoOpcoes(String arquivoEntrada, Page<Book> pagina, Integer numeroPagina) {
        this.arquivoEntrada = arquivoEntrada;
        this.pagina = pagina;
        this.numeroPagina = numeroPagina;
    }

    public void checagem() {
        switch (arquivoEntrada) {
            case "1":
                if (!pagina.isFirst()) {
                    numeroPagina--;
                    Arquivo.setNumeroPagina(numeroPagina);
                }
                break;
            case "2":
                if (!pagina.isLast()) {
                    numeroPagina++;
                    Arquivo.setNumeroPagina(numeroPagina);
                }
                break;
            case "3":
                boolean checagem = false;

                while (!checagem) {
                    Menu.askPage();

                    try {
                        numeroPagina = scanner.nextInt();
                        scanner.nextLine();

                        if (numeroPagina >= 1 && numeroPagina <= pagina.getTotalPages()) {
                            Arquivo.setNumeroPagina(numeroPagina);
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
                Menu.askOption();
                Menu.askLanguage();
                String lingua = scanner.nextLine();
                ScreenClear.clear();

                switch (lingua) {
                    case "1":
                        Arquivo.setLingua("en");
                        break;
                    case "2":
                        Arquivo.setLingua("pt");
                        break;
                    case "3":
                        Arquivo.setLingua("all");
                        break;
                    case "0":
                        Menu.backToCatalogue();
                        break;
                    default:
                        Menu.invalidOption();
                        break;
                }

                break;
            case "0":
                Arquivo.setNumeroPagina(1);
                arquivoEntrada = "0";
                break;
            default:
                Menu.invalidOption();
                break;
        }
    }

}
