package br.com.buscalivros.utils;

import br.com.buscalivros.controller.Arquivo;
import br.com.buscalivros.entrada.Entrada;
import br.com.buscalivros.model.Author;
import br.com.buscalivros.model.Book;
import br.com.buscalivros.model.Language;
import org.springframework.data.domain.Page;

import java.util.List;

public class Menu {
    private static final String mainMenu = """
            
            ***************** Buscalivros *****************
            
            Escolha uma opção:
            
            1 - Exibir catálogo online
            2 - Buscar título/autor no catálogo online
            3 - Consultar livros salvos no banco de dados
            4 - Consultar autores salvos no banco de dados
            0 - Sair
            
            ***********************************************
            """;
    private static final String searchMenu = """
            Menu da Busca por Título/Autor:
            
            1 - Salvar todos os livros desta página
            2 - Salvar livro por id
            0 - Voltar ao menu 
            ---------------------------------------
            """;
    private static final String catalogueMenu = """
            Menu do Catálogo:
            
            1 - Página anterior
            2 - Próxima página
            3 - Buscar página no catálogo
            4 - Salvar todos os livros desta página
            5 - Salvar livro por id
            6 - Filtrar livros por idioma
            0 - Voltar ao menu principal
            ---------------------------------------
            """;
    private static final String archiveMenu = """
            Lista de Livros do Banco de Dados:
            
            1 - Página anterior
            2 - Pŕoxima página
            3 - Buscar página no banco de dados
            4 - Filtrar livros por idioma
            0 - Voltar ao menu principal
            ------------------------------------
            """;
    private static final String autorMenu = """
            Lista de Autores do Banco de Dados:
            
            1 - Página anterior
            2 - Pŕoxima página
            3 - Buscar página no banco de dados
            4 - Filtrar autores vivos em determinado ano
            0 - Voltar ao menu principal
            --------------------------------------------
            """;

    public static void autorMenu() { System.out.println(autorMenu); }

    public static void autorMenuInfo(int pageNumber, Page<Author> page) {
        System.out.println("Exibindo página " + pageNumber + " de " + page.getTotalPages());
        System.out.println("Autores nesta página: " + page.getNumberOfElements());
        System.out.println("Total de Autores Armazenados: " + page.getTotalElements() + "\n");
    }


    // Entrada
    public static void mainMenu() {
        System.out.println(mainMenu);
    }
    // Entrada
    public static void searchMenu() {
        System.out.println(searchMenu);
    }
    // Entrada
    public static void archiveMenu() { System.out.println(archiveMenu); }
    // Entrada
    public static void askName() { System.out.println("Digite um nome para busca: "); }
    // Entrada
    public static void exit() { System.out.println("Encerrando...\n"); }
    // Entrada
    public static void archiveMenuInfo (int pageNumber, Page<Book> page, List<Book> books) {
        System.out.println("Exibindo página " + pageNumber + " de " + page.getTotalPages());
        System.out.println("Livros nesta página: " + page.getNumberOfElements());
        System.out.println("Total de Livros Armazenados: " + page.getTotalElements());
        System.out.println("Exibindo os idiomas: ");
        List<Book> booksEn = books.stream().filter(book -> book.getLanguage().equals(Language.ENGLISH)).toList();
        List<Book> booksPt = books.stream().filter(book -> book.getLanguage().equals(Language.PORTUGUESE)).toList();

        if (Arquivo.getLingua().equals("en")) {
            System.out.println("+ Inglês: " + booksEn.size() + " livros\n");
        } else if (Arquivo.getLingua().equals("pt")) {
            System.out.println("+ Português: " + booksPt.size() + " livros\n");
        } else {
            System.out.println("+ Inglês: " + booksEn.size() + " livros");
            System.out.println("+ Português: " + booksPt.size() + " livros\n");
        }
    }

    // ArquivoOpcoes
    public static <T> void invalidPage (Page<T> page) {
        System.out.println("Digite uma página entre 1 e " + page.getTotalPages() + ".");
    }

    // Catalogo
    public static void pageNotFound() { System.out.println("Página: " + Entrada.getPaginaApi() + " não encontrada"); }
    // Catalogo
    public static void pageAndLang() {
        System.out.println("***** Página: " + Entrada.getPaginaApi() + " *****");
        System.out.println("* Exibindo os idiomas *");
        if (Entrada.getIngles().equals("en") && Entrada.getPortugues().isEmpty()) {
            System.out.println("+ Inglês\n");
        } else if (Entrada.getPortugues().equals("pt") && Entrada.getIngles().isEmpty()) {
            System.out.println("+ Português\n");
        } else {
            System.out.println("+ Inglês");
            System.out.println("+ Português\n");
        }
    }

    // CatalogoOpcoes
    public static void askId() { System.out.println("Digite o id do livro: "); }
    // CatalogoOpcoes
    public static void askLanguage() {
        System.out.println("\n1 - Inglês");
        System.out.println("2 - Português");
        System.out.println("3 - Inglês e Português");
        System.out.println("0 - Voltar\n");
    }

    // CatalogoOpcoes
    public static void saving() { System.out.println("Salvando...\n"); }
    // CatalogoOpcoes
    public static void saved() { System.out.println("Salvo com sucesso!\n"); }
    // CatalogoOpcoes
    public static void alreadySaved() { System.out.println("Livro/Livros já armazenado(s) anteriormente!\n"); }

    // Entrada and Catalogo
    public static void catalogueMenu() { System.out.println(catalogueMenu); }
    // Catalogo and CatalogoOpcoes
    public static void backToCatalogue() { System.out.println("Voltando ao catálogo...\n"); }
    // Entrada and CatalogoOpcoes
    public static void askOption() { System.out.println("Selecione uma opção: "); }
    // Entrada and CatalogoOpcoes
    public static void connecting() { System.out.println("Estabelecendo conexão...\n"); }
    // Entrada and CatalogoOpcoes
    public static void invalidOption() { System.out.println("Digite uma opção válida."); }
    // Entrada and CatalogoOpcoes
    public static void notFound() { System.out.println("Livro/Autor não encontrado."); }
    // Entrada and CatalogoOpcoes
    public static void askPage() { System.out.println("Digite o número da página para busca: "); }

}
