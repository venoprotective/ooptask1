package library;

import java.util.Objects;

public class PhysicalBook {
    private final String inventoryNumber;
    private final Book book;
    private BookState state;
    private Shelf shelf;

    public PhysicalBook(String inventoryNumber, Book book, BookState state) {
        this.inventoryNumber = inventoryNumber;
        this.book = book;
        this.state = state;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public Book getBook() {
        return book;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public Shelf getShelf() {
        return shelf;
    }

    void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public StorageAddress getStorageAddress() {
        if (shelf == null) {
            throw new IllegalStateException("Экземпляр не находится на полке");
        }

        return new StorageAddress(shelf);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhysicalBook copy)) return false;

        return Objects.equals(inventoryNumber, copy.inventoryNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventoryNumber);
    }

    @Override
    public String toString() {
        return inventoryNumber + ": " + book + " (" + state + ")";
    }
}