package tools.ServerTools.databases;

import com.badlogic.gdx.scenes.scene2d.Stage;
import driver.GameLoopFactory;
import server.models.GameModel;
import state.screens.AbstractScreen;
import tools.Utils;

import java.util.concurrent.ConcurrentHashMap;

/**A storage object of all model types the user client will need.
 *
 *
 * Created by Hongyu Wang on 3/19/2016.
 */
public class LocalDatabase {

    private ConcurrentHashMap<Long, GameModel> models;
    private Stage gameStage;
    public static String ipAddress = "localhost";

    LocalDatabase(){
        models = Utils.newConcurrentMap();
        this.gameStage = ((AbstractScreen)(GameLoopFactory.getMainGameLoop().getScreen())).getStage();
    }

    public <E extends GameModel> E getModel(long key){
        try {
            return (E) models.get(key);
        }
        catch(ClassCastException e){
            return null;
        }
    }

    public void updateModel(GameModel model){
        models.put(model.getKey(), model);
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
            models.put(model.getKey(), model);
        }
        PostObject.newInstance().addModel(modelList.toArray(new GameModel[modelList.size()]));
    }
    **/

}
