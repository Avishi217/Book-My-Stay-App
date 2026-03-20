import java.util.*;

// Service class (Add-On)
class Service {
    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + price + ")";
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: ReservationID -> List of Services
    private Map<String, List<Service>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added " + service + " to Reservation: " + reservationId);
    }

    // Show services for a reservation
    public void showServices(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services for Reservation: " + reservationId);
            return;
        }

        System.out.println("\nServices for " + reservationId + ":");
        for (Service s : services) {
            System.out.println("- " + s);
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        List<Service> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;
        for (Service s : services) {
            total += s.getPrice();
        }
        return total;
    }
}

// Main Class
public class BookMyStayApp {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Example reservation IDs (from previous use case)
        String res1 = "ROOM-101";
        String res2 = "ROOM-102";

        // Add services
        manager.addService(res1, new Service("Breakfast", 200));
        manager.addService(res1, new Service("Spa", 500));
        manager.addService(res2, new Service("Airport Pickup", 300));

        // Show services
        manager.showServices(res1);
        manager.showServices(res2);

        // Show total cost
        System.out.println("\nTotal Add-On Cost for " + res1 + " = ₹"
                + manager.calculateTotalCost(res1));

        System.out.println("Total Add-On Cost for " + res2 + " = ₹"
                + manager.calculateTotalCost(res2));
    }
}