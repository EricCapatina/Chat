package Client;

import Client.Adapter.ClientConversationProvider;
import Client.Adapter.IConversationSaverAdapter;
import Client.Adapter.TxtToDocSaverAdapter;
import Client.Adapter.TxtToDocxSaverAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateGroupWindow extends JPanel
{
    private final ChatClient client;
    private String login = null;
    JButton _downloadTxtFile = new JButton("Download");

    public CreateGroupWindow(ChatClient client)
    {
        this.client = client;
        setLayout(new GridLayout(0, 5, 20, 20));
        JLabel label = new JLabel("File will be downloaded by default in txt format, u can change it with Adapter");
        JCheckBox doc = new JCheckBox(".doc");
        doc.setBounds(100, 100, 150, 20);
        JCheckBox docx = new JCheckBox(".docx");
        docx.setBounds(100, 150, 150, 20);
        JCheckBox text = new JCheckBox("test");
        text.setBounds(100, 200, 150, 20);
        text.setSelected(true);
        _downloadTxtFile.setBounds(100,250,80,30);
        add(_downloadTxtFile);
        add(label);
        add(doc);
        add(docx);
        add(text);
        setSize(400,400);
        setVisible(true);



        _downloadTxtFile.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(doc.isSelected())
                {
                    ClientConversationProvider clientConversationProvider = new ClientConversationProvider(client, login);
                    String text = clientConversationProvider.getConversationMessages();
                    IConversationSaverAdapter conversationSaverAdapter = new TxtToDocSaverAdapter();
                    conversationSaverAdapter.convertAndSave("SavedConversation", text);
                }
                if(docx.isSelected())
                {
                    ClientConversationProvider clientConversationProvider = new ClientConversationProvider(client, login);
                    String text = clientConversationProvider.getConversationMessages();
                    IConversationSaverAdapter conversationSaverAdapter = new TxtToDocxSaverAdapter();
                    conversationSaverAdapter.convertAndSave("SavedConversation", text);
                }
                if(text.isSelected())
                {
                    ClientConversationProvider clientConversationProvider = new ClientConversationProvider(client, login);
                    String text = clientConversationProvider.getConversationMessages();
                    try {
                        File myObj = new File("SavedConvesation" + ".txt");
                        FileWriter fileWriter = new FileWriter("SavedConvesation" + ".docx");
                        System.out.println("File created: " + myObj.getName());
                        fileWriter.write(text);
                        fileWriter.close();
                    }
                    catch (IOException e2)
                    {
                        System.out.println("An error occurred.");
                        e2.printStackTrace();
                    }
                }
            }
        });
    }

//    private void doGroup()
//    {
//        ArrayList<String> usersList = new ArrayList<>();
//        _usersField.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                String user = _usersField.getText();
//                if(user.length() > 1)
//                {
//                    usersList.add(user);
//                    _usersField.setText("");
//                }
//                else
//                {
//                    System.out.println("Invalid input!");
//                }
//            }
//        });
//        _createGroupButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                client.handleJoin(usersList);
//                System.out.println("Creating group: users: " + usersList.size());
//            }
//        });
    }


