package br.com.buscalivros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
        @JsonAlias("id") Integer idBook,
        String title,
        List<AuthorData> authors,
        List<String> subjects,
        List<String> languages,
        Boolean copyright,
        @JsonAlias("download_count") Integer downloadCount,
        @JsonAlias("detail") String invalidPage
) {
}
