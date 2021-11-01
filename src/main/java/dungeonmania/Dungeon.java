package dungeonmania;

import java.util.Map;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import dungeonmania.allEntities.BombItem;
import dungeonmania.allEntities.BombStatic;
import dungeonmania.allEntities.HealthPotion;
import dungeonmania.allEntities.InvincibilityPotion;
import dungeonmania.allEntities.InvisibilityPotion;
import dungeonmania.allEntities.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.*;

import java.util.HashMap;

public class Dungeon implements Serializable{

	private int id;
	private String name;
	private List<CollectibleEntity> inventory;
    private Map<String, Entity> entities;
    private String gameMode;
    private String goals;
	private int historicalEntCount;
	private int tickNumber;
	private Position spawnpoint;
	private Mode mode;


    public Dungeon(int id, String name, Map<String, Entity> entities, String gameMode, String goals) {
		this.id = id;
		this.name = name;	
		this.inventory = new ArrayList<>();	
        this.entities = entities;
        this.gameMode = gameMode;
        this.goals = goals;
		this.historicalEntCount = entities.size();
		// Initialise gamemode
		mode = new StandardMode();
		if (gameMode.equals("Peaceful")) {
			mode = new PeacefulMode();
		} else if (gameMode.equals("Standard")) {
			mode = new StandardMode();
		} else if (gameMode.equals("Hard")) {
			mode = new HardMode();
		}
    }

	/**
	 * @param itemString
	 * @return whether the item was used successfully
	 * @throws InvalidActionException
	 */
	public boolean useItem(String itemString) throws InvalidActionException {
		CollectibleEntity itemUsed = null;
		for (CollectibleEntity collectible : inventory) {
			if (collectible.getType().equals(itemString)) {
				itemUsed = collectible;
			}
		}
		
		if (itemUsed instanceof HealthPotion) {
			getPlayer().setHealth(mode.getHealth());
			inventory.remove(itemUsed);
			return true;
		}
		if (itemUsed instanceof InvisibilityPotion) {
			getPlayer().setVisibility(false);
			inventory.remove(itemUsed);
			return true;
		}

		if (itemUsed instanceof InvincibilityPotion) {
			getPlayer().setInvincibleTickDuration(mode.getInvincDuration());
			inventory.remove(itemUsed);
			return true;
		}

		if (itemUsed instanceof BombItem) {
			
			BombStatic bomb = new BombStatic(getPlayerPosition());

			int id = getHistoricalEntCount();
            bomb.setId(String.valueOf(id));
			entities.put(String.valueOf(id), bomb);
			setHistoricalEntCount(id++);

			inventory.remove(itemUsed);
			return true;
		}	
		return false;
	}
	/**
	 * returns a list of entity IDs surrounding an activated bomb which should be removed
	 * @param centre
	 * @return
	 */
	public List<String> toBeDetonated(Position centre) {

		List<String> result = new ArrayList<String>();
		for (Position adjacentPos : centre.getAdjacentPositions()) {
			for (Entity cellEnt : getEntitiesOnCell(adjacentPos)) {
				if (cellEnt != null && !(cellEnt instanceof Player)) {
					result.add(cellEnt.getId());
				}
			}	
		}
		
		for (Entity cellEnt : getEntitiesOnCell(centre)) {
			if (cellEnt != null && !(cellEnt instanceof Player)) {
				result.add(cellEnt.getId());
			}
		}
		return result;
	}
	
