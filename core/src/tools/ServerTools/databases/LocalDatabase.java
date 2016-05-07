//package tools.ServerTools.databases;
//
//import com.badlogic.gdx.Game;
//import driver.GameLoop;
//import server.models.GameModel;
//import server.webservices.PostObject;
//import server.webservices.RequestObject;
//import tools.ServerTools.generators.SerialGenerator;
//import tools.Utils;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentSkipListSet;
//
///**A storage object of all model types the user client will need.
// *
// *
// * Created by Hongyu Wang on 3/19/2016.
// */
//public class LocalDatabase {
//    private long user;
//    private int pullCount;
//    private Set<Long> pulledKeys;
//    private Map<Long, GameModel> models;
//    private Map<String, Long> hashtag_key;
//    private Map<String, Long> username_key;
//    private SerialGenerator generator;
//
//    public static String ipAddress = "localhost";
//
//
//
//    LocalDatabase(){
//        pullCount = 0;
//        models = Utils.newMap();
//        hashtag_key = Utils.newMap();
//        username_key = Utils.newMap();
//        pulledKeys = new ConcurrentSkipListSet<>();
//    }
//
//
//
//
//    public <E extends GameModel> E getModel(long key){
//        try {
//            return (E) models.get(key);
//        }
//        catch(ClassCastException e){
//            return null;
//        }
//    }
//
//    /**Pushes the model into the database.
//     *
//     * If it fails to send it to the server the system will return false;
//     *
//     * @param modelList The new model.
//     * @return          True if it sucessfully pushed to server.
//     */
//    public void pushModel(List<GameModel> modelList){
//        for(GameModel model: modelList){
//            models.put(model.getKey(), model);
//        }
//        PostObject.newInstance().addModel(modelList.toArray(new GameModel[modelList.size()]));
//    }
//
//    public void setModel(GameModel model){
//        models.put(model.getKey(), model);
//    }
//}
