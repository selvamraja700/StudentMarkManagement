import java.sql.*;
import java.util.*;
import java.io.FileWriter;

public class CollegeAdmissionSystem {

    static final String DB_URL = "jdbc:mysql://localhost:3306/college_admission";
    static final String DB_USER = "root";
    static final String DB_PASS = "raj@123"; // Replace with your MySQL password

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- College Admission Management System ---");
            System.out.println("1. Register Student");
            System.out.println("2. Add Course");
            System.out.println("3. Apply for Course");
            System.out.println("4. Process Applications (Admin)");
            System.out.println("5. Generate Admission List (CSV)");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (ch) {
                case 1 -> registerStudent(sc);
                case 2 -> addCourse(sc);
                case 3 -> applyForCourse(sc);
                case 4 -> processApplications();
                case 5 -> generateCSV();
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // a. Register new student
    static void registerStudent(Scanner sc) throws Exception {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO Students(name, email, marks) VALUES (?, ?, ?)");
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setInt(3, marks);
        ps.executeUpdate();
        System.out.println("✅ Student registered successfully.");
        con.close();
    }

    // b. Add new course
    static void addCourse(Scanner sc) throws Exception {
        System.out.print("Enter Course Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Cut-off Marks: ");
        int cutOff = sc.nextInt();

        Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO Courses(name, cutOff) VALUES (?, ?)");
        ps.setString(1, name);
        ps.setInt(2, cutOff);
        ps.executeUpdate();
        System.out.println("✅ Course added successfully.");
        con.close();
    }

    // c. Apply for a course
    static void applyForCourse(Scanner sc) throws Exception {
        System.out.print("Enter Student Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Course ID: ");
        int courseId = sc.nextInt();

        Connection con = getConnection();

        // Get student ID
        PreparedStatement ps = con.prepareStatement("SELECT id FROM Students WHERE email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int studentId = rs.getInt("id");

            // Check for existing application
            PreparedStatement check = con.prepareStatement("SELECT * FROM Applications WHERE studentId=? AND courseId=?");
            check.setInt(1, studentId);
            check.setInt(2, courseId);
            ResultSet checkRs = check.executeQuery();
            if (checkRs.next()) {
                System.out.println("⚠️ Already applied to this course.");
            } else {
                PreparedStatement ps2 = con.prepareStatement("INSERT INTO Applications(studentId, courseId) VALUES (?, ?)");
                ps2.setInt(1, studentId);
                ps2.setInt(2, courseId);
                ps2.executeUpdate();
                System.out.println("✅ Application submitted.");
            }
        } else {
            System.out.println("❌ Student not found.");
        }

        con.close();
    }

    // d. Process Applications based on merit
    static void processApplications() throws Exception {
        Connection con = getConnection();

        String sql = """
            UPDATE Applications a
            JOIN Students s ON a.studentId = s.id
            JOIN Courses c ON a.courseId = c.id
            SET a.status = CASE
                WHEN s.marks >= c.cutOff THEN 'Approved'
                ELSE 'Rejected'
            END
            WHERE a.status = 'Pending'
        """;

        PreparedStatement ps = con.prepareStatement(sql);
        int updated = ps.executeUpdate();

        System.out.println("✅ Applications processed: " + updated);
        con.close();
    }

    // e. Export approved students to CSV
    static void generateCSV() throws Exception {
        Connection con = getConnection();

        String sql = """
            SELECT s.name, s.email, s.marks, c.name AS course, a.status
            FROM Applications a
            JOIN Students s ON a.studentId = s.id
            JOIN Courses c ON a.courseId = c.id
            WHERE a.status = 'Approved'
        """;

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        FileWriter writer = new FileWriter("admission_list.csv");
        writer.write("Student Name,Email,Marks,Course,Status\n");

        while (rs.next()) {
            writer.write(rs.getString("name") + "," +
                    rs.getString("email") + "," +
                    rs.getInt("marks") + "," +
                    rs.getString("course") + "," +
                    rs.getString("status") + "\n");
        }

        writer.close();
        con.close();
        System.out.println("✅ Admission list saved to: admission_list.csv");
    }
}
