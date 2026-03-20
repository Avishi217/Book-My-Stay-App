import java.util.*;

// Reservation class (represents a booking request)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType;
    }
}

// Booking Request Queue
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added: " + reservation);
    }

    // View all requests
    public void showRequests() {
        if (queue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }

        System.out.println("\nBooking Requests (FIFO Order):");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulating multiple guest requests
        bookingQueue.addRequest(new Reservation("Abhishek", "Deluxe"));
        bookingQueue.addRequest(new Reservation("Ravi", "Standard"));
        bookingQueue.addRequest(new Reservation("Priya", "Suite"));

        // Display queue (no allocation happens)
        bookingQueue.showRequests();
    }
}