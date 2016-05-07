package tools.ServerTools.databases;

import server.models.GameModel;
import server.models.PlayerModel;
import tools.ServerTools.generators.SerialGenerator;
import tools.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/** *
 * Created by Kevin Zheng on 2016-03-09.
 */
public class VirtualDatabase {
    private Map<Long, GameModel> data;
    private SerialGenerator generator = SerialGenerator.getGenerator();
    private PlayerModel availablePlayer;

    public VirtualDatabase(){
        init();
    }

    public void init()
    {
        this.data = Utils.newConcurrentMap();
    }

    /**Gets a model from the database.
     *
     * @param key   The key of the model.
     * @return
     */
    public GameModel getModel(long key){
        return data.get(key);
    }

    public void addModel(GameModel model){
        data.put(model.getKey(), model);
    }

    /**Sets a model into the database.
     *
     * @param newModel The model.
     */
    public void setModel(GameModel newModel){
        if(data.containsKey(newModel.getKey())) {
            Field[] newFields = newModel.getClass().getDeclaredFields();
            GameModel oldModel = data.get(newModel.getKey());
            try {
                for (int i = 0; i < newFields.length; i++) {

                    String newName = newFields[i].getName();
                    newName = newName.substring(0,1).toUpperCase()+newName.substring(1);


                    Method getMethodNew = newModel.getClass().getMethod("get"+newName);
                    Method getMethodOld = oldModel.getClass().getMethod("get"+newName);


                    Object newObj = getMethodNew.invoke(newModel);
                    Object oldObj = getMethodOld.invoke(oldModel);

                    Class objectClass = newObj.getClass();

                    if(objectClass.equals(ArrayList.class)){
                        objectClass = List.class;
                    }

                    if (newObj instanceof List) {
                        Method setMethodNew = newModel.getClass().getMethod("set"+newName, objectClass);
                        List valueOldList = (List) newObj;
                        List valueNewList = (List) oldObj;
                        List newMergedList = this.union(valueNewList, valueOldList);
                        setMethodNew.invoke(newModel, newMergedList);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        data.put(newModel.getKey(), newModel);

    }

    private <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }

    public Map<Long, GameModel> getData() {
        return data;
    }

    public PlayerModel getAvailablePlayer() {
        return availablePlayer;
    }
}
