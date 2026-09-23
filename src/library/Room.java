package library;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final String name;
    private final List<Cabinet> cabinets;

    public Room(String name) {
        this.name = name;
        this.cabinets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Cabinet> getCabinets() {
        return List.copyOf(cabinets);
    }

    public void addCabinet(Cabinet cabinet) {
        if (cabinet.getRoom() != null) {
            cabinet.getRoom().removeCabinet(cabinet);
        }

        cabinets.add(cabinet);
        cabinet.setRoom(this);
    }

    public void removeCabinet(Cabinet cabinet) {
        cabinets.remove(cabinet);
        cabinet.setRoom(null);
    }
}