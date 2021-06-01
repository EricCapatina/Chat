package Client.Composite;

import Client.ChatClient;

public abstract class ShowOnlineStaffComponent
{
    public String name;

    public String getName()
    {
        return name;
    }

    public ShowOnlineStaffComponent(String name)
    {
        this.name = name;
    }

    public abstract void show(int level);
}
