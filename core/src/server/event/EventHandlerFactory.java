package server.event;


/**
 * Created by Hairuo on 2016-05-06.
 */
public class EventHandlerFactory {
    private static EventHandler eventHandler;

    private EventHandlerFactory(){}

    public static EventHandler createEventHandler(){
        if(eventHandler == null){
            eventHandler = new EventHandler();
        }
        return eventHandler;
    }
}
