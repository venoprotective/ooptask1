package library;

import library.entity.*;
import library.enums.BookState;
import library.enums.Genre;
import library.exception.BookAlreadyIssuedException;
import library.exception.BookNotIssuedException;
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

    // Подготовка тестовых данных:
    // создаётся каталог, иерархия хранения Room -> Cabinet -> Shelf,
    // книга, её физический экземпляр, и экземпляр добавляется в каталог на полку.
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

    // Проверяет, что каталог находит книгу по ISBN и возвращает именно ту книгу,
    // которая была добавлена в тестовых данных.
    @Test
    void findBookByIsbn() {
        Book result = catalog.findByIsbn("123");

        assertEquals(book, result);
    }

    // Проверяет, что каталог находит физический экземпляр по его инвентарному номеру
    // и возвращает именно этот экземпляр.
    @Test
    void findCopy() {
        PhysicalBook result = catalog.findCopy("001");

        assertEquals(copy, result);
    }

    // Проверяет поиск книг по части названия: книга "Effective Java"
    // должна находиться по строке "Effective".
    @Test
    void findBookByTitle() {
        List<Book> result = catalog.findByTitle("Effective");

        assertTrue(result.contains(book));
    }

    // Проверяет поиск книг по автору: книга должна находиться по фамилии "Bloch".
    @Test
    void findBookByAuthor() {
        List<Book> result = catalog.findByAuthor("Bloch");

        assertTrue(result.contains(book));
    }

    // Проверяет поиск книг по жанру: книга жанра SCIENCE
    // должна присутствовать в результате поиска по этому жанру.
    @Test
    void findBookByGenre() {
        List<Book> result = catalog.findByGenre(Genre.SCIENCE);

        assertTrue(result.contains(book));
    }

    // Проверяет, что для экземпляра можно получить адрес хранения,
    // и что в адресе правильно указаны комната, шкаф и полка.
    @Test
    void getBookAddress() {
        StorageAddress address = catalog.getAddress("001");

        assertEquals(room, address.getRoom());
        assertEquals(cabinet, address.getCabinet());
        assertEquals(shelf, address.getShelf());
    }

    // Проверяет запрет добавления в каталог второго экземпляра
    // с тем же инвентарным номером "001".
    @Test
    void cannotAddSameCopyTwice() {
        PhysicalBook secondCopy =
                new PhysicalBook("001", book, BookState.NEW);

        assertThrows(
                IllegalArgumentException.class,
                () -> catalog.addCopy(secondCopy, shelf)
        );
    }

    // Проверяет выдачу книги читателю: после выдачи создаётся активная выдача,
    // и в ней сохраняется имя читателя "Ivan".
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

    // Проверяет, что один и тот же экземпляр нельзя выдать повторно,
    // пока он уже выдан: ожидается BookAlreadyIssuedException.
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

    // Проверяет возврат выданной книги: после возврата список активных выдач
    // должен стать пустым.
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

    // Проверяет, что нельзя вернуть экземпляр, который не был выдан:
    // ожидается BookNotIssuedException.
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

    // Проверяет поиск просроченных выдач: если срок возврата 2026-10-05,
    // то на дату 2026-10-10 выдача должна считаться просроченной.
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

    // Проверяет историю выдач по читателю: после выдачи книги читателю "Ivan"
    // в его истории должна быть одна запись.
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

    // Проверяет историю выдач по книге: после выдачи экземпляра книги с ISBN "123"
    // в истории по этой книге должна быть одна запись.
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

    // Проверяет перемещение полки в другой шкаф:
    // после moveShelf полка должна ссылаться на новый шкаф "Cabinet 2".
    @Test
    void moveShelf() {
        Cabinet secondCabinet = new Cabinet("Cabinet 2");

        catalog.moveShelf(shelf, secondCabinet);

        assertEquals(secondCabinet, shelf.getCabinet());
    }

    // Проверяет перемещение шкафа в другую комнату:
    // после moveCabinet шкаф должен ссылаться на новую комнату "Room 2".
    @Test
    void moveCabinet() {
        Room secondRoom = new Room("Room 2");

        catalog.moveCabinet(cabinet, secondRoom);

        assertEquals(secondRoom, cabinet.getRoom());
    }

    // Проверяет распределение книг по жанрам:
    // в каталоге одна книга жанра SCIENCE, значит для SCIENCE должно быть 1.
    @Test
    void genreDistribution() {
        Map<Genre, Integer> result =
                catalog.getGenreDistribution();

        assertEquals(1, result.get(Genre.SCIENCE));
    }

    // Проверяет занятость полок для переданных комнат:
    // на полке "Room 1 -> Cabinet 1 -> Shelf 1" должен быть 1 экземпляр.
    @Test
    void shelfOccupancy() {
        Map<String, Integer> result =
                catalog.getShelfOccupancy(List.of(room));

        assertEquals(
                1,
                result.get("Room 1 -> Cabinet 1 -> Shelf 1")
        );
    }

    // Дублирует проверку cannotIssueBookTwice:
    // повторная выдача уже выданного экземпляра должна выбрасывать
    // BookAlreadyIssuedException.
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

    // Дублирует проверку cannotReturnBookThatWasNotIssued:
    // возврат невыданного экземпляра должен выбрасывать BookNotIssuedException.
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