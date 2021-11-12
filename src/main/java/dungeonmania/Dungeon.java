package dungeonmania;

import java.util.List;

import org.json.JSONObject;

import java.util.ArrayList;

import dungeonmania.allEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.*;

public class Dungeon {
	private int id;
	private String name;
	private List<CollectableEntity> inventory;
    private List<Entity> entities;
    private String gameMode;
    private String goals;
	private int historicalEntCount;
	private int tickNumber;
	private Position spawnpoint;
	private int height;
	private int width;
	private GoalNode foundGoals;
	private String goalConditions;
	private EntityFactory factory;
	private int mercSpawnrate;
	private String rewindPath;


    public Dungeon(int id, String name, List<Entity> entities, String gameMode, String goals, int height, int width, GoalNode foundGoals, String goalConditions) {
		this.id = id;
		this.name = name;	
		this.inventory = new ArrayList<>();	
        this.entities = entities;
        this.gameMode = gameMode;
        this.goals = goals;
		this.historicalEntCount = entities.size();
		this.foundGoals = foundGoals;
		this.goalConditions = goalConditions;
		// Initialise gamemode
		if (gameMode.equals("Peaceful")) {
			this.factory = new PeacefulFactory();
			this.mercSpawnrate = 20;
		} else if (gameMode.equals("Standard")) {
			this.factory = new StandardFactory();
			this.mercSpawnrate = 20;
		} else if (gameMode.equals("Hard")) {
			this.factory = new HardFactory();
			this.mercSpawnrate = 10;
		}
		this.height = height;
		this.width = width;
    }

	/**
	 * @param itemString
	 * @return whether the item was used successfully
	 * @throws InvalidActionException
	 */
	public boolean useItem(String itemString) throws InvalidActionException {
		CollectableEntity itemUsed = null;
		for (CollectableEntity colllectable : inventory) {
			if (colllectable.getId().equals(itemString)) {
				itemUsed = colllectable;
			}
		}
		
		if (itemUsed instanceof HealthPotion) {
			getPlayer().setHealth(getPlayer().getInitialHealth());
			inventory.remove(itemUsed);
			return true;
		}
		if (itemUsed instanceof InvisibilityPotion) {
			getPlayer().setVisibility(false);
			inventory.remove(itemUsed);
			return true;
		}

		if (itemUsed instanceof InvincibilityPotion) {
			getPlayer().setInvincibleTickDuration(getPlayer().getInvincibleAmount());
			inventory.remove(itemUsed);
			return true;
		}

		if (itemUsed instanceof BombItem) {
			BombStatic bomb = new BombStatic(String.valueOf(historicalEntCount), getPlayerPosition());

			entities.add(bomb);
			setHistoricalEntCount(historicalEntCount + 1);

			inventory.remove(itemUsed);
			return true;
		}	
		return false;
	}
	
