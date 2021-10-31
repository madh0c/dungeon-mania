package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.FileLoader;
import com.google.gson.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;
import java.util.List;
import dungeonmania.util.Position;
import java.util.Map;
import java.io.IOException;
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
					newEntity.setId(String.valueOf(i));
					dungeonMap.put(String.valueOf(i), newEntity);
				}
            }

            Map<String, Object> goalConditions = (Map<String, Object>)map.get("goal-condition");
			if (!goalConditions.get("goal").equals("AND") && !goalConditions.get("goal").equals("OR")) {
				goals = (String)goalConditions.get("goal");
			} 
			JSONObject goalCon = new JSONObject(goalConditions);
			goals = createGoals(goalCon).remainingString();
			System.out.println(goals);

		

        } catch (Exception IOException) {
			System.out.println("error");

		}
        return new Dungeon(id, path, dungeonMap, gameMode, goals);
    }

	public static GoalNode createGoals (JSONObject goal) {
		String current = goal.getString("goal");
		if (current.equals("enemies")) {
			return new GoalEnemies(current);
		} else if (current.equals("exit")) {
			return new GoalExit(current);
		} else if (current.equals("treasure")) {
			return new GoalTreasure(current);
		} else if (current.equals("boulder")) {
			return new GoalBoulder(current);
		} else if (current.equals("AND")) {
			GoalAnd andGoal = new GoalAnd(current);
			JSONArray subGoals = goal.getJSONArray("subgoals");
			for (int i = 0; i < subGoals.length(); i++) {
				GoalNode subGoal = createGoals(subGoals.getJSONObject(i));
				andGoal.add(subGoal);
			}
			return andGoal;

		} else if (current.equals("OR")) {
			GoalOr orGoal = new GoalOr(current);
			JSONArray subGoals = goal.getJSONArray("subgoals");
			for (int i = 0; i < subGoals.length(); i++) {
				GoalNode subGoal = createGoals(subGoals.getJSONObject(i));
				orGoal.add(subGoal);
			}
			return orGoal;
		} 
		return null;
	}

	
	public static void main(String[] args) {
		makeDungeon(0, "goals.json", "standard");
	}
	

}