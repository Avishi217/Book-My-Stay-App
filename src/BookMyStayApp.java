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

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "ID: " + reservationId + ", Guest: " + guestName + ", Room: " + roomType;
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Get all bookings
    public List<Reservation> getAllBookings() {
        return history;
    }
}

// Report Service
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> bookings) {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : bookings) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> bookings) {
        System.out.println("\n--- Booking Summary ---");

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : bookings) {
            roomCount.put(r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1);
        }

        for (String type : roomCount.keySet()) {
            System.out.println(type + " Rooms Booked: " + roomCount.get(type));
        }

        System.out.println("Total Bookings: " + bookings.size());
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        // Step 1: Booking History
        BookingHistory history = new BookingHistory();

        // Step 2: Add confirmed bookings (simulating Use Case 6 output)
        history.addReservation(new Reservation("ROOM-101", "Abhishek", "Deluxe"));
        history.addReservation(new Reservation("ROOM-102", "Ravi", "Standard"));
        history.addReservation(new Reservation("ROOM-103", "Priya", "Suite"));
        history.addReservation(new Reservation("ROOM-104", "Kiran", "Deluxe"));

        // Step 3: Report Service
        BookingReportService reportService = new BookingReportService();

        // Step 4: Show history
        reportService.showAllBookings(history.getAllBookings());

        // Step 5: Generate summary
        reportService.generateSummary(history.getAllBookings());
    }
}