package library;

import java.util.Objects;

public class Book {
    private final String isbn;
    private String title;
    private Author author;
    private Genre genre;
    private BookState state;

    public Book(String isbn, String title, Author author, Genre genre, BookState state) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.state = state;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return title + " - " + author + " [" + isbn + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;

        return Objects.equals(isbn, book.isbn);
    }
}

