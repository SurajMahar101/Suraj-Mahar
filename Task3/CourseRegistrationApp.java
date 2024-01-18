import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }
}

class Student {
    int studentID;
    String name;

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }
}

class Registration {
    int registrationID;
    int studentID;
    String courseCode;

    public Registration(int registrationID, int studentID, String courseCode) {
        this.registrationID = registrationID;
        this.studentID = studentID;
        this.courseCode = courseCode;
    }
}

class CourseRegistrationSystem {
    List<Course> courses = new ArrayList<>();
    List<Student> students = new ArrayList<>();
    List<Registration> registrations = new ArrayList<>();

    public void displayCourseListing() {
        System.out.println("Course Listing:");
        for (Course course : courses) {
            System.out.println("Course Code: " + course.courseCode);
            System.out.println("Title: " + course.title);
            System.out.println("Description: " + course.description);
            System.out.println("Capacity: " + course.capacity);
            System.out.println("Schedule: " + course.schedule);
            System.out.println();
        }
    }

    public void registerStudentForCourse(int studentID, String courseCode) {
        // Check if the course has available slots
        Course selectedCourse = courses.stream().filter(c -> c.courseCode.equals(courseCode)).findFirst().orElse(null);
        if (selectedCourse != null && selectedCourse.capacity > 0) {
            // Register the student for the course
            registrations.add(new Registration(registrations.size() + 1, studentID, courseCode));
            selectedCourse.capacity--;
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Course is full or does not exist.");
        }
    }

    public void removeStudentFromCourse(int studentID, String courseCode) {
        // Remove the registration entry
        registrations.removeIf(registration -> registration.studentID == studentID && registration.courseCode.equals(courseCode));

        // Increase the course capacity
        Course selectedCourse = courses.stream().filter(c -> c.courseCode.equals(courseCode)).findFirst().orElse(null);
        if (selectedCourse != null) {
            selectedCourse.capacity++;
            System.out.println("Course removal successful!");
        } else {
            System.out.println("Course removal failed. Course not found.");
        }
    }

    public void displayRegisteredCourses(int studentID) {
        System.out.println("Registered Courses for Student ID " + studentID + ":");
        for (Registration registration : registrations) {
            if (registration.studentID == studentID) {
                Course registeredCourse = courses.stream().filter(c -> c.courseCode.equals(registration.courseCode)).findFirst().orElse(null);
                if (registeredCourse != null) {
                    System.out.println("Course Code: " + registeredCourse.courseCode);
                    System.out.println("Title: " + registeredCourse.title);
                    System.out.println("Schedule: " + registeredCourse.schedule);
                    System.out.println();
                }
            }
        }
    }
}

public class CourseRegistrationApp {
    public static void main(String[] args) {
        CourseRegistrationSystem courseSystem = new CourseRegistrationSystem();

        // Adding sample courses and students
        courseSystem.courses.add(new Course("CS101", "Introduction to Computer Science", "Basic concepts of programming", 30, "Mon/Wed 9:00 AM"));
        courseSystem.courses.add(new Course("MATH201", "Calculus I", "Limits, derivatives, and integrals", 25, "Tue/Thu 11:00 AM"));
        courseSystem.courses.add(new Course("ENG101", "English Composition", "Writing skills and literature", 20, "Mon/Fri 2:00 PM"));

        courseSystem.students.add(new Student(1, "John Doe"));
        courseSystem.students.add(new Student(2, "Jane Smith"));

        Scanner scanner = new Scanner(System.in);

        // Display course listing
        courseSystem.displayCourseListing();

        // Student registration
        System.out.println("Enter student ID:");
        int studentID = scanner.nextInt();

        System.out.println("Enter the course code you want to register for:");
        scanner.nextLine();  // Consume the newline character
        String courseCode = scanner.nextLine();

        courseSystem.registerStudentForCourse(studentID, courseCode);

        // Display registered courses for the student
        courseSystem.displayRegisteredCourses(studentID);

        // Close the scanner
        scanner.close();
    }
}
