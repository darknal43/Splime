package server.webservices;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.models.GameModel;
import tools.ServerTools.databases.LocalDatabase;
import tools.ServerTools.databases.LocalDatabaseFactory;
import tools.ServerTools.generators.Tags;


/**
 * A singleton used to request models from the server
 */
public class UpdateObject implements Net.HttpResponseListener {
    private LocalDatabase localDatabase = LocalDatabaseFactory.createLocalDatabase();
    private ObjectMapper objectMapper = new ObjectMapper();
    private Object rOjbect;

    public static UpdateObject newInstance(){
        return new UpdateObject();

    }

    public void updateAll(){
        Net.HttpRequest httpGet = new Net.HttpRequest(Net.HttpMethods.GET);
        httpGet.setUrl("http://"+ LocalDatabase.ipAddress+":8081/webservice/updateAll");
        Gdx.net.sendHttpRequest(httpGet, this);
    }

    @Override
    public void cancelled() {

    }

    @Override
    public void handleHttpResponse(Net.HttpResponse httpResponse) {
        try {
            String json = httpResponse.getResultAsString();
            String[] objectList = objectMapper.readValue(json, String[].class);
            for(String model : objectList) {
                int tag = Integer.parseInt(model.substring(model.length() - 4));
                String className = Tags.ID_TAGS.getName(tag);
                json = model.substring(0, json.length() - 4);
                rOjbect = objectMapper.readValue(json, Class.forName(className));
                localDatabase.addModel((GameModel)rOjbect);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void failed(Throwable t) {
        System.out.println(t.getMessage());
    }

    public Object getrOjbect() {
        return rOjbect;
    }

    public void setrOjbect(Object rOjbect) {
        this.rOjbect = rOjbect;
    }
}
