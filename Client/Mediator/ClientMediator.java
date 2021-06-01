package Client.Mediator;

import Client.ChatClient;
import Server.ServerWorker;

import java.util.ArrayList;

public class ClientMediator implements IClientsMediator
{
    private ArrayList<ChatClient> _clients;

    public ClientMediator()
    {
        _clients = new ArrayList<ChatClient>();
    }

    public void addClient(ChatClient client)
    {
        _clients.add(client);
    }

    @Override
    public void sendMessageToAll(String msg, ServerWorker who)
    {
        for(ChatClient client : _clients )
        {
            client.readCommand(msg, who.getLogin());
        }
    }

    @Override
    public void sendMessageToClient(String msg, ServerWorker who, String toClient)
    {
        for(ChatClient client : _clients)
        {
            if(client == null)
            {
                System.out.println("Client doesn't exist");
            }
            else
            {
                if(who.getLogin().equals(toClient))
                {
                    client.readCommand(msg, who.getLogin());
                }
            }
        }
    }
}
