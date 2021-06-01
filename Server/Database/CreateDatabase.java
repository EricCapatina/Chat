package Server.Database;
import java.sql.*;


public class CreateDatabase
{
    public static void createNewDatabase(String fileName)
    {
        String url = "jdbc:sqlite:C:/Users/Краб/IdeaProjects/Chat/src/Server/Database/" + fileName;

        try (Connection conn = DriverManager.getConnection(url))
        {
            if (conn != null)
            {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void createTestUsers(int id, String name, String password, String friends, int adminLevel)
    {
        String url = "jdbc:sqlite:C:/Users/Краб/IdeaProjects/Chat/src/Server/Database/Chat.db";

        String sql = "INSERT INTO users(id,name,password,friends,adminLevel) VALUES(?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, password);
            pstmt.setString(4, friends);
            pstmt.setInt(5, adminLevel);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable()
    {
        String url = "jdbc:sqlite:C:/Users/Краб/IdeaProjects/Chat/src/Server/Database/Chat.db";

        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	password text NOT NULL,\n"
                + "	friends text,\n"
                + "	adminLevel integer\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement())
        {
            stmt.execute(sql);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args)
    {
        createNewDatabase("Chat.db");
        createNewTable();
        createTestUsers(1, "guest", "guest", " ", 0);
        createTestUsers(2, "jim", "jim", " ", 0);
        createTestUsers(3, "eric", "eric", "guest, jim", 10);
    }
}
