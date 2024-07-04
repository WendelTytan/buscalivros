package br.com.buscalivros.model;

import jakarta.persistence.*;

@Entity
@Table(name = "temas")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Subject() {}

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
