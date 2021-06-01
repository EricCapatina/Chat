package Client.Mediator;

public abstract class Clients
{
    protected IClientsMediator _clientmediator;

    public Clients(IClientsMediator mediator)
    {
        this._clientmediator = mediator;
    }

    public abstract void sendMessageToAll(String msg);
    public abstract void sendMessageToClient(String msg, String toClient);
    public abstract void readCommand(String msg, String who);
}
