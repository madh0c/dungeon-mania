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

    public static Map<String, Entity> makeDungeonMap(String path) {

        String jsonString;
        Map<String, Entity> result = new HashMap<String, Entity>();



        try {
            // Json String
            jsonString = FileLoader.loadResourceFile("/dungeons/" + path + ".json");

            // Convert JSON String to Java map
            Map<String, Object> map = new Gson().fromJson(jsonString, Map.class);
            System.out.println("Hello");

            Map<String, Object> goalConditions = (Map<String, Object>)map.get("goal-condition");

            String delimiter = (String) goalConditions.get("goal");
            System.out.println(delimiter);

            List<Map<String, String>> check = (List<Map<String, String>>) goalConditions.get("subgoals");

			// CHANGE LATER
			List<String> goals = new ArrayList<>();

            for (int i = 0; i < check.size(); i++) {
				goals.add(check.get(i).get("goal"));
            }

			List<Map<String, String>> entities = (List<Map<String, String>>)map.get("entities");
		
    


            for (int i = 0; i < entities.size(); i++) {

                Map<String,String> currentEntity = entities.get(i);

                String entityType = currentEntity.get("type");

                String xString = currentEntity.get("x");
                int xCoord = Integer.parseInt(xString);

                String yString = currentEntity.get("y");
                int yCoord = Integer.parseInt(yString);

                Position position = new Position(xCoord, yCoord);

				// entityType == "sword"
				// IntialiseEntity(entityType);

                switch (entityType) {
                    
                    /**
                     * Potential Strategy Pattern.
                     */
                    case "wall":
						Wall wall = new Wall(position);
						result.put(String.valueOf(i), wall);
						break;

            		case "exit":
						Exit exit = new Exit(position);
						result.put(String.valueOf(i), exit);
						break;
                    
                    case "boulder":
						Boulder boulder = new Boulder(position);
						result.put(String.valueOf(i), boulder);
						break;

                    case "switch":
						// cant have switch variable name
						Switch switch_ = new Switch(position);
						result.put(String.valueOf(i), switch_);
						break;

                    case "door":
						Door door = new Door(position);
						result.put(String.valueOf(i), door);
						break;

                    case "portal":
						String colour = currentEntity.get("colour");
						Portal portal = new Portal(position, colour);
						result.put(String.valueOf(i), portal);
						break;

                    case "zombie_toast_spawner":
						ZombieToastSpawner spawner = new ZombieToastSpawner(position);
						result.put(String.valueOf(i), spawner);
						break;

                    case "spider":
						Spider spider = new Spider(position);
						result.put(String.valueOf(i), spider);
						break;

                    case "zombie_toast":
						ZombieToast zombie = new ZombieToast(position);
						result.put(String.valueOf(i), zombie);
						break;

                    case "mercenary":
						Mercenary merc = new Mercenary(position);
						result.put(String.valueOf(i), merc);
						break;

                    case "treasure":
						Treasure treasure = new Treasure(position);
						result.put(String.valueOf(i), treasure);
						break;

                    case "key":
						Key key = new Key(position);
						result.put(String.valueOf(i), key);
						break;

                    case "health_potion":
						HealthPotion health = new HealthPotion(position);
						result.put(String.valueOf(i), health);
						break;

                    case "invincibility_potion":
						InvincibilityPotion invic = new InvincibilityPotion(position);
						result.put(String.valueOf(i), invic);
						break;

                    case "invisibility_potion":
						InvisibilityPotion invis = new InvisibilityPotion(position);
						result.put(String.valueOf(i), invis);
						break;

                    case "wood":
						Wood wood = new Wood(position);
						result.put(String.valueOf(i), wood);
						break;

                    case "arrow":
						Arrow arrow = new Arrow(position);
						result.put(String.valueOf(i), arrow);
						break;

                    case "bomb":
						Bomb bomb = new Bomb(position);
						result.put(String.valueOf(i), bomb);
						break;

                    case "sword":
						Sword sword = new Sword(position);
						result.put(String.valueOf(i), sword);
						break;

                }
            }

        } catch (Exception IOException) {

		}

        return result;
    }

}