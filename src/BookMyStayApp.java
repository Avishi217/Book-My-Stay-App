import java.util.*;
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void reduceRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void showInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// Booking Service
class BookingService {
    private Queue<Reservation> queue;
    private InventoryService inventoryService;

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomTypeMap = new HashMap<>();

    private int roomCounter = 101;

    public BookingService(Queue<Reservation> queue, InventoryService inventoryService) {
        this.queue = queue;
        this.inventoryService = inventoryService;
    }

    // Generate unique room ID
    private String generateRoomId() {
        String roomId;
        do {
            roomId = "ROOM-" + roomCounter++;
        } while (allocatedRoomIds.contains(roomId));
        return roomId;
    }

    // Process booking requests
    public void processBookings() {
        while (!queue.isEmpty()) {
            Reservation r = queue.poll();
            System.out.println("\nProcessing: " + r.guestName + " (" + r.roomType + ")");

            if (inventoryService.isAvailable(r.roomType)) {
                String roomId = generateRoomId();

                // Add to set (uniqueness)
                allocatedRoomIds.add(roomId);

                // Map room type to allocated rooms
                roomTypeMap.putIfAbsent(r.roomType, new HashSet<>());
                roomTypeMap.get(r.roomType).add(roomId);

                // Update inventory
                inventoryService.reduceRoom(r.roomType);

                System.out.println("Booking Confirmed! Room ID: " + roomId);
            } else {
                System.out.println("Booking Failed! No rooms available.");
            }
        }
    }
}

// Main App
public class BookMyStayApp {
    public static void main(String[] args) {

        // Step 1: Create booking request queue
        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Abhishek", "Deluxe"));
        bookingQueue.add(new Reservation("Ravi", "Standard"));
        bookingQueue.add(new Reservation("Priya", "Suite"));
        bookingQueue.add(new Reservation("Kiran", "Suite")); // extra test (no room)

        // Step 2: Inventory
        InventoryService inventory = new InventoryService();

        // Step 3: Booking Service
        BookingService bookingService = new BookingService(bookingQueue, inventory);

        // Step 4: Process bookings (FIFO)
        bookingService.processBookings();

        // Step 5: Show remaining inventory
        inventory.showInventory();
    }
}