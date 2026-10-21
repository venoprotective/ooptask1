package library.exception;

public class BookAlreadyIssuedException extends RuntimeException {
    public BookAlreadyIssuedException(String inventoryNumber){
        super("Экземлпяр уже выдали: " + inventoryNumber);
    }
}
