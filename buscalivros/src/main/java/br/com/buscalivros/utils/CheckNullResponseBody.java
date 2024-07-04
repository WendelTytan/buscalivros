package br.com.buscalivros.utils;

import br.com.buscalivros.model.Book;
import br.com.buscalivros.model.BookData;
import br.com.buscalivros.repository.BookRepository;
import br.com.buscalivros.service.Mapper;
import org.springframework.dao.DataIntegrityViolationException;

public class CheckNullResponseBody {
    public static void check(String responseBody, Mapper mapper, BookRepository bookRepository) {
        if (responseBody != null) {
            BookData bookDataById = mapper.getClassFromJson(responseBody, BookData.class);
            ScreenClear.clear();

            if (bookDataById.invalidPage() == null) {
                Book bookById = new Book(bookDataById);
                Menu.saving();

                try {
                    bookRepository.save(bookById);
                    Menu.saved();
                } catch (DataIntegrityViolationException e) {
                    Menu.alreadySaved();
                }

                bookById.printBook();
            } else {
                Menu.notFound();
            }

        }
    }
}
