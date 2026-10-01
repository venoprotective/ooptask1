package library;

import java.time.LocalDate;
import java.util.List;

public class Main {

    static LibraryCatalog catalog = new LibraryCatalog();

    static Room room1 = new Room("ROOM1");
    static Room room2 = new Room("ROOM2");

    static Cabinet cabinet1 = new Cabinet("CABINET1");
    static Cabinet cabinet2 = new Cabinet("CABINET2");

    static Shelf shelfJava = new Shelf("SHELF_JAVA");
    static Shelf shelfPython = new Shelf("SHELF_PYTHON");
    static Shelf shelfGolang = new Shelf("SHELF_GOLANG");
    static Shelf shelfOther = new Shelf("SHELF_OTHER");


    static Author AUTHOR_JAVA1;
    static Author AUTHOR_JAVA2;

    static Author AUTHOR_PYTHON1;
    static Author AUTHOR_PYTHON2;

    static Author AUTHOR_GOLANG1;
    static Author AUTHOR_GOLANG2;

    static Author AUTHOR_RUST1;
    static Author AUTHOR_JAVASCRIPT1;


    static Book JAVA1;
    static Book JAVA2;

    static Book PYTHON1;
    static Book PYTHON2;

    static Book GOLANG1;
    static Book GOLANG2;

    static Book RUST1;
    static Book JAVASCRIPT1;


    public static void main(String[] args) {

        fillLibrary();

        test1();
        test2();
        test3();
        test4();
        test5();
    }


    static void fillLibrary() {

        room1.addCabinet(cabinet1);
        room2.addCabinet(cabinet2);

        cabinet1.addShelf(shelfJava);
        cabinet1.addShelf(shelfPython);
        cabinet1.addShelf(shelfGolang);

        cabinet2.addShelf(shelfOther);


        AUTHOR_JAVA1 = new Author(
                "AUTHOR_JAVA1",
                ""
        );

        AUTHOR_JAVA2 = new Author(
                "AUTHOR_JAVA2",
                ""
        );

        AUTHOR_PYTHON1 = new Author(
                "AUTHOR_PYTHON1",
                ""
        );

        AUTHOR_PYTHON2 = new Author(
                "AUTHOR_PYTHON2",
                ""
        );

        AUTHOR_GOLANG1 = new Author(
                "AUTHOR_GOLANG1",
                ""
        );

        AUTHOR_GOLANG2 = new Author(
                "AUTHOR_GOLANG2",
                ""
        );

        AUTHOR_RUST1 = new Author(
                "AUTHOR_RUST1",
                ""
        );

        AUTHOR_JAVASCRIPT1 = new Author(
                "AUTHOR_JAVASCRIPT1",
                ""
        );


        JAVA1 = new Book(
                "000-001",
                "JAVA1",
                AUTHOR_JAVA1,
                Genre.SCIENCE
        );

        JAVA2 = new Book(
                "000-002",
                "JAVA2",
                AUTHOR_JAVA2,
                Genre.SCIENCE
        );

        PYTHON1 = new Book(
                "000-003",
                "PYTHON1",
                AUTHOR_PYTHON1,
                Genre.SCIENCE
        );

        PYTHON2 = new Book(
                "000-004",
                "PYTHON2",
                AUTHOR_PYTHON2,
                Genre.SCIENCE
        );

        GOLANG1 = new Book(
                "000-005",
                "GOLANG1",
                AUTHOR_GOLANG1,
                Genre.SCIENCE
        );

        GOLANG2 = new Book(
                "000-006",
                "GOLANG2",
                AUTHOR_GOLANG2,
                Genre.SCIENCE
        );

        RUST1 = new Book(
                "000-007",
                "RUST1",
                AUTHOR_RUST1,
                Genre.SCIENCE
        );

        JAVASCRIPT1 = new Book(
                "000-008",
                "JAVASCRIPT1",
                AUTHOR_JAVASCRIPT1,
                Genre.SCIENCE
        );


        catalog.addCopy(
                new PhysicalBook(
                        "COPY-001",
                        JAVA1,
                        BookState.NEW
                ),
                shelfJava
        );

        catalog.addCopy(
                new PhysicalBook(
                        "COPY-002",
                        JAVA2,
                        BookState.LIKENEW
                ),
                shelfJava
        );

        catalog.addCopy(
                new PhysicalBook(
                        "COPY-003",
                        PYTHON1,
                        BookState.NEW
                ),
                shelfPython
        );

        catalog.addCopy(
                new PhysicalBook(
                        "COPY-004",
                        PYTHON2,
                        BookState.OLD
                ),
                shelfPython
        );

        catalog.addCopy(
                new PhysicalBook(
                        "COPY-005",
                        GOLANG1,
                        BookState.NEW
                ),
                shelfGolang
        );

        catalog.addCopy(
                new PhysicalBook(
                        "COPY-006",
                        GOLANG2,
                        BookState.LIKENEW
                ),
                shelfGolang
        );

        catalog.addCopy(
                new PhysicalBook(
                        "COPY-007",
                        RUST1,
                        BookState.OLD
                ),
                shelfOther
        );

        catalog.addCopy(
                new PhysicalBook(
                        "COPY-008",
                        JAVASCRIPT1,
                        BookState.NEW
                ),
                shelfOther
        );
    }


