package library;

public class StorageAddress {
    private final Shelf shelf;

    public StorageAddress(Shelf shelf) {
        this.shelf = shelf;
    }

    public Room getRoom() {
        if (shelf.getCabinet() == null ||
                shelf.getCabinet().getRoom() == null) {
            throw new IllegalArgumentException("не суещствует шкаф или не существует комната или и шкаф и комната");
        }

        return shelf.getCabinet().getRoom();    
    }

    public Cabinet getCabinet() {
        if (shelf.getCabinet() == null) {
            throw new IllegalArgumentException("не существует шкаф");
        }

        return shelf.getCabinet();
    }

    public Shelf getShelf() {
        return shelf;
    }

    @Override
    public String toString() {
        return getRoom().getName() + " " + getCabinet().getName() + " " + shelf.getName();
    }
}