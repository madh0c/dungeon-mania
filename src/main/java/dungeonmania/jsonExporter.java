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
    String goals;


    public static Map<String, Entity> makeDungeonMap(String path) {

        String jsonString;
        Map<String, Entity> result = new HashMap<String, Entity>();

        try {
            // Json String
            jsonString = FileLoader.loadResourceFile("/dungeons/" + path + ".json");

            // Convert JSON String to Java map
            Map<String, Object> map = new Gson().fromJson(jsonString, Map.class);


			List<Map<String, String>> entities = (List<Map<String, String>>)map.get("entities"); 

            for (int i = 0; i < entities.size(); i++) {

                Map<String,String> currentEntity = entities.get(i);

                String entityType = currentEntity.get("type");

                String xString = currentEntity.get("x");
                int xCoord = Integer.parseInt(xString);

                String yString = currentEntity.get("y");
                int yCoord = Integer.parseInt(yString);

                Position position = new Position(xCoord, yCoord);

				if (entityType.equals("portal")) {
					String colour = currentEntity.get("colour");
					Portal portal = new Portal(position, colour);
					result.put(String.valueOf(i), portal);
				} else {
					EntityFactory init = new EntityFactory();

					Entity newEntity = init.createEntity(entityType, position);

					result.put(String.valueOf(i), newEntity);
				}
            }

            // CHANGE LATER
            Map<String, Object> goalConditions = (Map<String, Object>)map.get("goal-condition");

            String delimiter = (String) goalConditions.get("goal");

            List<Map<String, String>> subgoals = (List<Map<String, String>>) goalConditions.get("subgoals");		
			List<String> goals = new ArrayList<>();

            for (int i = 0; i < subgoals.size(); i++) {
				goals.add(subgoals.get(i).get("goal"));
            }


        } catch (Exception IOException) {

		}

        return result;
    }

}