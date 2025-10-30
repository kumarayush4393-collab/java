import java.util.*;
import java.util.stream.*;

class Student {
    private String name;
    private double marks;

    public Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }

    public String getName() { return name; }
    public double getMarks() { return marks; }
}

public class FilterStudents {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("Ayush", 85.5),
            new Student("Ritika", 68.0),
            new Student("Sohan", 91.2),
            new Student("Meena", 73.4),
            new Student("Karan", 88.9)
        );

        System.out.println("Students scoring above 75% (sorted by marks):");

        students.stream()
            .filter(s -> s.getMarks() > 75)  // filter
            .sorted(Comparator.comparingDouble(Student::getMarks).reversed()) // sort descending
            .map(Student::getName)           // get only names
            .forEach(System.out::println);   // display names
    }
}
