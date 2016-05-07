//package server.webservices;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Net;
//import com.badlogic.gdx.utils.JsonReader;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import server.models.GameModel;
//import tools.ServerTools.databases.LocalDatabase;
//import tools.ServerTools.databases.LocalDatabaseFactory;
//import tools.ServerTools.generators.Tags;
//
//
///**
// * A singleton used to request models from the server
// */
//public class RequestObject implements Net.HttpResponseListener {
//    private LocalDatabase localDatabase = LocalDatabaseFactory.createLocalDatabase();
//    private JsonReader reader = new JsonReader();
//    private ObjectMapper objectMapper = new ObjectMapper();
//    private Object rOjbect;
//
//    public static RequestObject newInstance(){
//        return new RequestObject();
//
//    }
//
//    /**
//     * Retrieves a model from the server
//     *
//     * @param key       id key of the model
//     */
//    public void getModel(long key) {
//        // LibGDX NET CLASS
//        Net.HttpRequest httpGet = new Net.HttpRequest(Net.HttpMethods.GET);
//        httpGet.setUrl("http://"+ LocalDatabase.ipAddress+":8081/webservice/getServerModel/" + key);
//        //httpGet.setHeader("Content-Type", "application/json");
//        //httpGet.setHeader("X-Parse-Application-Id", app_id);
//        //httpGet.setHeader("X-Parse-REST-API-Key", app_key);
//        Gdx.net.sendHttpRequest(httpGet, this);
//    }
//
//    @Override
//    public void cancelled() {
//
//    }
//
//    @Override
//    public void handleHttpResponse(Net.HttpResponse httpResponse) {
//        try {
//            String json = httpResponse.getResultAsString();
////            System.out.println(tag+1);
////            System.out.println(className);
//            String[] objectList = objectMapper.readValue(json, String[].class);
//            for(String model : objectList) {
//                int tag = Integer.parseInt(model.substring(model.length() - 4));
//                String className = Tags.ID_TAGS.getName(tag);
//                json = model.substring(0, json.length() - 4);
//                rOjbect = objectMapper.readValue(json, Class.forName(className));
//                localDatabase.setModel((GameModel)rOjbect);
//
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    @Override
//    public void failed(Throwable t) {
//        System.out.println(t.getMessage());
//    }
//
//    public Object getrOjbect() {
//        return rOjbect;
//    }
//
//    public void setrOjbect(Object rOjbect) {
//        this.rOjbect = rOjbect;
//    }
//}
