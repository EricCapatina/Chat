package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MessagePanel extends JPanel implements MessageListener
{
    private static MessagePanel _messagePanelSingleton;
    private final ChatClient client;
    private final String login;
    public DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> messageList = new JList<>(listModel);
    private JTextField inputField = new JTextField();
    JButton _createGroupButton = new JButton("Download conversation");
    //JButton _sendMessageToAll = new JButton("Send Message To All");



    private MessagePanel(ChatClient client, String login)
    {
        this.client = client;
        this.login = login;

        client.addMessageListener(this);

        setLayout(new BorderLayout());
        add(new JScrollPane(messageList), BorderLayout.CENTER);

        add(inputField, BorderLayout.SOUTH);
        add(_createGroupButton, BorderLayout.NORTH);
//        add(_sendMessageToAll, BorderLayout.EAST);
//
//        _sendMessageToAll.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                new Menu();
//            }
//        });

        _createGroupButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                CreateGroupWindow createGroupWindow = new CreateGroupWindow(client);
                JFrame frame = new JFrame("Download");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 600);
                frame.getContentPane().add(createGroupWindow, BorderLayout.CENTER);
                frame.setVisible(true);
                setVisible(false);
            }
        });



        inputField.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String text = inputField.getText();
                    client.msg(login, text);
                    listModel.addElement("You: " + text);
                    inputField.setText("");
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
    public static synchronized MessagePanel getInstance(ChatClient client, String login)
    {
        if (_messagePanelSingleton == null)
        {
            _messagePanelSingleton = new MessagePanel(client, login);
        }
        return _messagePanelSingleton;
    }

    public String getListModel()
    {
        return listModel.toString();
    }


    @Override
    public void onMessage(String fromLogin, String msgBody)
    {
        if (login.equalsIgnoreCase(fromLogin))
        {
            String line = fromLogin + ": " + msgBody;
            listModel.addElement(line);
        }
    }
}
