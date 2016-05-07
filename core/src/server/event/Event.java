package server.event;

import server.serverside.GameFactory;
import server.serverside.ServerSideGame;
import tools.ServerTools.databases.VirtualDatabase;
import tools.ServerTools.databases.VirtualDatabaseFactory;

/**
 * Created by Hairuo on 2016-05-06.
 */
public abstract class Event {

    protected VirtualDatabase database;
    protected ServerSideGame game;

    public Event(){
        this.database = VirtualDatabaseFactory.createVirtualDatabase();
        this.game = GameFactory.getGame();
    }

    public abstract void execute();

}
