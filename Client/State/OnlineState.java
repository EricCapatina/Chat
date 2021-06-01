package Client.State;

import Client.ChatClient;

public class OnlineState extends State
{

    public OnlineState(ChatClient client)
    {
        super(client);
    }

    @Override
    public String Online()
    {
        System.out.println("Online");
        return "Online";
    }

    @Override
    public String Offline() {
        _client.changeState(new OfflineState(_client));
        System.out.println("Offline");
        return "Offline";
    }
}
