package dungeonmania;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import dungeonmania.util.*;

import java.util.HashMap;

public class Dungeon {

	private int id;
	private String name;
	private List<Entity> inventory;
    private Map<String, Entity> entities;
    private String gameMode;
    private String goals;


    public Dungeon(int id, String name, Map<String, Entity> entities, String gameMode, String goals) {
		this.id = id;
		this.name = name;	
		this.inventory = new ArrayList<>();	
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

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Entity> getInventory() {
		return inventory;
	}

    public String getGameMode() {
        return gameMode;
    }

    public String getGoals() {
        return goals;
    }

	public Map<String, Entity> getAllEntities() {
		return entities;
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

	public List<String> getBuildables() {
		List<String> result = new ArrayList<String>();

		int wood = 0;
		int arrow = 0;
		int treasure = 0;
		int key = 0;

		for (Entity item : inventory) {
			if (item.getType() == "wood") {
				wood++;
			}

			if (item.getType() == "arrow") {
				arrow++;
			}

			if (item.getType() == "treasure") {
				treasure++;
			}

			if (item.getType() == "key") {
				key++;
			}
		}

		if (wood >= 1 && arrow >= 3) {
			result.add("bow");
		}

		if (wood >= 2 && (treasure >= 1 || key >= 1)) {
			result.add("shield");
		}

		return result;
	}
}
