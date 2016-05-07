package server.serverside;

import entities.GameEntity;
import tools.ServerTools.databases.VirtualDatabase;
import tools.ServerTools.databases.VirtualDatabaseFactory;
import tools.Utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Hairuo on 2016-05-06.
 */
public class ServerSideGame{

    private boolean running;
    private VirtualDatabase data;
    private ConcurrentHashMap<Long, GameEntity> actorMap;

    public ServerSideGame(){
        this.running = true;
        this.actorMap = Utils.newConcurrentMap();
        data = VirtualDatabaseFactory.createVirtualDatabase();
    }

    public void update(float delta){

        for(long i : actorMap.keySet()){
            actorMap.get(i).act(delta);
        }


    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public VirtualDatabase getData() {
        return data;
    }

    public void setData(VirtualDatabase data) {
        this.data = data;
    }

    public ConcurrentHashMap<Long, GameEntity> getActorMap() {
        return actorMap;
    }

    public void setActorMap(ConcurrentHashMap<Long, GameEntity> actorMap) {
        this.actorMap = actorMap;
    }
}
