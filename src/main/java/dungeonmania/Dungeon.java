package dungeonmania;

import java.util.Map;

import dungeonmania.util.*;

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

	public Entity getEntity(String id) {
		return entities.get(id);
	}

	// Check if type exists regardless of position
	public boolean entityExists(String type) {
		return entities.keySet().contains(type);
	}

	// Check if something exists in position
	public boolean entityExists(Position position) {
		for (Entity ent : entities.values()) {
			if (ent.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}

	// Check if type exists in position
	public boolean entityExists(String type, Position position) {		
		for (Entity ent : entities.values()) {
			if (ent.getPosition().equals(position) && ent.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}
}
