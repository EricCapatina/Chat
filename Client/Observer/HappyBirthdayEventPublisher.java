package Client.Observer;

public class HappyBirthdayEventPublisher extends EventsPublisher
{
    private String _eventInfo;

    public void setEventInfo(String event)
    {
        _eventInfo = event;
        this.notifySubscriber();
    }

    public String getEventInfo()
    {
        return _eventInfo;
    }
}
