package dungeonmania;

import dungeonmania.util.FileLoader;
import com.google.gson.*;
import java.util.Map;
import java.util.List;
import dungeonmania.util.Position;
import java.util.Map;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class testJsonExp {

    public static void main(String[] args) {
        String jsonString;
        try {
            // Json String
			jsonString = FileLoader.loadResourceFile("/dungeons/advanced.json");
			
            // Convert JSON String to Java map
        	Map<String, Object> map = new Gson().fromJson(jsonString, Map.class);
            Map<String, Object> goalConditions = (Map<String, Object>)map.get("goal-condition");
            
            String delimiter = (String) goalConditions.get("goal");
            System.out.println(delimiter);

            List<Map<String, String>> check = (List<Map<String, String>>) goalConditions.get("subgoals");
        
            for (int i = 0; i < check.size(); i++) {
                System.out.println(check.get(i).get("goal"));
            }
        }
        catch (Exception IOException){
        }

    }   
}
