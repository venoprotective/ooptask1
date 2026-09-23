package library;

public class Shelf {
    private final String name;
    private Cabinet cabinet;

    public Shelf(String name) {
        this.name = name;
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
}