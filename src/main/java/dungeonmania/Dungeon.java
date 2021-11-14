package dungeonmania;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import dungeonmania.allEntities.*;
import dungeonmania.util.*;

public class Dungeon {
	private int id;
	private String name;
	private List<CollectableEntity> inventory;
    private List<Entity> entities;
    private String gameMode;
    private String goals;
	private int historicalEntCount;
	/*  Current tick of the dungeon */
	private int tickNumber;
	private Position spawnpoint;
	private GoalNode foundGoals;
	private String goalConditions;
	private EntityFactory factory;
	/* Number of ticks before a Mercenary spawns */
	private int mercSpawnrate;
	/* Number of ticks before a Spider spawns */
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

	/**
	 * Add an entity to this dungeon, with id automatically calculated
	 * @param newEntity	Entity to be added
	 */
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

	public void setInventory(List<CollectableEntity> inventory) {
		this.inventory = inventory;
	}

	/**
	 * Return Entity of same id
	 * @param id	id of Entity
	 * @return		Entity of id
	 * 				<li> null, if it doesn't exist
	 */
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

	/**
	 * Return Entity of same position
	 * @param position	Position of Entity
	 * @return			Entity of position
	 * 					<li> null, if it doesn't exist
	 */
	public Entity getEntity(Position position) {
		for (Entity entity : getEntities()) {
			if (entity.getPosition().equals(position) && entity.getPosition().getLayer() == position.getLayer()) {
				return entity;
			}
		}
		return null;
	}

	/**
	 * Return Entity of same type and position
	 * @param type		type of Entity
	 * @param position	position of Entity
	 * @return		Entity of position and type
	 * 				<li> null, if it doesn't exist
	 */
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

	/**
	 * Remove an item from the inventory
	 * @param entity	Entity to be removed
	 */
	public void removeEntity(Entity entity) {
		getEntities().remove(entity);
	}

	/**
	 * Add an item to the inventory
	 * @param entity	CollectableEntity to be added
	 */
	public void addItemToInventory(CollectableEntity entity) {
		inventory.add(entity);
	}

