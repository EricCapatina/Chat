package Client.Composite;


import javax.swing.*;
import java.sql.*;

public class Administrator extends ShowOnlineStaffComponent
{
    JFrame _f;
    public Administrator(String name)
    {
        super(name);
    }


    private Connection connectToDB()
    {
        String url = "jdbc:sqlite:C:/Users/Краб/IdeaProjects/Chat/src/Server/Database/Chat.db";
        Connection conn = null;
        try
        {
            System.out.println("Connection succesful");
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @Override
    public void show(int level)
    {
        String sql = "SELECT name FROM users WHERE adminLevel BETWEEN 1 AND 10";
        String space = " ".repeat(level);
        System.out.println(space + " " + name);
        try (Connection conn = this.connectToDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while(rs.next())
            {
                name = rs.getString("name");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
