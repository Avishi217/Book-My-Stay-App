import java.util.*;
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return reservationId + " - " + guestName + " (" + roomType + ")";
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Standard", 1);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    public void increaseRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void showInventory() {
        System.out.println("\nInventory Status:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// Booking History
class BookingHistory {
    private Map<String, Reservation> bookings = new HashMap<>();

    public void add(Reservation r) {
        bookings.put(r.getReservationId(), r);
    }

    public Reservation get(String id) {
        return bookings.get(id);
    }

    public void remove(String id) {
        bookings.remove(id);
    }

    public boolean exists(String id) {
        return bookings.containsKey(id);
    }

    public void showAll() {
        System.out.println("\nCurrent Bookings:");
        for (Reservation r : bookings.values()) {
            System.out.println(r);
        }
    }
}

class CancellationService {

    private BookingHistory history;
    private InventoryService inventory;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(BookingHistory history, InventoryService inventory) {
        this.history = history;
        this.inventory = inventory;
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nCancelling: " + reservationId);

        if (!history.exists(reservationId)) {
            System.out.println("Cancellation Failed! Invalid Reservation ID.");
            return;
        }

        Reservation r = history.get(reservationId);

        rollbackStack.push(reservationId);

        inventory.increaseRoom(r.getRoomType());

        history.remove(reservationId);

        System.out.println("Cancellation Successful! Room released: " + reservationId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recent cancellations): " + rollbackStack);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        InventoryService inventory = new InventoryService();

        history.add(new Reservation("ROOM-101", "Abhishek", "Deluxe"));
        history.add(new Reservation("ROOM-102", "Ravi", "Standard"));

        history.showAll();

        CancellationService cancelService = new CancellationService(history, inventory);

        cancelService.cancelBooking("ROOM-101"); // valid
        cancelService.cancelBooking("ROOM-999"); // invalid

        history.showAll();
        inventory.showInventory();
        cancelService.showRollbackStack();
    }
}