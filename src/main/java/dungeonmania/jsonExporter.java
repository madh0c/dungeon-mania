package dungeonmania;

import dungeonmania.exportStrategyFiles.initialiseArrow;
import dungeonmania.util.FileLoader;
import com.google.gson.*;
import java.util.Map;
import java.util.List;
import dungeonmania.util.Position;
import dungeonmania.Dungeon;
import java.util.Map;
import java.util.HashMap;




public class jsonExporter {


    public Map<String, Entity> makeDungeonMap(String path) {

        String jsonString;
        Map<String, Entity> result = new HashMap<String, Entity>();



        try {
            // Json String
            jsonString = FileLoader.loadResourceFile(path);

            // Convert JSON String to Java map
            Map<String, Object> map = new Gson().fromJson(jsonString, Map.class);

            List<Map<String, String>> entities = (List<Map<String, String>>)map.get("entities");

            Map<String, String> goalConditions = (Map<String, String>)map.get("goal-condition");
            
            String delimiter = goalConditions.get("goal");
            
            String subgoals = (String)goalConditions.get("subgoals");
            Map<String, Object> subgoalsRev = new Gson().fromJson(subgoals, Map.class);
            
            String conditions = delimiter.valueOf("goal");

    

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
                        Wall newEntity = new Wall();
                        break;

                        // TODO ASK ADAM
                        /**
                         * 
                         * Call Constructor
                         * Add into result
                         */

                    case "exit":
                        initialiseExit(position, result);
                        break;
                    
                    case "boulder":
                        initialiseBoulder(position, result);
                        break;

                    case "switch":
                        initialiseSwitch(position, result);
                        break;

                    case "door":
                        initialiseDoor(position, result);
                        break;

                    case "portal":
                        String colour = currentEntity.get("colour");
                        initialisePortal(position, colour, result);
                        break;

                    case "zombie_toast_spawner":
                        initialiseZombieToastSpawner(position, result);
                        break;

                    case "spider":
                        initialiseSpider(position, result);
                        break;

                    case "zombie_toast":
                        initialiseZombieToast(position, result);
                        break;

                    case "mercenary":
                        initialiseMercenary(position, result);
                        break;

                    case "treasure":
                        initialiseTreasure(position, result);
                        break;

                    case "key":
                        initialiseKey(position, result);
                        break;

                    case "health_potion":
                        initialiseHealthPotion(position, result;
                        break;

                    case "invincibility_potion":
                        initialiseInvicibilityPotion(position, result);
                        break;

                    case "invisibility_potion":
                        initialiseInvisibilityPotion(position, result);
                        break;

                    case "wood":
                        initialiseWood(position, result);
                        break;

                    case "arrow":
                        initialiseArrow(position), result;
                        break;

                    case "bomb":
                        initialiseBomb(position, result);
                        break;

                    case "sword":
                        initialiseSword(position, result);
                        break;

                }
                public void exportStrategy(position, yCoord);
            }

        }
        catch (Exception IOException){

        }

        return map;
    }

}