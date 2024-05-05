import java.io.*;
import java.util.*;

class UserManager {
    private Map<String, User> users;

    public UserManager() {
        this.users = new HashMap<>();
    }

    public boolean registerUser(String username, String password, String name, String phone, String email, int age, String gender) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, password, name, phone, email, age, gender));
            System.out.println("User registered successfully.");
            return true;
        } else {
            System.out.println("Username already exists. Please choose a different username.");
            return false;
        }
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Invalid username or password. Please try again.");
            return false;
        }
    }

    public Map<String, User> getUsers() {
        return users;
    }
}

class User {
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private int age;
    private String gender;

    public User(String username, String password, String name, String phone, String email, int age, String gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}

class Course {
    private String courseCode;
    private String courseName;
    private int credits;


    public Course(String courseCode, String courseName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        
    }

    public String toString() {
        return "Course Code: " + courseCode + "\nCourse Name: " + courseName;
    }
    public String getCourseName() {
        return courseName;
    }
    public String getCourseCode() {
        return courseCode;
    }

}

class Faculty {
    private String facultyId;
    private String name;
    private List<String> assignedCourses;

    public Faculty(String facultyId, String name) {
        this.facultyId = facultyId;
        this.name = name;
        this.assignedCourses = new ArrayList<>();
    }

    public void assignCourse(String courseCode) {
        assignedCourses.add(courseCode);
    }

    public void unassignCourse(String courseCode) {
        assignedCourses.remove(courseCode);
    }

    public String toString() {
        return "Faculty ID: " + facultyId + "\nName: " + name ;
    }
    public String getName() {
        return name;
    }
    public String getFacultyId() {
        return facultyId;
    }
}

class Student {
    private String studentId;
    private String name;
    private String major;
    private List<String> courses;
    private Map<String, Integer> courseGrades; 
    private Map<String, Integer> courseAttendance;

    public Student(String studentId, String name, String major) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.courses = new ArrayList<>();
        this.courseGrades = new HashMap<>();
        this.courseAttendance = new HashMap<>();
    }

    public void enrollCourse(String courseCode) {
        courses.add(courseCode);
        courseGrades.put(courseCode, 0); // Initialize course grade to 0
        courseAttendance.put(courseCode, 0); // Initialize course attendance to 0
    }

    public void dropCourse(String courseCode) {
        courses.remove(courseCode);
        courseGrades.remove(courseCode);
        courseAttendance.remove(courseCode);
    }

    public void setGrade(String courseCode, int grade) {
        if (courseGrades.containsKey(courseCode)) {
            courseGrades.put(courseCode, grade);
        }
    }

    public double calculateGPA() {
        double totalPoints = 0;
        int totalCredits = 0;

        for (String course : courses) {
            int grade = courseGrades.get(course);
            int credits = 3; 
            totalPoints += (grade * credits);
            totalCredits += credits;
        }

        if (totalCredits == 0) {
            return 0.0; 
        }

        return totalPoints / totalCredits;
    }
    
    public void markAttendance(String courseCode) {
        if (courseAttendance.containsKey(courseCode)) {
            int currentAttendance = courseAttendance.get(courseCode);
            courseAttendance.put(courseCode, currentAttendance + 1);
        }
    }

    public int getAttendance(String courseCode) {
        return courseAttendance.getOrDefault(courseCode, 0);
    }

    public void saveToFile() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(studentId + ".txt"));
            writer.println("Student ID: " + studentId);
            writer.println("Name: " + name);
            writer.println("Major: " + major);
            writer.println("Courses: " + String.join(", ", courses));
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving student data to file: " + e.getMessage());
        }
    }

    public static Student loadFromFile(String studentId) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(studentId + ".txt"));
            String line;
            String name = null;
            String major = null;
            List<String> courses = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name: ")) {
                    name = line.substring("Name: ".length());
                } else if (line.startsWith("Major: ")) {
                    major = line.substring("Major: ".length());
                } else if (line.startsWith("Courses: ")) {
                    String coursesLine = line.substring("Courses: ".length());
                    String[] courseTokens = coursesLine.split(", ");
                    courses.addAll(Arrays.asList(courseTokens));
                }
            }

            if (name != null && major != null) {
                Student student = new Student(studentId, name, major);
                student.courses = courses;
                return student;
            } else {
                System.err.println("Invalid student data in file.");
            }
        } catch (IOException e) {
            System.err.println("Error loading student data from file: " + e.getMessage());
        }

        return null;
    }
    
    
   
    public String toString() {
        return "Student ID: " + studentId + "\nName: " + name + "\nMajor: " + major ;
    }

    public List<String> getCourses() {
        return courses;
    }
    public String getName() {
        return name;
    }
    public String getStudentId() {
        return studentId;
    }
    public String getMajor() {
        return major;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

}

