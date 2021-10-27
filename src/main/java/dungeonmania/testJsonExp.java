package dungeonmania;

import dungeonmania.util.FileLoader;
import com.google.gson.*;
import java.util.Map;
import java.util.List;
import dungeonmania.util.Position;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;




public class testJsonExp {


    public static void main(String[] args) {

        String jsonString;


        try {
            // Json String
            jsonString = FileLoader.loadResourceFile("/dungeons/advanced.json");

            // Convert JSON String to Java map
            Map<String, Object> map = new Gson().fromJson(jsonString, Map.class);
            System.out.println("Hello");

            Map<String, String> goalConditions = (Map<String, String>)map.get("goal-condition");
            
            String delimiter = goalConditions.get("goal");
            System.out.println(delimiter);

            System.out.println((goalConditions.containsKey("subgoals")));
            //System.out.println((goalConditions.get("subgoals")));
            System.out.println(goalConditions.values());
            //System.out.println(goalConditions.values().getClass());
            List<Map<String, String>> check = (List<Map<String, String>>)Arrays.asList(goalConditions.values().toArray()).get(1);
            for (int i = 0; i < check.size(); i++) {
                System.out.println(check.get(i).get("goal"));
            }
            

            // System.out.println(goalConditions.get("subgoals").getClass().getSimpleName());

            // System.out.println(subgoals);
            // Map<String, Object> subgoalsRev = new Gson().fromJson(subgoals, Map.class);
            
        }
        catch (Exception IOException){
        }

    } 
       

}
