import java.util.*;
import java.util.stream.*;

class Product {
    private String name;
    private String category;
    private double price;

    public Product(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
}

public class ProcessProducts {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
            new Product("Laptop", "Electronics", 85000),
            new Product("Smartphone", "Electronics", 55000),
            new Product("Table", "Furniture", 15000),
            new Product("Chair", "Furniture", 7000),
            new Product("Washing Machine", "Appliances", 30000),
            new Product("Refrigerator", "Appliances", 45000)
        );

        // Group by category
        Map<String, List<Product>> productsByCategory = products.stream()
            .collect(Collectors.groupingBy(Product::getCategory));

        System.out.println("Products grouped by category:");
        productsByCategory.forEach((category, list) -> {
            System.out.println(category + ": " +
                list.stream().map(Product::getName).collect(Collectors.joining(", ")));
        });

        // Find most expensive product in each category
        System.out.println("\nMost expensive product in each category:");
        Map<String, Optional<Product>> maxPriceByCategory = products.stream()
            .collect(Collectors.groupingBy(
                Product::getCategory,
                Collectors.maxBy(Comparator.comparingDouble(Product::getPrice))
            ));

        maxPriceByCategory.forEach((category, productOpt) ->
            System.out.println(category + ": " + productOpt.get().getName() +
                " (" + productOpt.get().getPrice() + ")")
        );

        // Calculate average price of all products
        double avgPrice = products.stream()
            .collect(Collectors.averagingDouble(Product::getPrice));

        System.out.println("\nAverage price of all products: " + avgPrice);
    }
}