    public List<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity newEntity) {
        this.entities.add(newEntity);
		historicalEntCount++;
    }

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<CollectableEntity> getInventory() {
		return inventory;
	}

    public String getGameMode() {
        return gameMode;
    }

    public String getGoals() {
        return foundGoals.remainingString();
    }

	public GoalNode getFoundGoals() {
		return foundGoals;
	}

	public void setFoundGoals(GoalNode foundGoals) {
		this.foundGoals = foundGoals;
	}

	public String getGoalConditions() {
		return this.foundGoals.saveGameJSON().toString();
	}
	public EntityFactory getFactory() {
		return factory;
	}

	public Entity getEntity(String id) {
		int entId = 0;
		for (Entity entity : getEntities()) {
			if (entity.getId().equals(id)) {
				entId = getEntities().indexOf(entity);
				return entities.get(entId);
			}
		}
		return null;
	}

	public void setInventory(List<CollectableEntity> inventory) {
		this.inventory = inventory;
	}

	public Entity getEntity(Position position) {
		for (Entity entity : getEntities()) {
			if (entity.getPosition().equals(position) && entity.getPosition().getLayer() == position.getLayer()) {
				return entity;
			}
		}
		return null;
	}

	public Entity getEntity(String type, Position position) {
		for (Entity entity : getEntities()) {
			if (entity.getPosition().equals(position) && entity.getType().equals(type)) {
				return entity;
			}
		}
		return null;
	}
	
	/**
	 * get all the entities on a 
	 * @param cell
	 * @return
	 */
	public List<Entity> getEntitiesOnCell(Position cell) {
		List<Entity> result = new ArrayList<>();
		for (Entity entity : getEntities()) {
			if (entity.getPosition().getX() == cell.getX() && entity.getPosition().getY() == cell.getY()) {
				result.add(entity);
			}
		}
		return result;
	}

	public int getTickNumber() {
		return tickNumber;
	}

	public int getMercSpawnrate() {
		return mercSpawnrate;
	}

	public String getRewindPath() {
		return rewindPath;
	}

	public void setRewindPath(String rewindPath) {
		this.rewindPath = rewindPath;
	}

	public Position getSpawnpoint() {
		return spawnpoint;
	}

	public int getMinX() {
		int retInt = 1000;
		for (Entity ent : getEntities()) {
			if (ent.getPosition().getX() < retInt) {
				retInt = ent.getPosition().getX();
			}
		} return retInt;
	}

	public int getMaxX() {
		int retInt = -1000;
		for (Entity ent : getEntities()) {
			if (ent.getPosition().getX() > retInt) {
				retInt = ent.getPosition().getX();
			}
		} return retInt;
	}

	public int getMinY() {
		int retInt = 1000;
		for (Entity ent : getEntities()) {
			if (ent.getPosition().getY() < retInt) {
				retInt = ent.getPosition().getY();
			}
		} return retInt;
	}

	public int getMaxY() {
		int retInt = -1000;
		for (Entity ent : getEntities()) {
			if (ent.getPosition().getY() > retInt) {
				retInt = ent.getPosition().getY();
			}
		} return retInt;
	}

	public void setSpawnpoint(Position spawnpoint) {
		this.spawnpoint = spawnpoint;
	}

	/**
	 * Add 1 to the dungeon's current tick
	 */
	public void tickOne() {
		tickNumber++;
	}

	public void removeEntity(Entity entity) {
		getEntities().remove(entity);
	}

	public void addItemToInventory(CollectableEntity entity) {
		inventory.add(entity);
	}

	// Check if type exists regardless of position
	public boolean entityExists(String type) {
		for (Entity ent : getEntities()) {
			if (ent.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	// Check if something exists in position
	public boolean entityExists(Position position) {
		for (Entity ent : getEntities()) {
			if (ent.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}

	// Check if type exists in position
	public boolean entityExists(String type, Position position) {		
		for (Entity ent : getEntities()) {
			if (ent.getPosition().equals(position) && ent.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return player's curr position in the dungeon
	 */
	public Position getPlayerPosition() {
		for (Entity entity : getEntities()) {
			if (entity instanceof Player) {
				return entity.getPosition();
			}	
		}
		return null;
	}

	/**
	 * @return the player entity of a dungeon
	 */
	public Player getPlayer() {
		for (Entity entity : getEntities()) {
			if (entity instanceof Player) {
				return (Player)entity;
			}	
		}
		return null;
	}

	public List<String> getBuildables() {
		List<String> result = new ArrayList<String>();

		int wood = 0;
		int arrow = 0;
		int treasure = 0;
		int key = 0;
		int sunStone = 0;
		int armour = 0;

		for (Entity item : inventory) {
			if (item.getType().equals("wood")) {
				wood++;
			}

			if (item.getType().equals("arrow")) {
				arrow++;
			}

			if (item.getType().equals("treasure")) {
				treasure++;
			}

			if (item.getType().equals("key")) {
				key++;
			}

			if (item.getType().equals("sun_stone")) {
				sunStone++;
			}

			if (item.getType().equals("armour")) {
				armour++;
			}
		}

		if (wood >= 1 && arrow >= 3) {
			result.add("bow");
		}

		if (wood >= 2 && (treasure >= 1 || key >= 1)) {
			result.add("shield");
		}

		if ((wood >= 1 || arrow >= 2) && (key >= 1 || treasure >= 1) && (sunStone >= 1)) {
			result.add("sceptre");
		} 
		Boolean zombie = false;
		for (Entity ent : getEntities()) {
			if (ent instanceof ZombieToast) {
				zombie = true;
			}
		}
		if (armour >= 1 && sunStone >= 1 && zombie == false) {
			result.add("midnight_armour");
		}

		return result;
	}

	public int getHistoricalEntCount() {
		return this.historicalEntCount;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setHistoricalEntCount(int historicalEntCount) {
		this.historicalEntCount = historicalEntCount;
	}

	public void setTickNumber(int tickNumber) {
		this.tickNumber = tickNumber;
	}

	public void setGoals(String goals) {
		this.goals = goals;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}


	// public boolean equals(Object obj) {
	// 	if (this == null || obj == null) {
	// 		return false;
	// 	}

	// 	if (this == obj) {
	// 		return true;
	// 	} 

	// 	if (getClass() != obj.getClass()) {
	// 		return false;
	// 	}

	// 	Dungeon other = (Dungeon) obj;

	// 	if (id != other.getId()) {
	// 		return false;
	// 	}
		
	// 	if (name == null) {
	// 		if (other.getName() != null) {
	// 			return false;
	// 		}
	// 	} else if (!name.equals(other.getName())) {
	// 		return false;
	// 	}

	// 	if (inventory == null) {
	// 		if (other.getInventory() != null) {
	// 			return false;
	// 		}
	// 	} else if (!inventory.equals(other.getInventory())) {
	// 		return false;
	// 	}

	// 	if (entities == null) {
	// 		if (other.getEntities() != null) {
	// 			return false;
	// 		}
	// 	} else if (!entities.equals(other.getEntities())) {
	// 		return false;
	// 	}

	// 	if (gameMode == null) {
	// 		if (other.getGameMode() != null) {
	// 			return false;
	// 		}
	// 	} else if (!gameMode.equals(other.getGameMode())) {
	// 		return false;
	// 	}

	// 	if (goals == null) {
	// 		if (other.getGoals() != null) {
	// 			return false;
	// 		}
	// 	} else if (!goals.equals(other.getGoals())) {
	// 		return false;
	// 	}
	// 	return true;
	// }
}