class University {
    private Map<String, Course> courses;
    private Map<String, Faculty> faculties;
    private Map<String, Student> students;
    private UserManager userManager; 

    public University() {
        courses = new HashMap<>();
        faculties = new HashMap<>();
        students = new HashMap<>();
        userManager = new UserManager();
    }
 
    public UserManager getUserManager() {
        return userManager;
    }
    public void addCourse(String courseCode, String courseName) {
        if (!courses.containsKey(courseCode)) {
            Course course = new Course(courseCode, courseName);
            courses.put(courseCode, course);
        } else {
            System.out.println("Course with this code already exists.");
        }
    }

    
    public void listStudents() {
        for (Student student : students.values()) {
            System.out.println(student);  // Assuming Student class has overridden toString method
        }
    }

    public void listCourses() {
        for (Course course : courses.values()) {
            System.out.println(course);
        }
    }
    public void saveDataToFiles() {
        try {
            saveCoursesToFile();
            saveFacultiesToFile();
            saveStudentsToFile();
            System.out.println("Data saved to files successfully.");
        } catch (IOException e) {
            System.err.println("Error saving data to files: " + e.getMessage());
        }
    }
    
    private void saveCoursesToFile() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter("courses.txt"));
        for (Course course : courses.values()) {
            writer.write(course.getCourseCode() + "," + course.getCourseName() + "\n");
        }
        writer.close();
    }
    
    private void saveFacultiesToFile() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter("faculties.txt"));
        for (Faculty faculty : faculties.values()) {
            writer.write(faculty.getFacultyId() + "," + faculty.getName() + "\n");
        }
        writer.close();
    }
    
    private void saveStudentsToFile() throws IOException {
        for (Student student : students.values()) {
            String fileName = student.getStudentId() + ".txt";
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            writer.write("Student ID: " + student.getStudentId() + "\n");
            writer.write("Name: " + student.getName() + "\n");
            writer.write("Major: " + student.getMajor() + "\n");
            writer.write("Courses: " + String.join(", ", student.getCourses()) + "\n");
            writer.close();
        }
    }
    
    
   
    public Map<String, Course> getCourses() {
        return courses;
    }

    public Map<String, Faculty> getFaculties() {
        return faculties;
    }

    public Map<String, Student> getStudents() {
        return students;
    }

    public void listFaculties() {
        for (Faculty faculty : faculties.values()) {
            System.out.println(faculty);
        }
    }
 }

