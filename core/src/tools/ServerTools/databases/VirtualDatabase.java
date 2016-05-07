package tools.ServerTools.databases;

import server.models.GameModel;
import tools.ServerTools.generators.SerialGenerator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/** *
 * Created by Kevin Zheng on 2016-03-09.
 */
public class VirtualDatabase {
    public Map<Long, GameModel> data;
    private Map<String, Long> hashtag_key;
    private Map<String, Long> username_key;
    private List<Long> updatedKeys;
    public SerialGenerator generator = SerialGenerator.getGenerator();
    public SerialGenerator HVGenerator = SerialGenerator.getHGenerator(1000);

    public VirtualDatabase(){
        init();
    }

    public void init()
    {
        this.username_key = new ConcurrentHashMap<>();
        this.hashtag_key = new ConcurrentHashMap<>();
        this.data = new ConcurrentHashMap<>();
        this.updatedKeys = new ArrayList<>();
    }





    /**Gets a model from the database.
     *
     * @param key   The key of the model.
     * @return
     */
    public GameModel getModel(long key){
        return data.get(key);
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
        updatedKeys.add(newModel.getKey());

    }

    public List<Long> getUpdatedKeys() {
        return updatedKeys;
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

}
