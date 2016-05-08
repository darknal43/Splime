package tools.ServerTools.databases;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import server.models.GameModel;
import tools.Utils;

import java.util.List;
import java.util.Map;

/**
 * Created by Hairuo on 2016-05-08.
 */
public abstract class DatabaseStructure {
    protected Map<Long, GameModel> data;
    protected Array<Actor> actorList;

    public DatabaseStructure(){
        this.data = Utils.newConcurrentMap();
    }

    public void addModel(GameModel model){
        data.put(model.getKey(), model);
    }

    public GameModel getModel(long key){
        return data.get(key);
    }
}
