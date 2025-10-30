import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/companydb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con.setAutoCommit(false); // enable transaction control
            boolean exit = false;

            while (!exit) {
                System.out.println("\n--- Product Management Menu ---");
                System.out.println("1. Add Product");
                System.out.println("2. View All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                try {
                    switch (choice) {
                        case 1 -> addProduct(con, sc);
                        case 2 -> viewProducts(con);
                        case 3 -> updateProduct(con, sc);
                        case 4 -> deleteProduct(con, sc);
                        case 5 -> exit = true;
                        default -> System.out.println("Invalid choice.");
                    }
                    con.commit(); // commit after each operation
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    con.rollback(); // rollback on error
                }
            }
            con.close();
            System.out.println("Program ended.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addProduct(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter ProductID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        String query = "INSERT INTO Product VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(4, qty);
            ps.executeUpdate();
            System.out.println("✅ Product added successfully!");
        }
    }

    private static void viewProducts(Connection con) throws SQLException {
        String query = "SELECT * FROM Product";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            System.out.println("\nProductID | ProductName | Price | Quantity");
            System.out.println("--------------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " | " + rs.getString(2)
                        + " | " + rs.getDouble(3) + " | " + rs.getInt(4));
            }
        }
    }

    private static void updateProduct(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter ProductID to update: ");
        int id = sc.nextInt();
        System.out.print("Enter new Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter new Quantity: ");
        int qty = sc.nextInt();

        String query = "UPDATE Product SET Price=?, Quantity=? WHERE ProductID=?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, price);
            ps.setInt(2, qty);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("✅ Product updated successfully!");
            else System.out.println("⚠️ Product not found.");
        }
    }

    private static void deleteProduct(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter ProductID to delete: ");
        int id = sc.nextInt();

        String query = "DELETE FROM Product WHERE ProductID=?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("✅ Product deleted successfully!");
            else System.out.println("⚠️ Product not found.");
        }
    }
}
