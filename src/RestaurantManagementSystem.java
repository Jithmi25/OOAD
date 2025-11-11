import java.util.*;

// MenuItem Class
class MenuItem {
    private String itemName;
    private double price;
    private String category;

    public MenuItem(String itemName, double price, String category) {
        this.itemName = itemName;
        this.price = price;
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return itemName + " (" + category + ") - Rs." + price;
    }
}

// Customer Class
class Customer {
    private String name;
    private String contact;

    public Customer(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }
}

//  Order Class
class Order {
    private Customer customer;
    private List<MenuItem> items;
    private boolean isCompleted;

    public Order(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
        this.isCompleted = false;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public void completeOrder() {
        isCompleted = true;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void displayOrderDetails() {
        System.out.println("Order for: " + customer.getName());
        for (MenuItem item : items) {
            System.out.println(" - " + item);
        }
        System.out.println("Total: Rs." + calculateTotal());
    }
}

// Waiter Class
class Waiter {
    private String name;
    private int id;

    public Waiter(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void takeOrder(Order order) {
        System.out.println("Waiter " + name + " is taking order for " + order);
    }

    public void serveOrder(Order order) {
        if (order.isCompleted()) {
            System.out.println("Waiter " + name + " served the completed order.");
        } else {
            System.out.println("Order not ready yet!");
        }
    }
}

// Main Class
public class RestaurantManagementSystem {
    public static void main(String[] args) {
        // Create Menu Items
        MenuItem item1 = new MenuItem("Burger", 550, "Main Course");
        MenuItem item2 = new MenuItem("Cola", 200, "Drink");
        MenuItem item3 = new MenuItem("Fries", 350, "Snack");

        // Create Customer
        Customer customer1 = new Customer("Jithmi", "0712345678");

        // Create Waiter
        Waiter waiter1 = new Waiter("Nimal", 1);

        // Create Order
        Order order1 = new Order(customer1);
        order1.addItem(item1);
        order1.addItem(item2);
        order1.addItem(item3);

        // Display Order Details
        order1.displayOrderDetails();

        // Complete and Serve Order
        order1.completeOrder();
        waiter1.serveOrder(order1);
    }
}
