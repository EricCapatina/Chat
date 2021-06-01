package Client.Adapter;


import Client.ChatClient;
import Client.MessagePanel;

public class ClientConversationProvider
{
    private final ChatClient client;
    private String login = null;

    public ClientConversationProvider(ChatClient client, String login)
    {
        this.client = client;
        this.login = login;
    }

    public String getConversationMessages()
    {
        MessagePanel _messagePanel = MessagePanel.getInstance(client, login);
        System.out.println("MSGES: " + _messagePanel.getListModel());
        return _messagePanel.getListModel();
    }
}
