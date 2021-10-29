package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.FileLoader;
import com.google.gson.*;
import java.util.Map;
import java.util.List;
import dungeonmania.util.Position;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;	

public class jsonExporter {
	//	dungeonMap (Map<String, Entity>):
	// ("0", Player),
	// ("1", Portal),
	// ("2", Portal)
	//
    public static Dungeon makeDungeon(int id, String path, String gameMode) {

        String jsonString;
        Map<String, Entity> dungeonMap = new HashMap<String, Entity>();
        String goals = "";

        try {
            // Json String
            jsonString = FileLoader.loadResourceFile("/dungeons/" + path);

            // Convert JSON String to Java map
            Map<String, Object> map = new Gson().fromJson(jsonString, Map.class);


			List<Map<String, Object>> entities = (List<Map<String, Object>>)map.get("entities"); 

            for (int i = 0; i < entities.size(); i++) {

                Map<String, Object> currentEntity = entities.get(i);

                String entityType = (String)currentEntity.get("type");                

                Double xDouble = (Double)currentEntity.get("x");
                Double yDouble = (Double)currentEntity.get("y");
                int xCoord = xDouble.intValue();
                int yCoord = yDouble.intValue();

				int zCoord = 0;
                if (entityType.equals("switch")) {
                    zCoord = -1;
                }
                Position position = new Position(xCoord, yCoord, zCoord);
                
				if (entityType.equals("portal")) {
					String colour = (String)currentEntity.get("colour");
					Portal portal = new Portal(position, colour);
                    
					dungeonMap.put(String.valueOf(i), portal);
				} else {
					Entity newEntity = EntityFactory.createEntity(entityType, position);
					dungeonMap.put(String.valueOf(i), newEntity);
				}
            }

            // TODO: Extract Goals
            Map<String, Object> goalConditions = (Map<String, Object>)map.get("goal-condition");

            String delimiter = (String) goalConditions.get("goal");

            List<Map<String, String>> subgoals = (List<Map<String, String>>) goalConditions.get("subgoals");            
            
            for (int i = 0; i < subgoals.size(); i++) {
                if (i == 0) {
                    goals = subgoals.get(i).get("goal");
                } else {
                    goals = goals + delimiter + subgoals.get(i).get("goal");
                }			
            }

        } catch (Exception IOException) {

		}

        return new Dungeon(id, path, dungeonMap, gameMode, goals);
    }



}