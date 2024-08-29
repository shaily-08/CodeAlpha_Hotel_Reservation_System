import java.util.*;

class Room {
    private int roomNumber;
    private String category;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", category='" + category + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

class Reservation {
    private static int reservationCounter = 1;
    private int reservationId;
    private Room room;
    private String guestName;
    private int numberOfNights;
    private double totalAmount;
    private boolean isPaid;

    public Reservation(Room room, String guestName, int numberOfNights) {
        this.reservationId = reservationCounter++;
        this.room = room;
        this.guestName = guestName;
        this.numberOfNights = numberOfNights;
        this.totalAmount = room.getPricePerNight() * numberOfNights;
        this.isPaid = false;
        room.setAvailable(false);  // Set room as unavailable once reserved
    }

    public int getReservationId() {
        return reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", room=" + room +
                ", guestName='" + guestName + '\'' +
                ", numberOfNights=" + numberOfNights +
                ", totalAmount=" + totalAmount +
                ", isPaid=" + isPaid +
                '}';
    }
}

class Hotel {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        initializeRooms();
    }

    private void initializeRooms() {
        rooms.add(new Room(101, "Single", 100.00));
        rooms.add(new Room(102, "Double", 150.00));
        rooms.add(new Room(103, "Suite", 300.00));
        rooms.add(new Room(104, "Single", 100.00));
        rooms.add(new Room(105, "Suite", 300.00));
    }

    public void displayAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    public Room findAvailableRoom(String category) {
        for (Room room : rooms) {
            if (room.isAvailable() && room.getCategory().equalsIgnoreCase(category)) {
                return room;
            }
        }
        return null;
    }

    public Reservation makeReservation(String guestName, String category, int numberOfNights) {
        Room availableRoom = findAvailableRoom(category);
        if (availableRoom != null) {
            Reservation reservation = new Reservation(availableRoom, guestName, numberOfNights);
            reservations.add(reservation);
            return reservation;
        } else {
            System.out.println("No available rooms in the category: " + category);
            return null;
        }
    }

    public void displayReservationDetails(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                System.out.println(reservation);
                return;
            }
        }
        System.out.println("Reservation not found with ID: " + reservationId);
    }

    public void processPayment(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                if (!reservation.isPaid()) {
                    reservation.setPaid(true);
                    System.out.println("Payment processed successfully for reservation ID: " + reservationId);
                } else {
                    System.out.println("Payment already made for this reservation.");
                }
                return;
            }
        }
        System.out.println("Reservation not found with ID: " + reservationId);
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("\nHotel Reservation System");
            System.out.println("1. Display Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Reservation Details");
            System.out.println("4. Process Payment");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            command = scanner.nextLine();

            switch (command) {
                case "1":
                    hotel.displayAvailableRooms();
                    break;
                case "2":
                    System.out.print("Enter your name: ");
                    String guestName = scanner.nextLine();
                    System.out.print("Enter room category (Single, Double, Suite): ");
                    String category = scanner.nextLine();
                    System.out.print("Enter number of nights: ");
                    int nights = Integer.parseInt(scanner.nextLine());
                    Reservation reservation = hotel.makeReservation(guestName, category, nights);
                    if (reservation != null) {
                        System.out.println("Reservation made successfully. Your reservation ID is " + reservation.getReservationId());
                    }
                    break;
                case "3":
                    System.out.print("Enter reservation ID: ");
                    int reservationId = Integer.parseInt(scanner.nextLine());
                    hotel.displayReservationDetails(reservationId);
                    break;
                case "4":
                    System.out.print("Enter reservation ID to process payment: ");
                    int paymentReservationId = Integer.parseInt(scanner.nextLine());
                    hotel.processPayment(paymentReservationId);
                    break;
                case "5":
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}