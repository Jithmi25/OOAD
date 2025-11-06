import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Room Class
class Room {
    private int roomNumber;
    private String roomType;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
    }

    // Getters
    public int getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isAvailable() { return isAvailable; }

    // Setters
    public void setAvailable(boolean available) { isAvailable = available; }

    public void displayRoomInfo() {
        System.out.println("Room " + roomNumber + " (" + roomType + ") - Rs." + pricePerNight + " per night - " +
                (isAvailable ? "Available" : "Occupied"));
    }
}

//Guest Class
class Guest {
    private int guestID;
    private String name;
    private String contactNumber;

    public Guest(int guestID, String name, String contactNumber) {
        this.guestID = guestID;
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public int getGuestID() { return guestID; }
    public String getName() { return name; }
    public String getContactNumber() { return contactNumber; }

    public void displayGuestInfo() {
        System.out.println("Guest ID: " + guestID + ", Name: " + name + ", Contact: " + contactNumber);
    }
}

// Reservation Class
class Reservation {
    private int reservationID;
    private Guest guest;
    private Room room;
    private int nights;
    private double totalAmount;
    private boolean isActive;

    public Reservation(int reservationID, Guest guest, Room room, int nights) {
        this.reservationID = reservationID;
        this.guest = guest;
        this.room = room;
        this.nights = nights;
        this.totalAmount = room.getPricePerNight() * nights;
        this.isActive = true;
        room.setAvailable(false);
    }

    public int getReservationID() { return reservationID; }
    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public double getTotalAmount() { return totalAmount; }
    public boolean isActive() { return isActive; }

    public void cancelReservation() {
        if (isActive) {
            isActive = false;
            room.setAvailable(true);
            System.out.println("Reservation " + reservationID + " cancelled successfully.");
        } else {
            System.out.println("Reservation already inactive.");
        }
    }

    public void displayReservationInfo() {
        System.out.println("Reservation ID: " + reservationID +
                " | Guest: " + guest.getName() +
                " | Room: " + room.getRoomNumber() +
                " | Nights: " + nights +
                " | Total: Rs." + totalAmount +
                " | Status: " + (isActive ? "Active" : "Cancelled"));
    }
}

//  Receptionist Class
class Receptionist {
    private int receptionistID;
    private String name;
    private List<Reservation> reservations;

    public Receptionist(int receptionistID, String name) {
        this.receptionistID = receptionistID;
        this.name = name;
        this.reservations = new ArrayList<>();
    }

    public void checkInGuest(Guest guest, Room room, int nights) {
        if (room.isAvailable()) {
            Reservation reservation = new Reservation(reservations.size() + 1, guest, room, nights);
            reservations.add(reservation);
            System.out.println("✅ Check-in successful! Reservation ID: " + reservation.getReservationID());
        } else {
            System.out.println("❌ Room " + room.getRoomNumber() + " is not available.");
        }
    }

    public void checkOutGuest(int reservationID) {
        for (Reservation res : reservations) {
            if (res.getReservationID() == reservationID && res.isActive()) {
                res.cancelReservation();
                System.out.println("✅ Guest checked out successfully.");
                return;
            }
        }
        System.out.println("❌ Reservation not found or already inactive.");
    }

    public void showAllReservations() {
        for (Reservation res : reservations) {
            res.displayReservationInfo();
        }
    }
}

//  Main System
public class HotelManagementSystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Sample Data
        Room r1 = new Room(101, "Single", 5000);
        Room r2 = new Room(102, "Double", 8000);
        Room r3 = new Room(103, "Suite", 12000);

        Guest g1 = new Guest(1, "Kavindu", "0771234567");
        Guest g2 = new Guest(2, "Kamal", "0719876543");

        Receptionist rec = new Receptionist(1, "Amal");

        // Menu
        int choice;
        do {
            System.out.println("\n=== HOTEL MANAGEMENT SYSTEM ===");
            System.out.println("1. View Room Details");
            System.out.println("2. Check-In Guest");
            System.out.println("3. Check-Out Guest");
            System.out.println("4. View All Reservations");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    r1.displayRoomInfo();
                    r2.displayRoomInfo();
                    r3.displayRoomInfo();
                    break;

                case 2:
                    System.out.print("Enter guest ID (1 or 2): ");
                    int gid = input.nextInt();
                    Guest guest = (gid == 1) ? g1 : g2;

                    System.out.print("Enter room number (101-103): ");
                    int roomNum = input.nextInt();
                    Room selectedRoom = null;
                    if (roomNum == 101) selectedRoom = r1;
                    else if (roomNum == 102) selectedRoom = r2;
                    else if (roomNum == 103) selectedRoom = r3;

                    System.out.print("Enter number of nights: ");
                    int nights = input.nextInt();

                    if (selectedRoom != null) rec.checkInGuest(guest, selectedRoom, nights);
                    break;

                case 3:
                    System.out.print("Enter reservation ID to check out: ");
                    int rid = input.nextInt();
                    rec.checkOutGuest(rid);
                    break;

                case 4:
                    rec.showAllReservations();
                    break;

                case 0:
                    System.out.println("Exiting system... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (choice != 0);

        input.close();
    }
}

