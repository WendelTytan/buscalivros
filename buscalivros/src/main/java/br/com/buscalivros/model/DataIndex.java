package br.com.buscalivros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataIndex(
        @JsonAlias("count") Integer totalBooks,
        @JsonAlias("next") String nextPage,
        @JsonAlias("previous") String previousPage,
        @JsonAlias("results") List<BookData> books,
        @JsonAlias("detail") String invalidPage
) {
}
