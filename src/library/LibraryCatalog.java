package library;

import java.time.LocalDate;
import java.util.*;

public class LibraryCatalog {

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, PhysicalBook> copies = new HashMap<>();
    private final List<Loan> loanHistory = new ArrayList<>();

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
            if (book.getAuthor().getFullName().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }

        return result;
    }

    public List<Book> findByTitle(String title) {
        List<Book> result = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
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

    public void issueBook(String inventoryNumber,
                          String reader,
                          LocalDate issueDate,
                          LocalDate dueDate) {

        PhysicalBook copy = copies.get(inventoryNumber);

        if (copy == null) {
            throw new IllegalArgumentException("Экземпляр не найден");
        }

        for (Loan loan : loanHistory) {
            if (loan.getCopy().equals(copy) && loan.isActive()) {
                throw new BookAlreadyIssuedException(inventoryNumber);
            }
        }

        Loan loan = new Loan(
                copy,
                reader,
                issueDate,
                dueDate
        );

        loanHistory.add(loan);
    }

    public void returnBook(String inventoryNumber, LocalDate returnDate) {
        for (Loan loan : loanHistory) {
            if (loan.getCopy().getInventoryNumber().equals(inventoryNumber) && loan.isActive()) {
                loan.close(returnDate);
                return;
            }
        }
        throw new BookNotIssuedException(inventoryNumber);
    }

    public List<Loan> getIssuedLoans() {
        List<Loan> result = new ArrayList<>();

        for (Loan loan : loanHistory) {
            if (loan.isActive()) {
                result.add(loan);
            }
        }

        return result;
    }

    public List<Loan> getOverdueLoans(LocalDate date) {
        List<Loan> result = new ArrayList<>();

        for (Loan loan : loanHistory) {
            if (loan.isOverdue(date)) {
                result.add(loan);
            }
        }

        return result;
    }

    public void moveShelf(Shelf shelf, Cabinet newCabinet) {
        newCabinet.addShelf(shelf);
    }

    public void moveCabinet(Cabinet cabinet, Room newRoom) {
        newRoom.addCabinet(cabinet);
    }

    public List<Loan> getHistoryByBook(String isbn) {
        List<Loan> result = new ArrayList<>();

        for (Loan loan : loanHistory) {
            if (loan.getCopy().getBook().getIsbn().equals(isbn)) {
                result.add(loan);
            }
        }

        return result;
    }

    public List<Loan> getHistoryByReader(String reader) {
        List<Loan> result = new ArrayList<>();

        for (Loan loan : loanHistory) {
            if (loan.getReader().equalsIgnoreCase(reader)) {
                result.add(loan);
            }
        }

        return result;
    }

    public Map<Genre, Integer> getGenreDistribution() {
        Map<Genre, Integer> result = new HashMap<>();

        for (PhysicalBook copy : copies.values()) {
            Genre genre = copy.getBook().getGenre();

            result.put(genre, result.getOrDefault(genre, 0) + 1);
        }

        return result;
    }

    public Map<String, Integer> getShelfOccupancy(List<Room> rooms) {
        Map<String, Integer> result = new HashMap<>();

        for (Room room : rooms) {
            for (Cabinet cabinet : room.getCabinets()) {
                for (Shelf shelf : cabinet.getShelves()) {
                    String address = room.getName() + " -> " + cabinet.getName() + " -> " + shelf.getName();
                    result.put(address, shelf.getBookCopies().size());
                }
            }
        }

        return result;
    }
}