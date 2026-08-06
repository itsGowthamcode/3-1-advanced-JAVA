import java.sql.*;

class UResultSet{
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/testdb?";
        String user = "testuser";
        String password = "testpass";

        try{
        // Load JDBC-ODBC bridge driver (Type-1 driver)
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect using DSN (make sure 'demodsn' is configured in ODBC)
        Connection con = DriverManager.getConnection(url, user, password);

        // Create updatable and scrollable statement
        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        // Execute query to fetch all student records
        ResultSet rs = st.executeQuery("SELECT * FROM Student");

        // -------------------------
        // DELETE the last row
        // -------------------------
        rs.last();            // Move to last record
        rs.deleteRow();       // Delete it
        System.out.println("Last student record deleted successfully.");

        // -------------------------
        // INSERT a new row
        // -------------------------
        rs.moveToInsertRow();                  // Move to special insert row
        rs.updateInt("RollNo", 105);           // Set RollNo
        rs.updateString("Name", "John Doe");   // Set Name
        rs.updateString("Address", "Hyderabad"); // Set Address
        rs.insertRow();                        // Insert the new row
        System.out.println("New student record inserted successfully.");

        // Close the connection
        con.close();
    }
       catch(Exception e) {
          e.printStackTrace();
}
