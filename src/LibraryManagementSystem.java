import java.util.*;

// Book Class
class Book {
    private int bookID;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(int bookID, String title, String author) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public int getBookID() { return bookID; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) { this.isAvailable = available; }

    public void displayBookInfo() {
        System.out.println("Book ID: " + bookID + " | Title: " + title + " | Author: " + author +
                " | Status: " + (isAvailable ? "Available" : "Borrowed"));
    }
}

//  Member Class
class Member {
    private int memberID;
    private String name;
    private String contactNumber;
    private List<Book> borrowedBooks;

    public Member(int memberID, String name, String contactNumber) {
        this.memberID = memberID;
        this.name = name;
        this.contactNumber = contactNumber;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getMemberID() { return memberID; }
    public String getName() { return name; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    public void displayMemberInfo() {
        System.out.println("Member ID: " + memberID + " | Name: " + name + " | Contact: " + contactNumber);
        if (borrowedBooks.isEmpty()) {
            System.out.println("  No borrowed books.");
        } else {
            System.out.println("  Borrowed Books:");
            for (Book b : borrowedBooks) {
                System.out.println("    - " + b.getTitle());
            }
        }
    }
}

//  Librarian Class
class Librarian {
    private int librarianID;
    private String name;

    public Librarian(int librarianID, String name) {
        this.librarianID = librarianID;
        this.name = name;
    }

    public void issueBook(Member member, Book book) {
        if (book.isAvailable()) {
            member.borrowBook(book);
            book.setAvailable(false);
            System.out.println("✅ Book '" + book.getTitle() + "' issued to " + member.getName());
        } else {
            System.out.println("❌ Book '" + book.getTitle() + "' is already borrowed.");
        }
    }

    public void receiveBook(Member member, Book book) {
        if (member.getBorrowedBooks().contains(book)) {
            member.returnBook(book);
            book.setAvailable(true);
            System.out.println("✅ Book '" + book.getTitle() + "' returned successfully by " + member.getName());
        } else {
            System.out.println("❌ This member did not borrow '" + book.getTitle() + "'");
        }
    }

    public void viewAllBooks(List<Book> books) {
        for (Book b : books) {
            b.displayBookInfo();
        }
    }

    public void viewAllMembers(List<Member> members) {
        for (Member m : members) {
            m.displayMemberInfo();
            System.out.println("-----------------------------");
        }
    }
}

//  Main System
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Sample data
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "The Great Gatsby", "F. Scott Fitzgerald"));
        books.add(new Book(2, "1984", "George Orwell"));
        books.add(new Book(3, "To Kill a Mockingbird", "Harper Lee"));

        List<Member> members = new ArrayList<>();
        members.add(new Member(1, "Jithmi", "0712345678"));
        members.add(new Member(2, "Amal", "0778765432"));

        Librarian librarian = new Librarian(1, "Nimal");

        int choice;
        do {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. View All Books");
            System.out.println("2. View All Members");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    librarian.viewAllBooks(books);
                    break;

                case 2:
                    librarian.viewAllMembers(members);
                    break;

                case 3:
                    System.out.print("Enter Member ID: ");
                    int memID = input.nextInt();
                    Member selectedMember = null;
                    for (Member m : members) {
                        if (m.getMemberID() == memID) selectedMember = m;
                    }

                    System.out.print("Enter Book ID to issue: ");
                    int bookID = input.nextInt();
                    Book selectedBook = null;
                    for (Book b : books) {
                        if (b.getBookID() == bookID) selectedBook = b;
                    }

                    if (selectedMember != null && selectedBook != null) {
                        librarian.issueBook(selectedMember, selectedBook);
                    } else {
                        System.out.println("❌ Invalid Member ID or Book ID.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Member ID: ");
                    int memID2 = input.nextInt();
                    Member returningMember = null;
                    for (Member m : members) {
                        if (m.getMemberID() == memID2) returningMember = m;
                    }

                    System.out.print("Enter Book ID to return: ");
                    int bookID2 = input.nextInt();
                    Book returningBook = null;
                    for (Book b : books) {
                        if (b.getBookID() == bookID2) returningBook = b;
                    }

                    if (returningMember != null && returningBook != null) {
                        librarian.receiveBook(returningMember, returningBook);
                    } else {
                        System.out.println("❌ Invalid Member ID or Book ID.");
                    }
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
