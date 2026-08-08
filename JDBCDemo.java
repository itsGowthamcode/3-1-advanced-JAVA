 import java.sql.*;

public class JDBCDemo {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "testuser";
        String password = "testpass";

        try (Connection conn =
                     DriverManager.getConnection(url, user, password)) {

            System.out.println("Database Connected Successfully.");

            // 1. Create Employee table if it does not exist
            String createTable =
                    "CREATE TABLE IF NOT EXISTS Employee (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "salary DECIMAL(10,2))";

            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTable);
            }

            System.out.println("Employee table ready.");

            // 2. Remove employee 101 if already present
            String deleteSQL =
                    "DELETE FROM Employee WHERE id = ?";

            try (PreparedStatement deleteStmt =
                         conn.prepareStatement(deleteSQL)) {

                deleteStmt.setInt(1, 101);
                deleteStmt.executeUpdate();
            }

            // 3. Insert employee
            String insertSQL =
                    "INSERT INTO Employee (id, name, salary) " +
                    "VALUES (?, ?, ?)";

            try (PreparedStatement insertStmt =
                         conn.prepareStatement(insertSQL)) {

                insertStmt.setInt(1, 101);
                insertStmt.setString(2, "John Doe");
                insertStmt.setDouble(3, 55000.00);

                insertStmt.executeUpdate();

                System.out.println("Employee inserted successfully.");
            }

            // 4. Get salary of employee
            String salarySQL =
                    "SELECT salary FROM Employee WHERE id = ?";

            try (PreparedStatement salaryStmt =
                         conn.prepareStatement(salarySQL)) {

                salaryStmt.setInt(1, 101);

                ResultSet rs = salaryStmt.executeQuery();

                if (rs.next()) {
                    double salary = rs.getDouble("salary");

                    System.out.println(
                            "Salary of Employee 101 = " + salary
                    );
                }

                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
