package dungeonmania;

// import dungeonmania.allEntities.*;
// import dungeonmania.util.FileLoader;
// import com.google.gson.*;
// import java.util.Map;
// import java.util.List;
// import dungeonmania.util.Position;
// import java.util.Map;
// import java.util.Arrays;
// import java.util.HashMap;

public class testJsonExp {
    // public static void main(String[] args) {

    //     String jsonString;
    //     Map<String, Entity> result = new HashMap<String, Entity>();

    //     try {
    //         // Json String
    //         jsonString = FileLoader.loadResourceFile("/dungeons/portals.json");


    //         // Convert JSON String to Java map
    //         Map<String, Object> map = new Gson().fromJson(jsonString, Map.class);
    //         List<Map<String, Object>> entities = (List<Map<String, Object>>)map.get("entities");             


    //         for (int i = 0; i < entities.size(); i++) {

    //             Map<String, Object> currentEntity = entities.get(i);

    //             String entityType = (String)currentEntity.get("type");                

    //             Double xDouble = (Double)currentEntity.get("x");
    //             Double yDouble = (Double)currentEntity.get("y");
    //             int xCoord = xDouble.intValue();
    //             int yCoord = yDouble.intValue();

	// 			int zCoord = 0;
    //             if (entityType.equals("switch")) {
    //                 zCoord = -1;
    //             }
    //             Position position = new Position(xCoord, yCoord, zCoord);
                
	// 			if (entityType.equals("portal")) {
	// 				String colour = (String)currentEntity.get("colour");
	// 				Portal portal = new Portal(position, colour);
                    
	// 				result.put(String.valueOf(i), portal);
	// 			} else {
	// 				Entity newEntity = EntityFactory.createEntity(entityType, position);
	// 				result.put(String.valueOf(i), newEntity);
	// 			}
    //         }
            

    //     }
    //     catch (Exception IOException){
    //     }

    // } 
       

}