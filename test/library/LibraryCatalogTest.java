package library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LibraryCatalogTest {

    private LibraryCatalog catalog;
    private Book book;
    private PhysicalBook copy;
    private Room room;
    private Cabinet cabinet;
    private Shelf shelf;

    @BeforeEach
    void setUp() {
        catalog = new LibraryCatalog();

        room = new Room("Room 1");
        cabinet = new Cabinet("Cabinet 1");
        shelf = new Shelf("Shelf 1");

        room.addCabinet(cabinet);
        cabinet.addShelf(shelf);

        Author author = new Author("Joshua", "Bloch");

        book = new Book(
                "123",
                "Effective Java",
                author,
                Genre.SCIENCE
        );

        copy = new PhysicalBook(
                "001",
                book,
                BookState.NEW
        );

        catalog.addCopy(copy, shelf);
    }

    @Test
    void findBookByIsbn() {
        Book result = catalog.findByIsbn("123");

        assertEquals(book, result);
    }

    @Test
    void findCopy() {
        PhysicalBook result = catalog.findCopy("001");

        assertEquals(copy, result);
    }

    @Test
    void findBookByTitle() {
        List<Book> result = catalog.findByTitle("Effective");

        assertTrue(result.contains(book));
    }

    @Test
    void findBookByAuthor() {
        List<Book> result = catalog.findByAuthor("Bloch");

        assertTrue(result.contains(book));
    }

    @Test
    void findBookByGenre() {
        List<Book> result = catalog.findByGenre(Genre.SCIENCE);

        assertTrue(result.contains(book));
    }

    @Test
    void getBookAddress() {
        StorageAddress address = catalog.getAddress("001");

        assertEquals(room, address.getRoom());
        assertEquals(cabinet, address.getCabinet());
        assertEquals(shelf, address.getShelf());
    }

    @Test
    void cannotAddSameCopyTwice() {
        PhysicalBook secondCopy =
                new PhysicalBook("001", book, BookState.NEW);

        assertThrows(
                IllegalArgumentException.class,
                () -> catalog.addCopy(secondCopy, shelf)
        );
    }

    @Test
    void issueBook() {
        catalog.issueBook(
                "001",
                "Ivan",
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 10)
        );

        List<Loan> loans = catalog.getIssuedLoans();

        assertEquals(1, loans.size());
        assertEquals("Ivan", loans.get(0).getReader());
    }

    @Test
    void cannotIssueBookTwice() {
        catalog.issueBook(
                "001",
                "Ivan",
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 10)
        );

        assertThrows(
                BookAlreadyIssuedException.class,
                () -> catalog.issueBook(
                        "001",
                        "Anna",
                        LocalDate.of(2026, 10, 2),
                        LocalDate.of(2026, 10, 15)
                )
        );
    }

    @Test
    void returnBook() {
        catalog.issueBook(
                "001",
                "Ivan",
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 10)
        );

        catalog.returnBook(
                "001",
                LocalDate.of(2026, 10, 5)
        );

        assertTrue(catalog.getIssuedLoans().isEmpty());
    }

    @Test
    void cannotReturnBookThatWasNotIssued() {
        assertThrows(
                BookNotIssuedException.class,
                () -> catalog.returnBook(
                        "001",
                        LocalDate.of(2026, 10, 5)
                )
        );
    }

    @Test
    void findOverdueBook() {
        catalog.issueBook(
                "001",
                "Ivan",
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 5)
        );

        List<Loan> loans =
                catalog.getOverdueLoans(
                        LocalDate.of(2026, 10, 10)
                );

        assertEquals(1, loans.size());
    }

    @Test
    void getReaderHistory() {
        catalog.issueBook(
                "001",
                "Ivan",
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 10)
        );

        List<Loan> history =
                catalog.getHistoryByReader("Ivan");

        assertEquals(1, history.size());
    }

    @Test
    void getBookHistory() {
        catalog.issueBook(
                "001",
                "Ivan",
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 10)
        );

        List<Loan> history =
                catalog.getHistoryByBook("123");

        assertEquals(1, history.size());
    }

    @Test
    void moveShelf() {
        Cabinet secondCabinet = new Cabinet("Cabinet 2");

        catalog.moveShelf(shelf, secondCabinet);

        assertEquals(secondCabinet, shelf.getCabinet());
    }

    @Test
    void moveCabinet() {
        Room secondRoom = new Room("Room 2");

        catalog.moveCabinet(cabinet, secondRoom);

        assertEquals(secondRoom, cabinet.getRoom());
    }

    @Test
    void genreDistribution() {
        Map<Genre, Integer> result =
                catalog.getGenreDistribution();

        assertEquals(1, result.get(Genre.SCIENCE));
    }

    @Test
    void shelfOccupancy() {
        Map<String, Integer> result =
                catalog.getShelfOccupancy(List.of(room));

        assertEquals(
                1,
                result.get("Room 1 -> Cabinet 1 -> Shelf 1")
        );
    }

    @Test
    void cannotIssueAlreadyIssuedBook() {
        catalog.issueBook(
                "001",
                "Ivan",
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 10)
        );

        assertThrows(
                BookAlreadyIssuedException.class,
                () -> catalog.issueBook(
                        "001",
                        "Anna",
                        LocalDate.of(2026, 10, 2),
                        LocalDate.of(2026, 10, 15)
                )
        );
    }

    @Test
    void cannotReturnNotIssuedBook() {
        assertThrows(
                BookNotIssuedException.class,
                () -> catalog.returnBook(
                        "001",
                        LocalDate.of(2026, 10, 5)
                )
        );
    }
}