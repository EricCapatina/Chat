package Client.Composite;

import Client.ChatClient;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShowOnlineStaffDisplay extends ShowOnlineStaffComponent
{
    private List<ShowOnlineStaffComponent> _mainComponent = new ArrayList<ShowOnlineStaffComponent>();
    JFrame _f;
    public ShowOnlineStaffDisplay(String name)
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
        System.out.println(space + "- " + name);
        for(ShowOnlineStaffComponent component : _mainComponent)
        {
            component.show(level + 1);
        }

    }

    public void addStaff(ShowOnlineStaffComponent component)
    {
        _mainComponent.add(component);
    }

    public void removeStaff(ShowOnlineStaffComponent component)
    {
        _mainComponent.remove(component);
    }
}
