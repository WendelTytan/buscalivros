package br.com.buscalivros.model;

public enum Language {
    ENGLISH("en"),
    PORTUGUESE("pt");

    private String lingua;

    Language(String lingua) {
        this.lingua = lingua;
    }

    public static Language fromString(String text) {
        for (Language language : Language.values()) {
            if (language.lingua.equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado para a opção fornecida");
    }
}
