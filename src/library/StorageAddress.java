package library;

public class StorageAddress {
    Room room;
    Cabinet cabinet;
    Shelf shelf;

    public String toString(){
        return room.getName()  + " " + cabinet.getName() + " " + shelf.getName();
    }
}
