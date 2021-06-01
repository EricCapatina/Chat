package Client.State;

import Client.ChatClient;

public abstract class State
{
    public ChatClient _client;

    public State(ChatClient client)
    {
        this._client = client;
    }

    public abstract String Online();
    public abstract String Offline();
}
