package Client.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class EventsPublisher
{
    protected List<IEventSubscriber> _subscribers = new ArrayList<IEventSubscriber>();

    public void addSubscriber(IEventSubscriber subscriber)
    {
        _subscribers.add(subscriber);
    }

    public void deleteSubscriber(IEventSubscriber subscriber)
    {
        _subscribers.remove(subscriber);
    }

    public void notifySubscriber()
    {
        for(IEventSubscriber subscriber : _subscribers)
        {
            subscriber.update();
        }
    }
}