    static void test1() {

        System.out.println("\nTEST1");

        System.out.println("JAVA:");
        System.out.println(catalog.findByTitle("JAVA"));// если будет javas выведет онли жс

        System.out.println("PYTHON:");
        System.out.println(catalog.findByTitle("PYTHON"));

        System.out.println("GOLANG:");
        System.out.println(catalog.findByTitle("GOLANG"));

        System.out.println("AUTHOR_JAVA1:");
        System.out.println(catalog.findByAuthor("AUTHOR_JAVA1"));

        System.out.println("000-003:");
        System.out.println(catalog.findByIsbn("000-003"));
    }


    static void test2() {

        System.out.println("\nTEST2");

        System.out.println(
                "JAVA1: " + catalog.getAddress("COPY-001")
        );

        System.out.println(
                "PYTHON1: " + catalog.getAddress("COPY-003")
        );

        System.out.println(
                "GOLANG1: " + catalog.getAddress("COPY-005")
        );

        System.out.println(
                "RUST1: " + catalog.getAddress("COPY-007")
        );
    }


    static void test3() {

        System.out.println("\nTEST3");

        catalog.issueBook(
                "COPY-001",
                "Иван",
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 15)
        );

        System.out.println("Выданные:");
        System.out.println(catalog.getIssuedLoans());


        try {

            catalog.issueBook(
                    "COPY-001",
                    "Максим",
                    LocalDate.of(2026, 10, 2),
                    LocalDate.of(2026, 10, 20)
            );

        } catch (BookAlreadyIssuedException e) {

            System.out.println(e.getMessage());
        }


        catalog.returnBook(
                "COPY-001",
                LocalDate.of(2026, 10, 10)
        );

        System.out.println("После возврата:");
        System.out.println(catalog.getIssuedLoans());
    }


    static void test4() {

        System.out.println("\nTEST4");

        catalog.issueBook(
                "COPY-003",
                "Анна",
                LocalDate.of(2026, 9, 1),
                LocalDate.of(2026, 9, 10)
        );

        catalog.issueBook(
                "COPY-005",
                "Максим",
                LocalDate.of(2026, 9, 5),
                LocalDate.of(2026, 10, 10)
        );

        catalog.issueBook(
                "COPY-007",
                "Анна",
                LocalDate.of(2026, 9, 5),
                LocalDate.of(2026, 9, 15)
        );


        System.out.println("Просроченные:");

        System.out.println(
                catalog.getOverdueLoans(
                        LocalDate.of(2026, 9, 25)
                )
        );


        System.out.println("История Анны:");

        System.out.println(
                catalog.getHistoryByReader("Анна")
        );


        System.out.println("История PYTHON1:");

        System.out.println(
                catalog.getHistoryByBook(
                        PYTHON1.getIsbn()
                )
        );
    }


    static void test5() {

        System.out.println("\nTEST5");

        System.out.println("PYTHON1 до переноса:");
        System.out.println(
                catalog.getAddress("COPY-003")
        );


        catalog.moveShelf(
                shelfPython,
                cabinet2
        );


        System.out.println("После переноса полки:");
        System.out.println(
                catalog.getAddress("COPY-003")
        );


        catalog.moveCabinet(
                cabinet2,
                room1
        );


        System.out.println("После переноса шкафа:");
        System.out.println(
                catalog.getAddress("COPY-003")
        );


        System.out.println("Жанры:");

        System.out.println(
                catalog.getGenreDistribution()
        );


        System.out.println("Полки:");

        System.out.println(
                catalog.getShelfOccupancy(
                        List.of(room1, room2)
                )
        );
    }
}