	/**
	 * Check if the given type exists
	 * @param type Type of entity
	 * @return true if the entity exists in the dungeon
	 * 			<li> false if otherwise
	 */
	public boolean entityExists(String type) {
		for (Entity ent : getEntities()) {
			if (ent.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if given position contains an entity
	 * @param position	Position of entity
	 * @return	true if the position contains an entity
	 * 			<li> false if otherwise
	 */
	public boolean entityExists(Position position) {
		for (Entity ent : getEntities()) {
			if (ent.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if given type exists in given position
	 * @param type		Type of entity
	 * @param position	Position of entity
	 * @return	true if the type of entity exists on the position
	 * 			<li> false if otherwise
	 */
	public boolean entityExists(String type, Position position) {		
		for (Entity ent : getEntities()) {
			if (ent.getPosition().equals(position) && ent.getType().equals(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Counts the number of given type entities in the dungeon
	 * @param type	Type of entity
	 * @return	the number of entities of same type in the dungeon
	 */
	public int numOfEntities(String type) {
		int counter = 0;
		for (Entity ent : getEntities()) {
			if (ent.getType().equals(type)) {
				counter++;
			}
		}
		return counter;
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
	 * Spawns in Mercenary, ZombieToast and Spider
	 * Mercenary spawnrate:<ul>
	 * <li> Peaceful: 20 ticks
	 * <li> Standard: 20 ticks
	 * <li> Hard: 10 ticks
	 * </ul>
	 * ZombieToast spawnrate:<ul>
	 * <li> Peaceful: 20 ticks
	 * <li> Standard: 20 ticks
	 * <li> Hard: 15 ticks
	 * </ul>
	 * Spider spawnrate:<ul>
	 * <li> Peaceful: 20 ticks
	 * <li> Standard: 20 ticks
	 * <li> Hard: 15 ticks
	 * </ul>
	 */
	public void spawnEntities() {
		// Spawn in Mercenary if appropriate
		mercSpawn();
		// Spawn in ZombieToast if appropriate
		zombieSpawn();
		// Spawn in Spider if appropriate
		spiderSpawn();
		// Spawn in Hydra if appropriate
		hydraSpawn();
	}

	/**
	 * Spawn in a mercenary at the spawnpoint at certain intervals depending on gamemode
	 * <ul><li>Peaceful: Every 20 ticks
	 * <li>Standard: Every 20 ticks
	 * <li>Hard: Every 10 ticks</ul>
	 */
	private void mercSpawn() {
		// Check if correct tick to spawn merc
		if (getTickNumber() % getMercSpawnrate() != 0 || getTickNumber() == 0) return;
		// If there is no spawnpoint
		if (getSpawnpoint() == null) return;

		// Merc spawn every 10/20 ticks
		int newId = getHistoricalEntCount();
		Mercenary newMerc = factory.createMercenary(String.valueOf(newId), spawnpoint);

		for (Entity entity : getEntitiesOnCell(getSpawnpoint())) {
			if (!newMerc.collide(entity, this)) return;
		}
		
		Random rand = new Random();
		int random = rand.nextInt(10);
		
		if (random < 2) {
			Assassin newAssassin = factory.createAssassin(String.valueOf(newId), spawnpoint);
			addEntity(newAssassin);
		} else {
			addEntity(newMerc);
		}			
		return;				
	}

	/**
	 * Spawn in a zombie_toast<ul>
	 * <li> Peaceful: 20 ticks
	 * <li> Standard: 20 ticks
	 * <li> Hard: 15 ticks
	 * </ul>
	 */
	private void zombieSpawn() {
		// Spawn in zombies if appropriate
		List<ZombieToastSpawner> spawners = new ArrayList<ZombieToastSpawner>();

		for (Entity entity : getEntities()) {
			if (entity.getType().equals("zombie_toast_spawner")) {
				ZombieToastSpawner spawner = (ZombieToastSpawner)entity;
				spawners.add(spawner);
			}
		}
		// Spawn in new zombietoast after 20 ticks (20 ticks checked inside method)
		for (ZombieToastSpawner spawner : spawners) {
			spawner.spawnZombie(this);
		}
	}

	/**
	 * Spawn in a spider<ul>
	 * <li> Peaceful: 20 ticks
	 * <li> Standard: 20 ticks
	 * <li> Hard: 15 ticks
	 * </ul>
	 */
	private void spiderSpawn() {
		// Ignore if not the right number of ticks
		if (getTickNumber() % spiderSpawnrate != 0 || getTickNumber() == 0) return;
		// If more than four spiders, don't spawn
		if (maxSpiders()) return;

		int newId = getHistoricalEntCount();
		// Random spawnpoint
		Position rngSpawn;
		boolean flag = false;
		// Checker for infinite loop
		int infLoop = 0;

		// Keep generating random spawnpoints, until there is one where the spider
		// is collideable with all entities on the spawnpoint, then spawn spider
		do {
			// Infinite loop protection
			if (infLoop > 50) {
				// Spawn on player if can't find anywhere else
				addEntity(factory.createSpider(String.valueOf(newId), getSpawnpoint()));
				return;
			}

			rngSpawn = randomSpawnpoint();
			for (Entity ent : getEntitiesOnCell(rngSpawn)) {
				if (!Spider.spawnCollide(ent)) {
					flag = true;
					break;
				}
			}
			infLoop++;

		} while (flag);

		addEntity(factory.createSpider(String.valueOf(newId), rngSpawn));

	}
	
	/**
	 * Checks if there are more than 4 spiders currently in the dungeon
	 * @return	true if there are more than 4 spiders
	 * 			<li> false if otherwise
	 */
	private boolean maxSpiders() {
		return (numOfEntities("spider") >= 4);
	}

	/**
	 * Returns a random spawnpoint on the dungeon
	 * @return	Position of random spawnpoint
	 */
	private Position randomSpawnpoint() {
		Random rng = new Random();

		int minX  = getMinX() > 0 ? getMinX() : 0;
		int maxX = getMaxX() > minX ? getMaxX() : minX + 1;
		int randX = rng.nextInt(maxX - minX) + minX;

		int minY  = getMinY() > 0 ? getMinY() : 0;
		int maxY = getMaxY() > minY ? getMaxY() : minY + 1;
		int randY = rng.nextInt(maxY - minY) + minY;

		Position ret = new Position(randX, randY);

		return ret;
	}

	/**
	 * Spawn in a Hydra every 50 ticks, at the spawnpoint of the dungeon
	 */
	private void hydraSpawn() {
		// Check if correct tick
		if (getTickNumber() % 50 != 0 || getTickNumber() == 0) return;
		// If no spawnpoint
		if (getSpawnpoint() == null) return;

		int newId = getHistoricalEntCount();
		Hydra newHydra = factory.createHydra(String.valueOf(newId), spawnpoint);

		// Check if can collide with everything on spawnpoint
		for (Entity entity : getEntitiesOnCell(spawnpoint)) {
			if (!newHydra.collide(entity, this)) return;
		}

		addEntity(newHydra);
	}

	public void moveEnemiesAndBoulder() {
		// Create a list of temp MovingEntities, to avoid Concurrent modifier exception
		List<MovingEntity> tempEnts = new ArrayList<>();

		for (Entity entity : this.getEntities()) {
			if (entity instanceof MovingEntity) {
				MovingEntity mov = (MovingEntity) entity;
				tempEnts.add(mov);
			} else if (entity instanceof Boulder) {
				Boulder boulder = (Boulder) entity;
				boulder.move(this);
			}
		}

		// Move all Movable Entities
		for (MovingEntity mov : tempEnts) {
			Player player = getPlayer();
			if (player == null) break;			
			if (player.getInvincibleTickDuration() == 0) {
				mov.move(this);
			} else {
				mov.moveScared(this);
			}
		}
	}

	public void detonateBombs() {
		// Explode all valid bombs
		List<Entity> toRemove = new ArrayList<>();
		for (Entity entity : this.getEntities()) {
			if (entity instanceof Bomb) {
				Bomb bomb = (Bomb)entity;
				if (bomb.isActive()) {
					toRemove.addAll(bomb.explode(this));
				}
			}
		}

		if (toRemove.size() != 0) {
			this.getEntities().removeAll(toRemove);
		}	
	}

	public void useItem(String itemUsed) {
		if (getPlayer() == null) {
			return;
		}

		// Use item
		CollectableEntity item = null;
		for (CollectableEntity colllectable : this.getInventory()) {
			if (colllectable.getId().equals(itemUsed)) {
				item = colllectable;
				UtilityEntity util = (UtilityEntity) item;
				util.use(getPlayer());
				
				if (item instanceof Bomb) {
					this.getEntities().add(item);
				}
			}
		}

		this.getInventory().remove(item);
	}

}
