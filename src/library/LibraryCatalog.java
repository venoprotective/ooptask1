package library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryCatalog {

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, PhysicalBook> copies = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public void addCopy(PhysicalBook copy, Shelf shelf) {
        books.putIfAbsent(copy.getBook().getIsbn(), copy.getBook());

        if (copies.containsKey(copy.getInventoryNumber())) {
            throw new IllegalArgumentException(
                    "Экземпляр уже существует: " + copy.getInventoryNumber()
            );
        }

        copies.put(copy.getInventoryNumber(), copy);
        shelf.addBookCopy(copy);
    }

    public Book findByIsbn(String isbn) {
        return books.get(isbn);
    }

    public PhysicalBook findCopy(String inventoryNumber) {
        return copies.get(inventoryNumber);
    }

    public List<Book> findByAuthor(String author) {
        List<Book> result = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.getAuthor()
                    .getFullName()
                    .toLowerCase()
                    .contains(author.toLowerCase())) {

                result.add(book);
            }
        }

        return result;
    }

    public List<Book> findByTitle(String title) {
        List<Book> result = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.getTitle()
                    .toLowerCase()
                    .contains(title.toLowerCase())) {

                result.add(book);
            }
        }

        return result;
    }

    public List<Book> findByGenre(Genre genre) {
        List<Book> result = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.getGenre() == genre) {
                result.add(book);
            }
        }

        return result;
    }

    public StorageAddress getAddress(String inventoryNumber) {
        PhysicalBook copy = copies.get(inventoryNumber);

        if (copy == null) {
            throw new IllegalArgumentException("Экземпляр не найден");
        }

        return copy.getStorageAddress();
    }
}