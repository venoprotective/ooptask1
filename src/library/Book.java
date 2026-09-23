package library;

import java.util.Objects;

public class Book {
    private final String isbn;
    private String title;
    private Author author;
    private Genre genre;

    public Book(String isbn, String title, Author author, Genre genre) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
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
}

