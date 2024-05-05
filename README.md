# University-Management-System

Classes:

UserManager: Manages user registration and login functionalities. It maintains a collection of users and provides methods for registering new users and authenticating existing ones.

User: Represents a user in the system with attributes like username, password, name, phone, email, age, and gender.

Course: Represents a university course with attributes such as course code, course name, and credits.

Faculty: Represents a faculty member with attributes like faculty ID and name. It also tracks the courses assigned to the faculty.

Student: Represents a student with attributes including student ID, name, major, enrolled courses, grades, and attendance.

University: The main class representing the university management system. It contains collections of courses, faculties, and students. It also has a UserManager for handling user-related operations.
Functionalities:

User Management: Users can register with the system providing details like username, password, name, phone, email, age, and gender. Registered users can then log in using their credentials.

Course Management: Faculty members can add or remove courses. Students can enroll or drop courses.

Faculty Management: Admins can add or remove faculty members and assign them to courses.

Student Management: Admins can add or remove students. Students can also mark their attendance for enrolled courses.

Data Persistence: The system supports saving data to files, including information about courses, faculties, and students. This feature ensures that data is preserved even after the program terminates.

Menu-driven Interface: The main method provides a menu-driven interface for different user roles (students, faculty, admin). Users can navigate through the options to perform various operations.

Feedback:
Error Handling: The code includes error handling for input validation, such as checking for numeric values and handling input mismatch exceptions. However, consistent error handling throughout the codebase would improve its robustness.

Code Organization: The code is well-structured with separate methods for different functionalities, enhancing readability and maintainability.

Input Validation: The code validates user inputs for certain fields like phone numbers and ages, ensuring data integrity and preventing errors.
