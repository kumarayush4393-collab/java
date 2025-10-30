import java.util.*;

class Employee {
    private String name;
    private int age;
    private double salary;

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return name + " | Age: " + age + " | Salary: " + salary;
    }
}

public class SortEmployees {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Aarav", 25, 50000),
            new Employee("Isha", 30, 70000),
            new Employee("Rahul", 22, 45000),
            new Employee("Neha", 28, 60000)
        );

        // Sort by salary (ascending)
        employees.sort((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));

        System.out.println("Employees sorted by salary:");
        employees.forEach(System.out::println);

        // Sort by name (alphabetically)
        employees.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));

        System.out.println("\nEmployees sorted by name:");
        employees.forEach(System.out::println);
    }
}
