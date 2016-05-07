package server.event;

import entities.Player;
import tools.ServerTools.databases.VirtualDatabase;
import tools.ServerTools.databases.VirtualDatabaseFactory;

/**
 * Created by Hairuo on 2016-05-06.
 */
public abstract class Event {

    private VirtualDatabase database;
    private Player player;

    public Event(int i){
        this.database = VirtualDatabaseFactory.createVirtualDatabase();
        //TODO set player

    }

    public abstract void execute();

}
