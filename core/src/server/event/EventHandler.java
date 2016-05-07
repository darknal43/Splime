package server.event;

import tools.Utils;

import java.util.List;

/**
 * Created by Hairuo on 2016-05-06.
 */
public class EventHandler{
    private List<Event> events;

    public EventHandler(){
        this.events = Utils.newList();
    }


    public void executeAll(){
        for (Event event : events){
            event.execute();
        }
    }

    public void add(Event event){
        events.add(event);
    }

    public void clear(){
        events = Utils.newList();
    }


}
