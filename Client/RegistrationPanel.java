package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegistrationPanel extends JFrame
{
    JTextField _registrationField = new JTextField();
    JPasswordField _passwordField = new JPasswordField();
    JButton _registrationbutton = new JButton("Registration");


    private Connection connectToDB()
    {
        String url = "jdbc:sqlite:C:/Users/Краб/IdeaProjects/Chat/src/Server/Database/Chat.db";
        Connection conn = null;
        try
        {
            System.out.println("Connection successful");
            conn = DriverManager.getConnection(url);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void doRegistration()
    {
        String login = _registrationField.getText();
        String password = _passwordField.getText();

        String url = "jdbc:sqlite:C:/Users/Краб/IdeaProjects/Chat/src/Server/Database/Chat.db";
        String sql = "INSERT INTO users(name,password,adminLevel) VALUES(?,?,?)";

        if(!login.equalsIgnoreCase(""))
        {
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql))
            {
                pstmt.setString(1, login);
                pstmt.setString(2, password);
                pstmt.setInt(3, 0);
                pstmt.executeUpdate();
                System.out.println("Registration successful!");
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public RegistrationPanel()
    {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(_registrationField);
        p.add(_passwordField);
        p.add(_registrationbutton);
        getContentPane().add(p, BorderLayout.CENTER);
        pack();

        _registrationbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                doRegistration();
            }
        });
    }
    public static void main(String[] args)
    {
        RegistrationPanel loginWindow = new RegistrationPanel();
        loginWindow.setVisible(true);
    }
}
