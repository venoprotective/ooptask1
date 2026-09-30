package library;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    private final String name;
    private Cabinet cabinet;
    private final List<PhysicalBook> bookCopies;

    public Shelf(String name) {
        this.name = name;
        this.bookCopies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    public List<PhysicalBook> getBookCopies() {
        return List.copyOf(bookCopies);
    }

    public void addBookCopy(PhysicalBook copy) {
        if (copy.getShelf() != null) {
            copy.getShelf().removeBookCopy(copy);
        }

        bookCopies.add(copy);
        copy.setShelf(this);
    }

    public void removeBookCopy(PhysicalBook copy) {
        if (bookCopies.remove(copy)) {
            copy.setShelf(null);
        }
    }
}