public class UniversityManagementSystem {
    private static University university;
    private static Scanner scanner;
    private static User currentUser;
    public static void main(String[] args) {
        university = new University();
        scanner = new Scanner(System.in);

        
         
        while (true) {
            if (currentUser == null) {
                
                System.out.println("\nUniversity Management System");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                String loginChoice = scanner.nextLine();

                switch (loginChoice) {
                    case "1":
                    performRegistration();
                        break;
                    case "2":
                    performLogin();
                        
                        break;
                    case "3":
                        System.out.println("Exiting the system. Goodbye!");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
               
                showMainMenu();
            }
        }
    }
    private static void performRegistration() {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String newPassword = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
    
        // Validate and get a valid phone number
        String phone = getValidPhoneNumber();
    
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter age: ");
    
        // Validate and get a valid age
        int age = getValidAge();
    
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter gender (Male/Female): ");
        String gender = getvalidGender();
        // Register the user with the validated information
        university.getUserManager().registerUser(newUsername, newPassword, name, phone, email, age, gender);
    }
    
    private static String getValidPhoneNumber() {
        while (true) {
            try {
                System.out.print("Enter phone number (10 digits): ");
                String phone = scanner.nextLine();
    
                // Check if the phone number contains only digits and has a length of 10
                if (isNumeric(phone) && phone.length() == 10) {
                    return phone;
                } else {
                    throw new InputMismatchException("Invalid phone number. Please enter 10 digits.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. " + e.getMessage());
               
            }
        }
    }
    
    
    private static int getValidAge() {
        while (true) {
            try {
                int age = scanner.nextInt();
    
                // Check if the age is a positive number
                if (age > 0) {
                    return age;
                } else {
                    throw new InputMismatchException("Invalid age. Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. " + e.getMessage());
                
            }
        }
    }
    

    private static void performLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (university.getUserManager().login(username, password)) {
            currentUser = university.getUserManager().getUsers().get(username);
        }
    }
    private static void showMainMenu() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\nUniversity Management System");
            System.out.println("1. Student Menu");
            System.out.println("2. Faculty Menu");
            System.out.println("3. Course Menu");
            System.out.println("4. Calculate Total Marks, Percentage, and GPA");
            System.out.println("5. Mark Attendance");
            System.out.println("6. Save Data to Files");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            String studentId;
            switch (choice) {
                case "1":
                    studentMenu();
                    break;
                case "2":
                    facultyMenu();
                    break;
                case "3":
                    courseMenu();
                    break;
                case "4":
                System.out.print("Enter Student ID: ");
                studentId = scanner.nextLine();
                Student student = university.getStudents().get(studentId);
                
                if (student != null) {
                    int totalMarks = 0;
                    int totalCredits = 0;
                    
                    for (String course : student.getCourses()) {
                        System.out.print("Enter marks for course " + course + ": ");
                        int marks = Integer.parseInt(scanner.nextLine());
                        student.setGrade(course, marks);
                        totalMarks += marks;
                        totalCredits += 3; 
                    }
                    
                    double percentage = (totalMarks / (totalCredits * 100.0)) * 100.0;
                    double gpa = student.calculateGPA();
                    
                    System.out.println("Total Marks: " + totalMarks);
                    System.out.println("Percentage: " + percentage + "%");
                    System.out.println("GPA: " + gpa);
                } else {
                    System.out.println("Student not found.");
                }
                break;
                case "5":
                    markAttendance();
                    break;
                case "6":
                    university.saveDataToFiles();
                    System.out.println("Data saved to files.");
                    break;
                
                case "7":
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void studentMenu() {
        boolean isStudentMenuRunning = true;
        while (isStudentMenuRunning) {
            System.out.println("\nStudent Menu");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. List All Students");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            String studentChoice = scanner.nextLine();

            switch (studentChoice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    removeStudent();
                    break;
                case "3":
                    university.listStudents();
                    break;
                case "4":
                    isStudentMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void facultyMenu() {
        boolean isFacultyMenuRunning = true;
        while (isFacultyMenuRunning) {
            System.out.println("\nFaculty Menu");
            System.out.println("1. Add Faculty");
            System.out.println("2. Remove Faculty");
            System.out.println("3. List Faculties");
            System.out.println("4. Assign Faculty to Course");
            System.out.println("5. Unassign Faculty from Course");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            String facultyChoice = scanner.nextLine();

            switch (facultyChoice) {
                case "1":
                    addFaculty();
                    break;
                case "2":
                    removeFaculty();
                    break;
                case "3":
                    university.listFaculties();
                    break;
                case "4":
                    assignFacultyToCourse();
                    break;
                case "5":
                    unassignFacultyFromCourse();
                    break;
                case "6":
                    isFacultyMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void courseMenu() {
        boolean isCourseMenuRunning = true;
        while (isCourseMenuRunning) {
            System.out.println("\nCourse Menu");
            System.out.println("1. Add Course");
            System.out.println("2. Remove Course");
            System.out.println("3. Enroll Student in Course");
            System.out.println("4. Drop Student from Course");
            System.out.println("5. List All Courses");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            String courseChoice = scanner.nextLine();

            switch (courseChoice) {
                case "1":
                    addCourse();
                    break;
                case "2":
                    removeCourse();
                    break;
                case "3":
                    enrollStudentInCourse();
                    break;
                case "4":
                    dropStudentFromCourse();
                    break;
                case "5":
                    university.listCourses();
                    break;
                case "6":
                    isCourseMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

     static void addStudent() {
        boolean validInput = false;
    
        while (!validInput) {
            try {
                System.out.print("Enter student ID: ");
                String studentId = scanner.nextLine();
    
                // Check if the input is numeric
                if (isNumeric(studentId)) {
                    // Convert the string to an integer if it is numeric
                    int numericId = Integer.parseInt(studentId);
    
                    // Check if the student ID already exists
                    if (!university.getStudents().containsKey(studentId)) {
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter student major: ");
                        String major = scanner.nextLine();
    
                        Student student = new Student(studentId, name, major);
                        university.getStudents().put(studentId, student);
                        System.out.println("Student added successfully: " + student);
                        validInput = true;
                    } else {
                        System.out.println("Student with ID " + studentId + " already exists. Please enter a different ID.");
                    }
                } else {
                    throw new InputMismatchException("Invalid input. Please enter a numeric student ID.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid student ID.");
                
            } catch (Exception e) {
                System.out.println("Error adding student: " + e.getMessage());
            }
        }
    }
    

    private static void removeStudent() {
        try {
            System.out.print("Enter student ID to remove: ");
            String studentId = scanner.nextLine();
    
            // Check if the student ID exists before removing
            if (university.getStudents().containsKey(studentId)) {
                university.getStudents().remove(studentId);
                System.out.println("Student removed successfully with ID: " + studentId);
            } else {
                System.out.println("Student not found with ID: " + studentId);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid student ID.");
            
        } catch (Exception e) {
            System.out.println("Error removing student: " + e.getMessage());
        }
    }
        
    private static void addFaculty() {
        boolean validInput = false;
    
        while (!validInput) {
            try {
                System.out.print("Enter faculty ID: ");
                String facultyId = scanner.nextLine();
    
                // Check if the input is numeric
                if (isNumeric(facultyId)) {
                    // Convert the string to an integer if it is numeric
                    int numericId = Integer.parseInt(facultyId);
    
                    // Check if the faculty ID already exists
                    if (!university.getFaculties().containsKey(facultyId)) {
                        System.out.print("Enter faculty name: ");
                        String name = scanner.nextLine();
    
                        Faculty faculty = new Faculty(facultyId, name);
                        university.getFaculties().put(facultyId, faculty);
                        System.out.println("Faculty added successfully: " + faculty);
                        validInput = true;
                    } else {
                        System.out.println("Faculty with ID " + facultyId + " already exists. Please enter a different ID.");
                    }
                } else {
                    throw new InputMismatchException("Invalid input. Please enter a numeric faculty ID.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid faculty ID.");
                
            } catch (Exception e) {
                System.out.println("Error adding faculty: " + e.getMessage());
            }
        }
    }
    

    private static void removeFaculty() {
        boolean validInput = false;
    
        while (!validInput) {
            try {
                System.out.print("Enter faculty ID to remove: ");
                String facultyId = scanner.nextLine();
    
                // Check if the faculty ID is numeric
                if (isNumeric(facultyId)) {
                    // Check if the faculty ID exists before removing
                    if (university.getFaculties().containsKey(facultyId)) {
                        university.getFaculties().remove(facultyId);
                        System.out.println("Faculty removed successfully with ID: " + facultyId);
                        validInput = true;
                    } else {
                        System.out.println("Faculty not found with ID: " + facultyId);
                    }
                } else {
                    throw new InputMismatchException("Invalid input. Please enter a numeric faculty ID.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid faculty ID.");
                scanner.nextLine(); // Consume the invalid input
            } catch (Exception e) {
                System.out.println("Error removing faculty: " + e.getMessage());
            }
        }
    }

    private static void assignFacultyToCourse() {
        System.out.print("Enter faculty ID: ");
        String facultyId = scanner.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();

        if (university.getFaculties().containsKey(facultyId) && university.getCourses().containsKey(courseCode)) {
            Faculty faculty = university.getFaculties().get(facultyId);
            Course course = university.getCourses().get(courseCode);
            faculty.assignCourse(courseCode);
            System.out.println("Faculty " + faculty.getName() + " assigned to course " + course.getCourseName());
        } else {
            System.out.println("Invalid faculty ID or course code.");
        }
    }

    private static void unassignFacultyFromCourse() {
        System.out.print("Enter faculty ID: ");
        String facultyId = scanner.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();

        if (university.getFaculties().containsKey(facultyId) && university.getCourses().containsKey(courseCode)) {
            Faculty faculty = university.getFaculties().get(facultyId);
            Course course = university.getCourses().get(courseCode);
            faculty.unassignCourse(courseCode);
            System.out.println("Faculty " + faculty.getName() + " unassigned from course " + course.getCourseName());
        } else {
            System.out.println("Invalid faculty ID or course code.");
        }
    }

    private static void addCourse() {
        boolean validInput = false;
    
        while (!validInput) {
            try {
                System.out.print("Enter course code: ");
                String courseCode = scanner.nextLine();
    
                // Check if the course code is numeric and has length 4
                if (isNumeric(courseCode) && courseCode.length() == 4) {
                    System.out.print("Enter course name: ");
                    String courseName = scanner.nextLine();
    
                    university.addCourse(courseCode, courseName);
                    System.out.println("Course added: " + university.getCourses().get(courseCode));
                    validInput = true;
                } else {
                    throw new InputMismatchException("Invalid input. Please enter a numeric course code with length 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. " + e.getMessage());
                
            } catch (Exception e) {
                System.out.println("Error adding course: " + e.getMessage());
            }
        }
    }

    private static void removeCourse() {
        boolean validInput = false;
    
        while (!validInput) {
            try {
                System.out.print("Enter course code to remove: ");
                String courseCode = scanner.nextLine();
    
                // Check if the course code is numeric and has length 4
                if (isNumeric(courseCode) && courseCode.length() == 4) {
                    // Check if the course code exists before removing
                    if (university.getCourses().containsKey(courseCode)) {
                        university.getCourses().remove(courseCode);
                        System.out.println("Course removed successfully with code: " + courseCode);
                        validInput = true;
                    } else {
                        System.out.println("Course not found with code: " + courseCode);
                    }
                } else {
                    throw new InputMismatchException("Invalid input. Please enter a numeric course code with length 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. " + e.getMessage());
                scanner.nextLine(); // Consume the invalid input
            } catch (Exception e) {
                System.out.println("Error removing course: " + e.getMessage());
            }
        }
    }
    

    private static void enrollStudentInCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();

        if (university.getStudents().containsKey(studentId) && university.getCourses().containsKey(courseCode)) {
            Student student = university.getStudents().get(studentId);
            Course course = university.getCourses().get(courseCode);
            student.enrollCourse(courseCode);
            System.out.println("Student " + student.getName() + " enrolled in course " + course.getCourseName());
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    private static void dropStudentFromCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();

        if (university.getStudents().containsKey(studentId) && university.getCourses().containsKey(courseCode)) {
            Student student = university.getStudents().get(studentId);
            Course course = university.getCourses().get(courseCode);
            student.dropCourse(courseCode);
            System.out.println("Student " + student.getName() + " dropped from course " + course.getCourseName());
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    private static void markAttendance() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
    
        try {
            if (university.getStudents().containsKey(studentId)) {
                Student student = university.getStudents().get(studentId);
                List<String> courses = student.getCourses();
    
                if (!courses.isEmpty()) {
                    for (String courseCode : courses) {
                        System.out.print("Enter attendance for course " + courseCode + " (0 or 1): ");
                        int attendance = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character
    
                        if (attendance == 0 || attendance == 1) {
                            student.markAttendance(courseCode);
                            System.out.println("Attendance marked for course " + courseCode);
                        } else {
                            System.out.println("Invalid attendance value. Please enter 0 or 1.");
                            break;
                        }
                    }
                } else {
                    System.out.println("The student is not enrolled in any courses.");
                }
            } else {
                System.out.println("Student not found with ID: " + studentId);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numeric values for attendance.");
            scanner.nextLine(); 
        }
    }
        private static String getValidGender() {
            while (true) {
                try {
                    System.out.print("Enter gender (Male/Female): ");
                    String gender = scanner.nextLine().toLowerCase(); // Convert to lowercase for case-insensitive comparison
                    if (gender.equals("male") || gender.equals("female")) {
                        return gender;
                    } else {
                        throw new Exception("Invalid input. Please enter either 'male' or 'female'.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. " + e.getMessage());
                }
            }
        } 
        
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }  
}