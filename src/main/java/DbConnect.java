import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnect {
    public static void main(String[] args) {

        // Connect to DB sql server
        String connectionUrl = "jdbc:sqlserver://localhost\\MSSQLSERVER:1433; databaseName=BikeStores; user=sa; password=mainSQL!123#R;";

        try {
            Connection connectionGet = DriverManager.getConnection(connectionUrl);
            Statement stmt = connectionGet.createStatement();

            // Insert
            stmt.executeUpdate(
                    "insert into sales.customers " +
                            "(first_name, last_name, phone, email, street, city, state, zip_code) " +
                            "values " +
                            "('Roman', 'Tihai', 324609, 'roman@gmail.com', '20 Moscow street', 'Chisinau', 'Chisinau', 2019)"
            );

            // Update
            stmt.executeUpdate(
                    "update sales.customers " +
                            "set email = 'roman.tihai@gmail.com'" +
                            "where last_name = 'Tihai' and first_name = 'Roman'"
            );

            // Delete
            stmt.executeUpdate(
                    "delete from sales.customers " +
                            "where last_name = 'Tihai' and first_name = 'Roman'"
            );

            // Bulk insert
            String sqlBulk = "BULK INSERT sales.customers FROM 'D:\\DB_data.csv' WITH (FIRSTROW = 2, FIELDTERMINATOR = ',', ROWTERMINATOR='\n') ";


            // Execute SQL statement
            String SQL = "SELECT * FROM sales.customers WHERE last_name = 'Tihai'";
            ResultSet resultSet = stmt.executeQuery(SQL);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            // Iterate through the data in the result set and display it
            while (resultSet.next()) {
                int nrCol = rsMetaData.getColumnCount();
                StringBuilder row = new StringBuilder("| ");
                for (int i = 1; i <= nrCol; i++) {
                    row.append(resultSet.getString(i)).append(" | ");
                }
                System.out.println(row);
            }
        }

        // Handle any errors that may have occurred
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
