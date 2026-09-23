package library;

import java.util.ArrayList;
import java.util.List;

public class Cabinet {
    private final String name;
    private final List<Shelf> shelves;

    public Cabinet(String name) {
        this.name = name;
        this.shelves = new ArrayList<Shelf>();
    }
}
