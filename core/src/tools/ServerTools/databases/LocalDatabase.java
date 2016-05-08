package tools.ServerTools.databases;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import driver.GameLoopFactory;
import entities.GameEntity;
import server.models.GameModel;
import state.screens.AbstractScreen;
import tools.Utils;
import tools.hitboxes.ConicHitbox;

import java.lang.reflect.InvocationTargetException;
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
        String className = model.getClassName();
        GameEntity entity = null;
        try {
            entity = (GameEntity)Class.forName(className).getDeclaredMethod("initiateFromModel", model.getClass()).invoke(model);
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if(data.containsKey(model.getKey())){

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
