package library;

import java.util.ArrayList;
import java.util.List;

public class Cabinet {
    private final String name;
    private final List<Shelf> shelves;
    private Room room;

    public Cabinet(String name) {
        this.name = name;
        this.shelves = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Room getRoom() {
        return room;
    }

    void setRoom(Room room) {
        this.room = room;
    }

    public List<Shelf> getShelves() {
        return List.copyOf(shelves);
    }

    public void addShelf(Shelf shelf) {
        if (shelf.getCabinet() != null) {
            shelf.getCabinet().removeShelf(shelf);
        }

        shelves.add(shelf);
        shelf.setCabinet(this);
    }

    public void removeShelf(Shelf shelf) {
        shelves.remove(shelf);
        shelf.setCabinet(null);
    }
}