import java.util.HashMap;
import java.util.Map;

// Abstract Room class
abstract class Room {
    private String roomType;
    private double price;

    public Room(String roomType, double price) {
        this.roomType = roomType;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public abstract void displayDetails();
}

// Room implementations
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1000.0);
    }

    public void displayDetails() {
        System.out.println("Single Room - Price: ₹" + getPrice());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2000.0);
    }

    public void displayDetails() {
        System.out.println("Double Room - Price: ₹" + getPrice());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 5000.0);
    }

    public void displayDetails() {
        System.out.println("Suite Room - Price: ₹" + getPrice());
    }
}

// Inventory (read-only usage here)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // unavailable
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllRooms() {
        return inventory;
    }
}

// Search Service
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("---- Available Rooms ----");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());

            // Show only available rooms
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// Main class (STRICT FORMAT)
public class BookMyStayApp {

    public static void main(String[] args) {

        // Inventory (state holder)
        RoomInventory inventory = new RoomInventory();

        // Room domain objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Search service (read-only)
        RoomSearchService searchService = new RoomSearchService();

        // Perform search
        searchService.searchAvailableRooms(inventory, rooms);
    }
}