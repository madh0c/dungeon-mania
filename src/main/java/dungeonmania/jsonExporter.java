package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.FileLoader;
import com.google.gson.*;
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
					dungeonMap.put(String.valueOf(i), newEntity);
				}
            }

			// "goal-condition": {
			// 	"goal": "AND",
			// 	"subgoals": [
			// 	  {
			// 		"goal": "AND",
			//		"subgoals" :[
			// 		{
			// 			"goal": "spider"
			// 		},
			// 		{
			// 			"goal": "zombie"
			// 		}
			//		]
			//		},
			// 	  {
			// 		"goal": "treasure"
			// 	  }
			// 	]
			//   }


			// "goal-condition": {
			// 	"goal": "AND",
			// 	"subgoals": [
			// 	  {
			// 		"goal": "enemies"
			// 	  },
			// 	  {
			// 		"goal": "treasure"
			// 	  }
			// 	]
			//   }
			// subgoals is a List<Map<String, Object>>

			//                Map<String, Object> currentEntity = entities.get(i);

			// String entityType = (String)currentEntity.get("type");                

			// Double xDouble = (Double)currentEntity.get("x");
			// Double yDouble = (Double)currentEntity.get("y");
            // TODO: Extract Goals

            Map<String, Object> goalConditions = (Map<String, Object>)map.get("goal-condition");

            // String delimiter = (String) goalConditions.get("goal");

            // List<Map<String, String>> subgoals = (List<Map<String, String>>) goalConditions.get("subgoals");            
            
            // for (int i = 0; i < subgoals.size(); i++) {
            //     if (i == 0) {
            //         goals = subgoals.get(i).get("goal");
            //     } else {
            //         goals = goals + delimiter + subgoals.get(i).get("goal");
            //     }			
            // }

			GoalGoal goal = new GoalGoal(goalConditions.get("goal"));
			String delim = goal.evaluate();

			List<Map<String, Object>> sgoal = (List<Map<String, Object>>)goalConditions.get("subgoals");
			Map<String, Object> sgoal1 = sgoal.get(0);
			Map<String, Object> sgoal2 = sgoal.get(1);

			GoalSub subgoal1 = new GoalSub(sgoal1);
			GoalSub subgoal2 = new GoalSub(sgoal2);
			String subGoal1 = subgoal1.evaluate();
			String subGoal2 = subgoal2.evaluate();
			System.out.println(subGoal1 + " " + delim + " " + subGoal2);

        } catch (Exception IOException) {
			System.out.println("error");

		}
        return new Dungeon(id, path, dungeonMap, gameMode, goals);
    }

	public static void main(String[] args) {
		makeDungeon(0, "goals.json", "standard");
	}


}