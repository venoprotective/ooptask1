package library.exception;

public class BookNotIssuedException extends RuntimeException {
    public BookNotIssuedException(String inventoryNumber) {
        super("Экземпляр не был выдан: " + inventoryNumber);
    }
}