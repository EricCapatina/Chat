package Client.Mediator;

import Server.ServerWorker;

public interface IClientsMediator
{
    void sendMessageToAll(String msg, ServerWorker who);
    void sendMessageToClient(String msg, ServerWorker who, String toClient);
}
