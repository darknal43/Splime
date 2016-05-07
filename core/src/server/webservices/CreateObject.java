package server.webservices;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.JsonReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.models.PlayerModel;
import tools.ServerTools.databases.LocalDatabase;
import tools.ServerTools.databases.LocalDatabaseFactory;


/**
 * A singleton used to request models from the server
 */
public class CreateObject implements Net.HttpResponseListener {
    private LocalDatabase localDatabase = LocalDatabaseFactory.createLocalDatabase();
    private JsonReader reader = new JsonReader();
    private ObjectMapper objectMapper = new ObjectMapper();
    private Object rOjbect;

    public static CreateObject newInstance(){
        return new CreateObject();

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
            rOjbect = objectMapper.readValue(json, PlayerModel.class);
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
