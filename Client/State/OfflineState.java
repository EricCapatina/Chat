package Client.State;

import Client.ChatClient;

public class OfflineState extends State
{
    public OfflineState(ChatClient client)
    {
        super(client);
    }

    @Override
    public String Online()
    {
        _client.changeState(new OnlineState(_client));
        System.out.println("Online");
        return "Online";
    }

    @Override
    public String Offline() {
        System.out.println("Offline");
        return "Offline";
    }
}
