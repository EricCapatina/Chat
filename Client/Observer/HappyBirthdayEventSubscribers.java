package Client.Observer;

import Client.ChatClient;

import javax.swing.*;
import java.awt.*;

public class HappyBirthdayEventSubscribers extends JPanel implements IEventSubscriber
{
    private HappyBirthdayEventPublisher _publisher;
    private ChatClient _client;
    JFrame _f;

    public HappyBirthdayEventSubscribers(HappyBirthdayEventPublisher publisher, ChatClient client)
    {
        _publisher = publisher;
        _client = client;
        //  _publisher.addSubscriber(this);
    }

    @Override
    public void update()
    {
        String inform = _publisher.getEventInfo();
        System.out.println("Subscriber of: " + _publisher.getClass() + " with nickname: " +
                _client.getClass() + " got event news: " + inform);
        JOptionPane.showMessageDialog(_f,"Subscriber of: " + _publisher.getClass() + " with nickname: "
                + _client.getClass() + " got event news: " + inform);
    }
}
