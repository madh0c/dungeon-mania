package dungeonmania;

import java.util.Map;
import java.util.HashMap;

public class Dungeon {

    private Map<String, Entity> entities;
    private String gameMode;
    private String goals;


    public Dungeon(Map<String, Entity> entities, String gameMode, String goals) {
        this.entities = entities;
        this.gameMode = gameMode;
        this.goals = goals;
    }

    public Map<String, Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity newEntity) {
        int currentSize = this.entities.size();
        this.entities.put(String.valueOf(currentSize), newEntity);
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getGoals() {
        return goals;
    }
}
