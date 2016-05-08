package tools.ServerTools.databases;

import com.badlogic.gdx.scenes.scene2d.Stage;
import driver.GameLoopFactory;
import entities.GameEntity;
import server.models.GameModel;
import state.screens.AbstractScreen;
import tools.Utils;

import java.util.concurrent.ConcurrentHashMap;

/**A storage object of all model types the user client will need.
 *
 *
 * Created by Hongyu Wang on 3/19/2016.
 */
public class LocalDatabase extends DatabaseStructure{

    public static String ipAddress = "localhost";

    LocalDatabase(){
        super();
    }

    @Override
    public void addModel(GameModel model) {

        GameEntity entity = new GameEntity(model);
        if(data.containsKey(model.getKey())){
            if(entity instanceof )
            ((AbstractScreen)(GameLoopFactory.getMainGameLoop().getScreen())).getStage();
        }

        data.put(model.getKey(), model);
        actorMap.put(model.getKey(), entity);


    }

    /**Pushes the model into the database.
     *
     * If it fails to send it to the server the system will return false;
     *
     * @param modelList The new model.
     * @return          True if it sucessfully pushed to server.
     */
    /**
    public void pushModel(List<GameModel> modelList){
        for(GameModel model: modelList){
            data.put(model.getKey(), model);
        }
        PostObject.newInstance().addModel(modelList.toArray(new GameModel[modelList.size()]));
    }
    **/

}
