package library;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final String name;
    private List<Cabinet> cabinets;

    public Room(String name) {
        this.name = name;
        this.cabinets = new ArrayList<Cabinet>();
    }

    public String getName() {
        return name;
    }
}
