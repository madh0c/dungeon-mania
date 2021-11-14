package dungeonmania;

import java.util.List;
import java.util.Random;

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
	private GoalNode foundGoals;
	private String goalConditions;
	private EntityFactory factory;
	private int mercSpawnrate;
	private int spiderSpawnrate;
	private String rewindPath;


    public Dungeon(int id, String name, List<Entity> entities, String gameMode, String goals, GoalNode foundGoals, String goalConditions) {
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
		if (gameMode.equals("peaceful")) {
			this.factory = new PeacefulFactory();
			this.mercSpawnrate = 20;
			this.spiderSpawnrate = 20;
		} else if (gameMode.equals("standard")) {
			this.factory = new StandardFactory();
			this.mercSpawnrate = 20;
			this.spiderSpawnrate = 20;
		} else if (gameMode.equals("hard")) {
			this.factory = new HardFactory();
			this.mercSpawnrate = 10;
			this.spiderSpawnrate = 15;
		}
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
	 * Get all the entities on a cell, regardless of the layer of the Entity
	 * @param cell	Position of cell
	 * @return	List<Entity> of entities on cell
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

	/**
	 * Returns the current tick of the dungeon
	 * @return	tickNumber
	 */
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

			if (item.getType().contains("key")) {
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

	public boolean getMidnightStatus() {
		for (Entity ent : getInventory()) {
			if (ent instanceof MidnightArmour) {
				return true;
			}
		} return false;
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

	/**
	 * Spawn in a mercenary at the spawnpoint at certain intervals depending on gamemode
	 * <ul><li>Peaceful: Every 20 ticks
	 * <li>Standard: Every 20 ticks
	 * <li>Hard: Every 10 ticks</ul>
	 */
	public void spawnMerc() {
		// Check if correct tick to spawn merc
		if (getTickNumber() % getMercSpawnrate() != 0 || getTickNumber() == 0) {
			return;
		}

		// If there is a spawnpoint
		if (getSpawnpoint() != null) {
			// Merc spawn every 10/20 ticks
			int newId = getHistoricalEntCount();
			Mercenary newMerc = (Mercenary)getFactory().createEntity(String.valueOf(newId), "mercenary", getSpawnpoint());
			
			for (Entity entity : getEntitiesOnCell(getSpawnpoint())) {
				if (!newMerc.collide(entity, this)) {
					return;
				}
			}
			
			Random rand = new Random();
			int random = rand.nextInt(10);
			
			if (random < 2) {
				Entity newAssassin = getFactory().createEntity(String.valueOf(newId), "assassin", getSpawnpoint());
				addEntity(newAssassin);
			} else {
				addEntity(newMerc);
			}			
			return;				
		}
		return;
	}

	/**
	 * Spawn in a spider<ul>
	 * <li> Peaceful: 20 ticks
	 * <li> Standard: 20 ticks
	 * <li> Hard: 20 ticks
	 * </ul>
	 */
	public void spiderSpawn() {
		// Ignore if not the right number of ticks
		if (getTickNumber() % spiderSpawnrate != 0) return;

		// Random spawnpoint
		Position rngSpawn;
		boolean flag = false;
		// Generate random spawnpoint until it is a valid one
		do {
			rngSpawn = randomSpawnpoint();
			for (Entity ent : getEntitiesOnCell(rngSpawn)) {
				if (!Spider.spawnCollide(ent)) {
					flag = true;
					break;
				}
			}
		} while (flag);

		factory.createSpider(gameMode, rngSpawn);

	}
	

	/**
	 * Returns a random spawnpoint on the dungeon
	 * @return	Position of random spawnpoint
	 */
	private Position randomSpawnpoint() {
		Random rng = new Random();
		// max: getMaxX()
		// min: getMinX()
		int randX = rng.nextInt(getMaxX() - getMinX()) + getMinX();

		// max: getMaxY()
		// min: getMaxX()
		int randY = rng.nextInt(getMaxY() - getMinY()) + getMinY();

		Position ret = new Position(randX, randY);

		return ret;
	}

	// private boolean validSpiderSpawnpoint(Position pos) {
	// 	Spider temp = new Spider(gameMode, pos, false);
	// 	for (Entity ent : getEntitiesOnCell(pos)) {
	// 		if (!Spider.spawnCollide(ent)) {
	// 			return false;
	// 		}
	// 	}
	// 	return 
	// }


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
