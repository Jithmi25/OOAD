import java.util.*;

// Movie Class
class Movie {
    private int movieID;
    private String title;
    private String genre;
    private double ticketPrice;
    private int availableSeats;

    public Movie(int movieID, String title, String genre, double ticketPrice, int availableSeats) {
        this.movieID = movieID;
        this.title = title;
        this.genre = genre;
        this.ticketPrice = ticketPrice;
        this.availableSeats = availableSeats;
    }

    public int getMovieID() { return movieID; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public double getTicketPrice() { return ticketPrice; }
    public int getAvailableSeats() { return availableSeats; }

    public void reduceSeats(int count) { availableSeats -= count; }
    public void increaseSeats(int count) { availableSeats += count; }

    public void displayMovieInfo() {
        System.out.println("Movie ID: " + movieID + " | Title: " + title + " | Genre: " + genre +
                " | Price: Rs." + ticketPrice + " | Available Seats: " + availableSeats);
    }
}

//  Customer Class
class Customer {
    private int customerID;
    private String name;
    private String contactNumber;
    private List<Ticket> tickets;

    public Customer(int customerID, String name, String contactNumber) {
        this.customerID = customerID;
        this.name = name;
        this.contactNumber = contactNumber;
        this.tickets = new ArrayList<>();
    }

    public int getCustomerID() { return customerID; }
    public String getName() { return name; }
    public List<Ticket> getTickets() { return tickets; }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void cancelTicket(Ticket ticket) {
        tickets.remove(ticket);
    }

    public void displayCustomerInfo() {
        System.out.println("Customer ID: " + customerID + " | Name: " + name + " | Contact: " + contactNumber);
        if (tickets.isEmpty()) {
            System.out.println("  No tickets booked.");
        } else {
            System.out.println("  Tickets:");
            for (Ticket t : tickets) {
                System.out.println("    - " + t.getMovie().getTitle() + " (" + t.getSeatCount() + " seats)");
            }
        }
    }
}

//  Ticket Class
class Ticket {
    private int ticketID;
    private Movie movie;
    private int seatCount;
    private double totalAmount;
    private boolean isActive;

    public Ticket(int ticketID, Movie movie, int seatCount) {
        this.ticketID = ticketID;
        this.movie = movie;
        this.seatCount = seatCount;
        this.totalAmount = movie.getTicketPrice() * seatCount;
        this.isActive = true;
    }

    public int getTicketID() { return ticketID; }
    public Movie getMovie() { return movie; }
    public int getSeatCount() { return seatCount; }
    public double getTotalAmount() { return totalAmount; }
    public boolean isActive() { return isActive; }

    public void cancelTicket() {
        isActive = false;
        movie.increaseSeats(seatCount);
    }

    public void displayTicketInfo() {
        System.out.println("Ticket ID: " + ticketID +
                " | Movie: " + movie.getTitle() +
                " | Seats: " + seatCount +
                " | Total: Rs." + totalAmount +
                " | Status: " + (isActive ? "Active" : "Cancelled"));
    }
}

// BookingManager (like Receptionist)
class BookingManager {
    private int managerID;
    private String name;
    private List<Ticket> allTickets;

    public BookingManager(int managerID, String name) {
        this.managerID = managerID;
        this.name = name;
        this.allTickets = new ArrayList<>();
    }

    public void bookTicket(Customer customer, Movie movie, int seats) {
        if (movie.getAvailableSeats() >= seats) {
            Ticket ticket = new Ticket(allTickets.size() + 1, movie, seats);
            customer.addTicket(ticket);
            allTickets.add(ticket);
            movie.reduceSeats(seats);
            System.out.println("✅ Ticket booked successfully for " + customer.getName() +
                    " | Movie: " + movie.getTitle() + " | Seats: " + seats);
        } else {
            System.out.println("❌ Not enough seats available for " + movie.getTitle());
        }
    }

    public void cancelTicket(Customer customer, int ticketID) {
        for (Ticket t : allTickets) {
            if (t.getTicketID() == ticketID && t.isActive()) {
                t.cancelTicket();
                customer.cancelTicket(t);
                System.out.println("✅ Ticket " + ticketID + " cancelled successfully.");
                return;
            }
        }
        System.out.println("❌ Ticket not found or already cancelled.");
    }

    public void viewAllMovies(List<Movie> movies) {
        for (Movie m : movies) {
            m.displayMovieInfo();
        }
    }

    public void viewAllCustomers(List<Customer> customers) {
        for (Customer c : customers) {
            c.displayCustomerInfo();
            System.out.println("------------------------------");
        }
    }

    public void viewAllTickets() {
        for (Ticket t : allTickets) {
            t.displayTicketInfo();
        }
    }
}

//  Main System
public class MovieTicketBookingSystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Sample Data
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "Avatar 2", "Sci-Fi", 1200, 10));
        movies.add(new Movie(2, "Inception", "Thriller", 1000, 8));
        movies.add(new Movie(3, "Inside Out 2", "Animation", 800, 12));

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "Jithmi", "0712345678"));
        customers.add(new Customer(2, "Amal", "0778765432"));

        BookingManager manager = new BookingManager(1, "Nimal");

        int choice;
        do {
            System.out.println("\n===== MOVIE TICKET BOOKING SYSTEM =====");
            System.out.println("1. View All Movies");
            System.out.println("2. View All Customers");
            System.out.println("3. Book Ticket");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. View All Tickets");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    manager.viewAllMovies(movies);
                    break;

                case 2:
                    manager.viewAllCustomers(customers);
                    break;

                case 3:
                    System.out.print("Enter Customer ID: ");
                    int custID = input.nextInt();
                    Customer selectedCustomer = null;
                    for (Customer c : customers) {
                        if (c.getCustomerID() == custID) selectedCustomer = c;
                    }

                    System.out.print("Enter Movie ID: ");
                    int movieID = input.nextInt();
                    Movie selectedMovie = null;
                    for (Movie m : movies) {
                        if (m.getMovieID() == movieID) selectedMovie = m;
                    }

                    System.out.print("Enter number of seats: ");
                    int seats = input.nextInt();

                    if (selectedCustomer != null && selectedMovie != null) {
                        manager.bookTicket(selectedCustomer, selectedMovie, seats);
                    } else {
                        System.out.println("❌ Invalid Customer ID or Movie ID.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Customer ID: ");
                    int custID2 = input.nextInt();
                    Customer returningCustomer = null;
                    for (Customer c : customers) {
                        if (c.getCustomerID() == custID2) returningCustomer = c;
                    }

                    System.out.print("Enter Ticket ID to cancel: ");
                    int ticketID = input.nextInt();

                    if (returningCustomer != null) {
                        manager.cancelTicket(returningCustomer, ticketID);
                    } else {
                        System.out.println("❌ Invalid Customer ID.");
                    }
                    break;

                case 5:
                    manager.viewAllTickets();
                    break;

                case 0:
                    System.out.println("Exiting system... Goodbye!");
                    break;

                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }

        } while (choice != 0);

        input.close();
    }
}
