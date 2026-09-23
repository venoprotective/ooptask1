package library;

public class Main {
    public static void main(String[] args) {
        Room room1 = new Room("Комната1");
        Room room2 = new Room("Комната2");

        Cabinet cabinet1 = new Cabinet("Шкаф1");
        room1.addCabinet(cabinet1);

        Shelf shelf1 = new Shelf("Полка1");
        cabinet1.addShelf(shelf1);

        StorageAddress address = new StorageAddress(shelf1);

        System.out.println(address);
        // Комната1 -> Шкаф1 -> Полка1
        System.out.println(shelf1.getCabinet().getName());
        System.out.println(shelf1.getCabinet().getRoom().getName());

        room2.addCabinet(cabinet1);

        System.out.println(address);
        System.out.println(shelf1.getCabinet().getName());
        System.out.println(shelf1.getCabinet().getRoom().getName());
        // Комната2 -> Шкаф 1 -> Полка 1
    }
}