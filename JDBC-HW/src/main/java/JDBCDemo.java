import java.sql.*;

/**
 * A JDBC program that interacts with MySql jdbc_demo db.
 *
 * Steps:
 *  - allocate connection object to connect to the db
 *  - allocate statement objects (here prepareStatement objects) to run SQL statements
 *  - execute the statements
 *      + return ResultSet for executeQuery
 *      + return int for executeUpdate
 *  - process the query result
 */
public class JDBCDemo {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // search & load MySQL driver implementation
        // Class.forName("com.mysql.jdbc.Driver");
        Class.forName("com.mysql.cj.jdbc.Driver");

        // query to initialize table students
        String dropTableQuery = "DROP TABLE IF EXISTS students;";
        String createTableQuery = """
                CREATE TABLE students (
                                          id INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          first_name VARCHAR(255) NOT NULL,
                                          last_name VARCHAR(255) NOT NULL,
                                          age INT
                ) DEFAULT CHARSET=utf8;""";

        // query to insert rows to students
        // "?" is where the parameters go
        String insertRowsQuery = "INSERT INTO students (first_name, last_name, age) VALUES (?, ?, ?);";

        // query to select rows in students table
        String selectRowsQuery = "SELECT * FROM students;";

        // query to update rows in students table where teh student whose first name is Jessie
        String updateRowsQuery = """
                UPDATE students
                SET age = 14
                WHERE first_name = "Jessie";
                """;

        // query to delete rows in students table
        String deleteRowsQuery = "DELETE FROM students WHERE age > 25 OR age < 18;";

        try (
                // connect to database (Requires JDBC) [Default username:root, pw shaojiarui]
                Connection connection = DriverManager.getConnection(Config.getUrl(), Config.getUser(), Config.getPassword());

                // PreparedStatement is a subclass of Statement that supports data substitution & can execute a statement multiple times
                // prepared statements are pre-compiled, to prevent the sql injection attack
                // create PreparedStatement objects to run SQL statements on DB
                PreparedStatement pstmtDrop = connection.prepareStatement(dropTableQuery);
                PreparedStatement pstmtCreate = connection.prepareStatement(createTableQuery);
                PreparedStatement pstmtInsert = connection.prepareStatement(insertRowsQuery);
                PreparedStatement pstmtSelect = connection.prepareStatement(selectRowsQuery);
                PreparedStatement pstmtUpdate = connection.prepareStatement(updateRowsQuery);
                PreparedStatement pstmtDelete = connection.prepareStatement(deleteRowsQuery);
        ) {

            try {
                // JDBC is auto-commit by default, so we have to manually turn it off for transactions
                connection.setAutoCommit(false);

                // CURD Operations
                // 1 - initialize the table students if not exist
                pstmtDrop.execute();
                pstmtCreate.execute();
                connection.commit();

                // insert instances into students
                pstmtInsert.setString(1, "Jessie");
                pstmtInsert.setString(2, "Shao");
                pstmtInsert.setInt(3, 23);
                // executeUpdate(statement) is used to run DDL (e.g. CREATE) or DML (e.g INSERT) commands
                pstmtInsert.executeUpdate();

                pstmtInsert.setString(1, "Dave");
                pstmtInsert.setString(2, "Holmes");
                pstmtInsert.setInt(3, 28);
                pstmtInsert.executeUpdate();

                pstmtInsert.setString(1, "Casey");
                pstmtInsert.setString(2, "Shepard");
                pstmtInsert.setInt(3, 18);
                pstmtInsert.executeUpdate();
                connection.commit();

                // 2 - read the table rows (instances)
                // executeQuery(statement) is used to run DQL command (i.e. SELECT) and returns a ResultSet object
                ResultSet result = pstmtSelect.executeQuery();
                System.out.printf("%-6s\t%-8s\t%-3s%n", "fName", "lName", "age");
                System.out.println("-----------------------");
                while (result.next()) {
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    int age = result.getInt("age");
                    System.out.printf("%-6s\t%-8s\t%-3d%n", firstName, lastName, age);
                }
                System.out.println();
                connection.commit();

                // 3 - update the table instances
                int updateCount = pstmtUpdate.executeUpdate();
                System.out.println(updateCount + " rows have been updated.");
                System.out.println();
                connection.commit();

                // 4 - delete the table instances
                int deleteCount = pstmtDelete.executeUpdate();
                System.out.println(deleteCount + " rows have been deleted.");
                System.out.println();
                connection.commit();

                // display the modified table
                result = pstmtSelect.executeQuery();
                System.out.printf("%-6s\t%-8s\t%-3s%n", "fName", "lName", "age");
                System.out.println("-----------------------");
                while (result.next()) {
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    int age = result.getInt("age");
                    System.out.printf("%-6s\t%-8s\t%-3d%n", firstName, lastName, age);
                }
                connection.commit();

            } catch (SQLException e) {
                System.out.println("rolling back changes");
                connection.rollback();
                e.printStackTrace();
            }
        }
    }
}
