import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;

// Room class
class Room {
    private int roomNumber;
    private String category;
    private boolean isAvailable;

    public Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

// Reservation class
class Reservation {
    private String guestName;
    private int roomNumber;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(String guestName, int roomNumber, Date checkInDate, Date checkOutDate) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Reservation{" +
                "guestName='" + guestName + '\'' +
                ", roomNumber=" + roomNumber +
                ", checkInDate=" + sdf.format(checkInDate) +
                ", checkOutDate=" + sdf.format(checkOutDate) +
                '}';
    }
}

// Hotel class
class Hotel {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        // Adding some rooms
        rooms.add(new Room(101, "Single"));
        rooms.add(new Room(102, "Double"));
        rooms.add(new Room(103, "Suite"));
        rooms.add(new Room(104, "Single"));
        rooms.add(new Room(105, "Double"));
    }

    public List<Room> getAvailableRooms(String category) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category) && room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public void makeReservation(String guestName, int roomNumber, Date checkInDate, Date checkOutDate) {
        Room room = getRoomByNumber(roomNumber);
        if (room != null && room.isAvailable()) {
            room.setAvailable(false);
            reservations.add(new Reservation(guestName, roomNumber, checkInDate, checkOutDate));
            System.out.println("Reservation successful.");
        } else {
            System.out.println("Room is not available.");
        }
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    private Room getRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }
}

// HotelReservationSystem class
public class HotelReservationSystem {
    private static Hotel hotel = new Hotel();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        while (true) {
            System.out.println("\n1. Search for available rooms");
            System.out.println("2. Make a reservation");
            System.out.println("3. View reservations");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    searchForAvailableRooms(scanner);
                    break;
                case 2:
                    makeReservation(scanner, sdf);
                    break;
                case 3:
                    viewReservations();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void searchForAvailableRooms(Scanner scanner) {
        System.out.print("Enter room category (Single/Double/Suite): ");
        String category = scanner.next();
        List<Room> availableRooms = hotel.getAvailableRooms(category);
        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms in this category.");
        } else {
            System.out.println("Available rooms:");
            for (Room room : availableRooms) {
                System.out.println("Room number: " + room.getRoomNumber());
            }
        }
    }

    private static void makeReservation(Scanner scanner, SimpleDateFormat sdf) {
        try {
            System.out.print("Enter your name: ");
            String name = scanner.next();
            System.out.print("Enter room number: ");
            int roomNumber = scanner.nextInt();
            System.out.print("Enter check-in date (dd/MM/yyyy): ");
            Date checkInDate = sdf.parse(scanner.next());
            System.out.print("Enter check-out date (dd/MM/yyyy): ");
            Date checkOutDate = sdf.parse(scanner.next());
            hotel.makeReservation(name, roomNumber, checkInDate, checkOutDate);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
        }
    }

    private static void viewReservations() {
        List<Reservation> reservations = hotel.getReservations();
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            System.out.println("Reservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }
}