    public Map<String, Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity newEntity) {
        this.entities.put((String.valueOf(historicalEntCount)), newEntity);
		historicalEntCount++;
    }

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<CollectibleEntity> getInventory() {
		return inventory;
	}

    public String getGameMode() {
        return gameMode;
    }

	public Mode getMode() {
		return mode;
	}

    public String getGoals() {
        return goals;
    }

	public Entity getEntity(String id) {
		return entities.get(id);
	}

	public Entity getEntity(Position position) {
		for (Map.Entry<String, Entity> entry : getEntities().entrySet()) {
			Entity currentEntity = entry.getValue();
			if (
				currentEntity.getPosition().equals(position)
				&& currentEntity.getPosition().getLayer() == position.getLayer()			
			) {
				return currentEntity;
			}
		}
		return null;
	}

	public Entity getEntity(String type, Position position) {
		for (Map.Entry<String, Entity> entry : getEntities().entrySet()) {
			Entity currentEntity = entry.getValue();
			if (currentEntity.getPosition().equals(position) &&
				currentEntity.getType().equals(type)) {
				return currentEntity;
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
		for (Map.Entry<String, Entity> entry : getEntities().entrySet()) {
			Entity currentEntity = entry.getValue();
			if (
				currentEntity.getPosition().getX() == cell.getX()
				&& currentEntity.getPosition().getY() == cell.getY()
			) {
				result.add(currentEntity);
			}
		}
		return result;
	}

	public int getTickNumber() {
		return tickNumber;
	}

	public Position getSpawnpoint() {
		return spawnpoint;
	}

	public int getSpawnRate() {
		return mode.getZombieTick();
	}

	public void setSpawnpoint(Position spawnpoint) {
		this.spawnpoint = spawnpoint;
	}

	public void tickOne() {
		tickNumber++;
	}

	public void removeEntity(Entity entity) {
		for (Map.Entry<String, Entity> entry : getEntities().entrySet()) {
			Entity currentEntity = entry.getValue();
			if (currentEntity.equals(entity)) {
				entities.remove(currentEntity.getId());
				break;
			}
		}
	}

	public void addItemToInventory(CollectibleEntity entity) {
		inventory.add(entity);
	}

	// Check if type exists regardless of position
	public boolean entityExists(String type) {
		for (Entity ent : entities.values()) {
			if (ent.getType().equals(type)) {
				return true;
			}
		}
		return false;
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

	/**
	 * @return player's curr position in the dungeon
	 */
	public Position getPlayerPosition() {
		for (Map.Entry<String, Entity> entry : entities.entrySet()) {
			Entity currentEntity = entry.getValue();
			if (currentEntity instanceof Player) {
				return currentEntity.getPosition();
			}		
		}
		return null;
	}

	/**
	 * @return the player entity of a dungeon
	 */
	public Player getPlayer() {
		for (Map.Entry<String, Entity> entry : entities.entrySet()) {
			Entity currentEntity = entry.getValue();
			if (currentEntity instanceof Player) {
				return (Player)currentEntity;
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
		}

		if (wood >= 1 && arrow >= 3) {
			result.add("bow");
		}

		if (wood >= 2 && (treasure >= 1 || key >= 1)) {
			result.add("shield");
		}

		return result;
	}

	public int getHistoricalEntCount() {
		return this.historicalEntCount;
	}

	public void setHistoricalEntCount(int historicalEntCount) {
		this.historicalEntCount = historicalEntCount;
	}

	public boolean equals(Object obj) {
		if (this == null || obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		} 

		if (getClass() != obj.getClass()) {
			return false;
		}

		Dungeon other = (Dungeon) obj;

		if (id != other.getId()) {
			return false;
		}
		
		if (name == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!name.equals(other.getName())) {
			return false;
		}

		if (inventory == null) {
			if (other.getInventory() != null) {
				return false;
			}
		} else if (!inventory.equals(other.getInventory())) {
			return false;
		}

		if (entities == null) {
			if (other.getEntities() != null) {
				return false;
			}
		} else if (!entities.equals(other.getEntities())) {
			return false;
		}

		if (gameMode == null) {
			if (other.getGameMode() != null) {
				return false;
			}
		} else if (!gameMode.equals(other.getGameMode())) {
			return false;
		}

		if (goals == null) {
			if (other.getGoals() != null) {
				return false;
			}
		} else if (!goals.equals(other.getGoals())) {
			return false;
		}
		return true;
	}
}
