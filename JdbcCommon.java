
import java.sql.DriverManager;
import java.sql.*;

public class JdbcCommon {

    public static ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/VideoOnDemand", "root", "system");
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;

    }

}
