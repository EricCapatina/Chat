package Client.Composite;

import Client.ChatClient;

import javax.swing.*;

public class Moderator extends ShowOnlineStaffComponent
{
    public Moderator(String name)
    {
        super(name);
    }

    @Override
    public void show(int level)
    {
        String space = " ".repeat(level);
        JTextArea staff = new JTextArea(space + " " + name);
        System.out.printf(space + " " + name);
    }
}
