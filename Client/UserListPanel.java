package Client;

import Client.Composite.Administrator;
import Client.Composite.ShowOnlineStaffComponent;
import Client.Composite.ShowOnlineStaffDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class UserListPanel extends JPanel implements UserStatusListener
{
    private final ChatClient client;
    private JList<String> userListUI;
    private JList<String> staffListUI;
    private DefaultListModel<String> userListModel;
    private DefaultListModel<String> userStaffModel;
    JButton _checkFriendsButton = new JButton("List of your friends");


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

    public UserListPanel(ChatClient client)
    {
        this.client = client;
        this.client.addUserStatusListener(this);

        userListModel = new DefaultListModel<>();
        userListUI = new JList<>(userListModel);
        setLayout(new BorderLayout());

        add(new JScrollPane(userListUI), BorderLayout.CENTER);
        add(_checkFriendsButton, BorderLayout.SOUTH);


        _checkFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame f = new JFrame("Friends");
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setSize(500, 500);
                //f.getContentPane().add(messagePane, BorderLayout.CENTER);
                f.setVisible(true);
            }
        });
        userListUI.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    String login = userListUI.getSelectedValue();
                    MessagePanel messagePane = MessagePanel.getInstance(client, login);
                    JFrame f = new JFrame("Message: " + login);
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setSize(500, 500);
                    f.getContentPane().add(messagePane, BorderLayout.CENTER);
                    f.setVisible(true);
                }
                if(e.getButton() == MouseEvent.BUTTON3)
                {
                    JFrame p = new JFrame("Check Staff");
                    userStaffModel = new DefaultListModel<>();
                    staffListUI = new JList<>(userStaffModel);
                    setLayout(new BorderLayout());
                    p.add(new JScrollPane(userListUI), BorderLayout.CENTER);
                    p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    p.setSize(500, 500);
                    String sql = "SELECT name FROM users WHERE adminLevel BETWEEN 1 AND 10";
                    try (Connection conn = this.connectToDB();
                         Statement stmt = conn.createStatement();
                         ResultSet rs = stmt.executeQuery(sql))
                    {
                        ShowOnlineStaffDisplay Administrators = new ShowOnlineStaffDisplay("Administrators: ");
                        while(rs.next())
                        {
                            userStaffModel.addElement(rs.getString("name"));
                            Administrator admins = new Administrator(rs.getString("name"));
                            Administrators.addStaff(admins);
                        }
                        Administrators.show(0);
                    }
                    catch (SQLException e1)
                    {
                        System.out.println(e1.getMessage());
                    }
                    p.setVisible(true);
                }
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
        });
    }

    @Override
    public void online(String login)
    {
        userListModel.addElement(login);
    }



    @Override
    public void offline(String login)
    {
        userListModel.removeElement(login);
    }
    public Object[] getUserListModel()
    {
        return userListModel.toArray();
    }
}
