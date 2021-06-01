package Client;

import Client.Mediator.ClientMediator;
import Client.Mediator.Clients;
import Client.Observer.HappyBirthdayEventPublisher;
import Client.Observer.HappyBirthdayEventSubscribers;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class LoginWindow extends JFrame
{
    private final ChatClient client;
    private UserListPanel panel;
    JTextField _loginField = new JTextField();
    JPasswordField _passwordField = new JPasswordField();
    JButton _loginButton = new JButton("Login");
    JMenu menu;
    JMenuItem sendMsgToAll;
    JMenuItem register;

    public LoginWindow()
    {
        super("Login");
        ClientMediator mediatorPanel = new ClientMediator();
        this.client = new ChatClient(mediatorPanel,"localhost", 1337);
        client.connect();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JMenuBar mb = new JMenuBar();
        menu = new JMenu("Menu");
        sendMsgToAll = new JMenuItem("Message All");
        register = new JMenuItem("Registration");
        menu.add(sendMsgToAll);
        menu.add(register);
        mb.add(menu);
        setJMenuBar(mb);
        p.add(_loginField);
        p.add(_passwordField);
        p.add(_loginButton);


        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                RegistrationPanel registrationPanel = new RegistrationPanel();
                registrationPanel.setVisible(true);
            }
        });


        sendMsgToAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ClientMediator clientMediator = new ClientMediator();
                for(Object _clients : panel.getUserListModel())
                {
                    clientMediator.addClient((ChatClient) _clients);
                    ((ChatClient) _clients).sendMessageToAll("Achooo!");
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                client.getState().Offline();
            }
        });

        _loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                doLogin();
            }
        });
        getContentPane().add(p, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }



    private void doLogin()
    {
        String login = _loginField.getText();
        String password = _passwordField.getText();
        try
        {
            if(client.login(login, password))
            {
                client.getState().Online();
                UserListPanel userListPanel = new UserListPanel(client);
                JFrame frame = new JFrame("User List");
                //frame.getContentPane().add(happyBirthdayEventSubscribers.update(), BorderLayout.CENTER);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 600);
                frame.getContentPane().add(userListPanel, BorderLayout.CENTER);
                frame.setVisible(true);
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        client.getState().Offline();
                    }
                });
                HappyBirthdayEventPublisher publisher = new HappyBirthdayEventPublisher();
                HappyBirthdayEventSubscribers happyBirthdayEventSubscribers = new HappyBirthdayEventSubscribers(publisher, client);
                publisher.addSubscriber(happyBirthdayEventSubscribers);
                publisher.setEventInfo("Happy Birthday!");
                setVisible(false);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Invalid login / password.");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setVisible(true);
    }

}
