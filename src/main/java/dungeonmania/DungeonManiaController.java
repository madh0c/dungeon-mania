package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import dungeonmania.allEntities.*;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;


public class DungeonManiaController {

	/**
	 * ArrayList games: each game is stored as a map of existing entities, with their unique id as the key (stored as an int).
	 */
	private List<Dungeon> games =  new ArrayList<>();
	private int lastUsedDungeonId = 0;
	private Dungeon currentDungeon;

    public DungeonManiaController() {

	}


	public String getSkin() {
		return "default";		
	}

	public String getLocalisation() {
		return "en_US";
	
	}

	public List<String> getGameModes() {
		return Arrays.asList("standard", "peaceful", "hard");
	}

	public List<AnimationQueue> getAnimations() {
		List<AnimationQueue> newAnimation = new ArrayList<>();
		if (currentDungeon.getPlayer() != null) {
			int currPlayerHealth = currentDungeon.getPlayer().getHealth();
			double doubleHP = currPlayerHealth;
			double healthFrac = doubleHP/100.0;
			String healthString = Double.toString(healthFrac);

			if (healthFrac > 0.75) {
				newAnimation.add(new AnimationQueue("PostTick", currentDungeon.getPlayer().getId(), Arrays.asList("healthbar set " + healthString, "healthbar tint 0x00ff00"), true, -1));
			} else if (healthFrac > 0.5) {
				newAnimation.add(new AnimationQueue("PostTick", currentDungeon.getPlayer().getId(), Arrays.asList("healthbar set " + healthString, "healthbar tint 0xffff00"), true, -1));
			} else if (healthFrac > 0.2) {
				newAnimation.add(new AnimationQueue("PostTick", currentDungeon.getPlayer().getId(), Arrays.asList("healthbar set " + healthString, "healthbar tint 0xffa500"), true, -1));
			} else {
				newAnimation.add(new AnimationQueue("PostTick", currentDungeon.getPlayer().getId(), Arrays.asList("healthbar set " + healthString, "healthbar tint 0xff0000"), true, -1));
			}
		} return newAnimation;
	}
    
        


	/**
	 * /dungeons
	 * 
	 * Done for you.
	*/
	public static List<String> dungeons() {
		try {
			return FileLoader.listFileNamesInResourceDirectory("/dungeons");
		} catch (IOException e) {
			return new ArrayList<>();
		}
	}

	public static List<String> getDungeons() {

		String[] dungeons;

        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File("src/main/resources/dungeons");

        // Populates the array with names of files and directories
        dungeons = f.list();

		List<String> returnList = new ArrayList<>();

        // Put every file name into a list
        for (String dungeonFile : dungeons) {
            returnList.add(dungeonFile.replace(".json", ""));
		}

		return returnList;
	}

	/**
	 * Create a new game, and store it into a file inside /resources/savedGames
	 * @param dungeonName		fileName of the dungeon
	 * @param gameMode			gameMode of the dungeon (Peaceful, Standard or Hard)
	 * @return DungeonResponse	the dungeon which is being created
	 * @throws IllegalArgumentException
	 */
	public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
		gameMode = gameMode.toLowerCase();
		checkValidNewGame(dungeonName, gameMode);
		String fileName = (dungeonName + ".json"); 

		try {
			String path = FileLoader.loadResourceFile("/dungeons/" + fileName);
			currentDungeon = GameInOut.fromJSON("new", path, dungeonName, lastUsedDungeonId, gameMode, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Date date = new Date();
		long currTime = date.getTime();
		String rewindTime = Long.toString(currTime);
		String rewindPath = "/rewind/" + rewindTime + "/";
		currentDungeon.setRewindPath(rewindPath);

		try {
			Path path = Paths.get("persistence" + rewindPath);
			Files.createDirectories(path);
		
		} catch (IOException e) {
			System.err.println("Failed to create directory!" + e.getMessage());
		}

		int currentId = currentDungeon.getId();
		lastUsedDungeonId++;
		games.add(currentDungeon);
				
		List<EntityResponse> entitiyResponses = getDungeonInfo(currentId).getEntities();

		for (Entity entity : currentDungeon.getEntities()) {
			if (entity instanceof Switch) {
				Position pos = entity.getPosition();
				Position newPos = new Position(pos.getX(), pos.getY(), 0);
				Boulder boulder = (Boulder) currentDungeon.getEntity("boulder", newPos);
				if (boulder != null) {
					Switch sw = (Switch) entity;
					sw.setStatus(true);
				}
			}
		}
		evalGoal(currentDungeon);
		DungeonResponse result = new DungeonResponse(
			String.valueOf(currentDungeon.getId()), 
			currentDungeon.getName(), 
			entitiyResponses, 
			new ArrayList<ItemResponse>(), 
			currentDungeon.getBuildables(),             
			currentDungeon.getGoals(),
			this.getAnimations() 
		);

		return result;
	}
		
	/**
	 * Gets the dungeon from dungeonId and returns a DungeonResponse class
	 * @param dungeonId		- Identifier of the dungeon in the list in controller
	 * @return DungeonResponse
	 */
	public DungeonResponse getDungeonInfo(int dungeonId) {
		Dungeon target = null;
		for (Dungeon dungeon : games) {
			if (dungeon.getId() == dungeonId) {
				target = dungeon;
			}
		}

		List<EntityResponse> listER = new ArrayList<EntityResponse>();
		for (Entity entity : target.getEntities()) {
			EntityResponse eR = new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), entity.isInteractable());
			listER.add(eR);
		}

		List<ItemResponse> inventory = new ArrayList<ItemResponse>();

		for (CollectableEntity collectableEntity : target.getInventory()) {
			inventory.add(new ItemResponse(collectableEntity.getId(), collectableEntity.getType()));
		}

		
		return new DungeonResponse(
			String.valueOf(target.getId()), 
			target.getName(), 
			listER, 
			inventory, 
			target.getBuildables(),             
			target.getGoals(),
			this.getAnimations()
		);
	}
	
	/**
	 * Checks if dungeonName is a real file
	 * Checks if gameMode is Peaceful, Standard or Hard
	 * @param dungeonName	File name of desired file
	 * @param gameMode		Desired gamemode of dungeon
	 * @throws IllegalArgumentException	If not a real file, or not a real gamemode
	 */
	public void checkValidNewGame(String dungeonName, String gameMode) throws IllegalArgumentException {
		boolean gameExists = false;
		if (getDungeons().contains(dungeonName)) {
			gameExists = true;
		}
		
		if (gameExists == false) {
			throw new IllegalArgumentException("Invalid Dungeon Map Passed; Requested Dungeon Does Not Exist");
		}

		if (!this.getGameModes().contains(gameMode)) {
			throw new IllegalArgumentException("Invalid Game Mode Passed; Supported Game Modes: Standard, Peaceful, Hard");
		}
	}
	
	public Dungeon getDungeon(int dungeonId) {
		return games.get(dungeonId);
	}

	/**
	 * Save the game into a file in /resources/savedGames
	 * @throws IllegalArgumentException	If the given file name is not a real file
	 * @return	DungeonResponse
	 */
	public DungeonResponse saveGame(String name) throws IllegalArgumentException {
		String feed = name.replaceFirst(".json", "");

		String path = ("persistence/savedGames/" + feed + ".json"); 

		int count = 0;
		for (int i = 0; i < feed.length( ); i++) {
			if (feed.charAt(i) == '-') {
				count++;
			}
		}

		// If you are loading a gave that has previously been saved, the old timestamp must be removed.
		if (count > 1) {
			String reFeed = feed.replaceAll("-.*-", "-");
			path = ("persistence/savedGames/" + reFeed + ".json");
		}

		try {
			GameInOut.toJSON(feed, path, currentDungeon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getDungeonInfo(currentDungeon.getId());
	}

	public DungeonResponse loadGame(String name) throws IllegalArgumentException {
		checkValidLoadGame(name);
		String feed = name.replaceFirst(".json", "");
		String fileName = (feed + ".json"); 

		// String path = FileLoader.loadResourceFile(rewindPath);
		// String path = FileLoader.loadResourceFile("/savedGames/" + fileName);


		try {
			File loadFile = new File("persistence/savedGames/" + fileName);
			byte[] byteArray = Files.readAllBytes(loadFile.toPath());
			String path = new String(byteArray);

			currentDungeon = GameInOut.fromJSON("load", path, feed, lastUsedDungeonId, null, 0);
			setLastUsedDungeonId(getLastUsedDungeonId() + 1);
			games.add(currentDungeon);
			
			for (Entity ent : currentDungeon.getEntities()) {
				if (ent instanceof Switch) {
					Position entityPos = ent.getPosition();
					List<Entity> entOnCell = currentDungeon.getEntitiesOnCell(entityPos);
					for (Entity entCell : entOnCell) {
						if (entCell instanceof Boulder) {
							Switch entSwitch = (Switch) ent;
							entSwitch.setStatus(true);
						}
					}
				}
			}

			for (Entity ent : currentDungeon.getEntities()) {
				if (ent instanceof OlderPlayer) {
					OlderPlayer oP = (OlderPlayer) ent;
					Player currentPlayer = currentDungeon.getPlayer();
					oP.setTrackingList(currentPlayer.getTraceList());
				}
			}

			evalGoal(currentDungeon);
			return getDungeonInfo(currentDungeon.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Checks if the file name is a valid file name for loading
	 * @param name	file name
	 */
	public void checkValidLoadGame(String name) throws IllegalArgumentException {
		if (!allGames().contains(name)) {
			throw new IllegalArgumentException("Invalid Dungeon Name Passed; Requested Dungeon Cannot Be Loaded As It Does Not Exist");
		}
	}


	public List<String> allGames() {
		String[] games;
		// Creates a new File instance by converting the given pathname string
		// into an abstract pathname
		File f = new File("persistence/savedGames");

		// Populates the array with names of files and directories
		games = f.list();
		List<String> gamesList = new ArrayList<>();

		// Put every file name into a list
		for (String gameFile : games) {
			gamesList.add(gameFile.replace(".json", ""));
		}

		return gamesList;
	}

	/**
	 * Go to next tick in give dungeon <p>
	 * Checks and uses itemUsed if it is valid <p>
	 * Checks the desired movement for Player<p>
	 * 			- Checks if able to move there<p>
	 * 			- Checks if there is a boulder, then move the boulder<p>
	 * Checks and moves all the MovableEntities
	 * 			- Checks if they will battle with Player
	 * 			- Checks if collideable with desired direction
	 * @param itemUsed
	 * @param movementDirection
	 * @return
	 * @throws IllegalArgumentException
	 * @throws InvalidActionException
	 */
	public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
		checkValidTick(itemUsed);

		saveRewind(currentDungeon.getRewindPath(), currentDungeon.getTickNumber(), currentDungeon);
	
		// Use item
		currentDungeon.useItem(itemUsed);
		
		Player player = currentDungeon.getPlayer();
		// First tick of game, some actions to do
		if (currentDungeon.getTickNumber() == 0) {
			// If player exists
			if (player != null) {
				currentDungeon.setSpawnpoint(currentDungeon.getPlayerPosition());
			}
		}

		// Spawn in new mercenary after amount of ticks, dependent on gamemode
		if (currentDungeon.getTickNumber() % currentDungeon.getMercSpawnrate() == 0 && currentDungeon.getTickNumber() > 0) {	
			currentDungeon.spawnMerc();
		}

		currentDungeon.tickOne();

		// Player actions
		if (player != null) {

			player.setCurrentDir(movementDirection);
			// make sure invincibility wears off
			int invicibleTicksLeft = player.getInvincibleTickDuration();
			player.setInvincibleTickDuration(invicibleTicksLeft - 1);
			// sceptre tick wearing off
			List<String> controlledIds = player.getControlled();
			// If there are mercs being controlled
			if (!controlledIds.isEmpty()) {
				for (Entity ent : currentDungeon.getEntities()) {
					if (ent instanceof Mercenary) {
						Mercenary merc = (Mercenary) ent;
						merc.sceptreTick(currentDungeon);
					}
				}
			}
			// Move player
			player.move(currentDungeon, movementDirection);

			// trace the player's path, so that olderPlayer can follow
			player.addTrace(player.getCurrentDir());

			// time travel through portal
			Position currPlayerPos = player.getPosition();
			List<Entity> entOnPlayerCell = currentDungeon.getEntitiesOnCell(currPlayerPos);

			for (Entity ent: entOnPlayerCell) {
				if (ent instanceof TimeTravellingPortal) {
					Direction currDir = player.getCurrentDir();
					player.setPosition(currPlayerPos.translateBy(currDir));
					this.rewind(30);
				}
			}
		}

		// Create a list of temp MovingEntities, to avoid Concurrent modifier exception
		List<MovingEntity> tempEnts = new ArrayList<>();

		for (Entity entity : currentDungeon.getEntities()) {
			if (entity instanceof MovingEntity) {
				MovingEntity mov = (MovingEntity) entity;
				tempEnts.add(mov);
			} else if (entity instanceof Boulder) {
				Boulder boulder = (Boulder) entity;
				boulder.move(currentDungeon);
			}
		}

		// Move all Movable Entities
		for (MovingEntity mov : tempEnts) {
			if (player == null) break;			
			if (player.getInvincibleTickDuration() == 0) {
				mov.move(currentDungeon);
			} else {
				mov.moveScared(currentDungeon);
			}
		}
		
		// Explode all valid bombs
		List<Entity> toRemove = new ArrayList<>();
		for (Entity entity : currentDungeon.getEntities()) {
			if (entity instanceof BombStatic) {
				BombStatic bomb = (BombStatic)entity;
				toRemove.addAll(bomb.explode(currentDungeon));
			}
		}

		if (toRemove.size() != 0) {
			currentDungeon.getEntities().removeAll(toRemove);
		}		
		
		// Spawn in zombies if appropriate
		List<ZombieToastSpawner> spawners = new ArrayList<ZombieToastSpawner>();

		for (Entity entity : currentDungeon.getEntities()) {
			if (entity.getType().equals("zombie_toast_spawner")) {
				ZombieToastSpawner foundSpawner = (ZombieToastSpawner)entity;
				spawners.add(foundSpawner);
			}
		}
		// Spawn in new zombietoast after 20 ticks (20 ticks checked inside method)
		for (ZombieToastSpawner spawner : spawners) {
			spawner.spawnZombie(currentDungeon);
		}		
		
		evalGoal(currentDungeon);

		return getDungeonInfo(currentDungeon.getId());
	}

	/**
	 * Checks if the itemUsed is able to be used
	 * @param itemUsed
	 */
	public void checkValidTick(String itemUsed) throws IllegalArgumentException, InvalidActionException {
		
		boolean itemInInventory = false;

		CollectableEntity objectUsed = null; 
		if (itemUsed != null) {
			for (CollectableEntity inv : currentDungeon.getInventory()) {
				if (inv.getId().equals(itemUsed)) {
					objectUsed = inv;
					itemInInventory = true;
					break;
				}
			}
		}

		if (itemUsed == null) {
			itemInInventory = true;
		}

		if (!itemInInventory) {
			throw new InvalidActionException("Cannot Use Requested Item; Item Does Not Exist In Inventory");
		}

		String itemType = null;
		
		if (objectUsed != null) {
			itemType = objectUsed.getType();
		}

		List<String> permittedItems = new ArrayList<String>();
		permittedItems.add("bomb");
		permittedItems.add("health_potion");
		permittedItems.add("invincibility_potion");
		permittedItems.add("invisibility_potion");
		
		if (!permittedItems.contains(itemType) && itemType != null) {
			throw new IllegalArgumentException("Cannot Use Requested Item; Ensure Item Is Either a Bomb, Health Potion, " +
			"Invincibility Potion, Invisibility Potion or null");
		}
		
	}

	public void evalGoal(Dungeon currentDungeon) {
		boolean enemies = true;
		boolean exit = false;
		boolean treasure = true;
		boolean boulders = true;

		for (Entity ent: currentDungeon.getEntities()) {
			if (ent instanceof MovingEntity || ent instanceof ZombieToastSpawner) {
				enemies = false;
				continue;
			} else if (ent instanceof Exit) {
				Position playerPos = currentDungeon.getPlayerPosition();
				Position exitPos = ent.getPosition();
				if(playerPos == null ) {
					continue;
				}else if (playerPos.equals(exitPos)) {
					exit = true;
					continue;
				} 
			} else if (ent instanceof Treasure) {
				treasure = false;
				continue;
			} else if (ent instanceof Switch) {
				Switch swtch = (Switch) ent;
				if (!swtch.getStatus()) {
					boulders = false;
					continue;
				}
			}
		}

		List<String> currAchieved = new ArrayList<>();

		if (enemies) {
			currAchieved.add("enemies");
		}
		if (exit) {
			currAchieved.add("exit");
		}
		if (treasure) {
			currAchieved.add("treasure");
		}
		if (boulders) {
			currAchieved.add("boulders");
		}

		evalLeafs(currAchieved, currentDungeon.getFoundGoals());

		evalNodes(currentDungeon.getFoundGoals());

		currentDungeon.setGoals(currentDungeon.getFoundGoals().remainingString());
	}

	public void evalLeafs(List<String> currAchieved, GoalNode head) {
		if (head instanceof GoalAnd) {
			GoalAnd headAnd = (GoalAnd) head;
			for (GoalNode subgoal : headAnd.getList()) {
				evalLeafs(currAchieved, subgoal);
			}
		} else if (head instanceof GoalOr) {
			GoalOr headOr = (GoalOr) head;
			for (GoalNode subgoal : headOr.getList()) {
				evalLeafs(currAchieved, subgoal);
			}
		} else {
			GoalLeaf leaf = (GoalLeaf) head;
			if (currAchieved.contains(leaf.getGoal())) {
				leaf.setHasCompleted(true);
			} else {
				leaf.setHasCompleted(false);
			}
		} 
	}

	public void evalNodes(GoalNode head) {
		if (head instanceof GoalAnd) {
			GoalAnd headAnd = (GoalAnd) head;
			int success = 0;
			int and = 0;
			for (GoalNode subgoal : headAnd.getList()) {
				if (subgoal.evaluate()) {
					success++;
					and++;
				}
				if (subgoal instanceof GoalAnd || subgoal instanceof GoalOr) {
					success = 0;
					success = evalSubGoals(subgoal, success);
					if (subgoal instanceof GoalAnd && success != 2) {
						success = 0;
					} 
				}
			}
			if (and == headAnd.getList().size()) {
				headAnd.setHasCompleted(true);
			}
		} else if (head instanceof GoalOr) {
			GoalOr headOr = (GoalOr) head;
			int success = 0;
			for (GoalNode subgoal : headOr.getList()) {
				if (subgoal.evaluate()) {
					success++;
					break;
				}
				if (subgoal instanceof GoalAnd || subgoal instanceof GoalOr) {
					success = 0;
					success = evalSubGoals(subgoal, success);
					if (subgoal instanceof GoalAnd && success != 2) {
						success = 0;
					}
				}
			}
			if (success > 0) {
				headOr.setHasCompleted(true);
			}
		}
	}

	public int evalSubGoals(GoalNode head, int total) {

		if (head instanceof GoalAnd) {
			GoalAnd headAnd = (GoalAnd) head;
			int success = 0;
			int and = 0;
			for (GoalNode subgoal : headAnd.getList()) {
				if (subgoal.evaluate()) {
					success++;
					total++;
					and++;
				}
				if (subgoal instanceof GoalAnd || subgoal instanceof GoalOr) {
					total = evalSubGoals(subgoal, total);
					success = evalSubGoals(subgoal, success);
					if (subgoal instanceof GoalAnd && success != 2) {
						success = 0;
						total = 0;
					} 
				}
			}
			if (and == headAnd.getList().size()) {
				headAnd.setHasCompleted(true);
			}
		} else if (head instanceof GoalOr) {
			GoalOr headOr = (GoalOr) head;
			int success = 0;
			for (GoalNode subgoal : headOr.getList()) {
				if (subgoal.evaluate()) {
					success++;
					total++;
					break;
				}
				if (subgoal instanceof GoalAnd || subgoal instanceof GoalOr) {
					total = total + evalSubGoals(subgoal, total);
					success = success + evalSubGoals(subgoal, success);
					if (subgoal instanceof GoalAnd && success != 2) {
						success = 0;
					}
				}
			}
			if (success > 0) {
				headOr.setHasCompleted(true);
			}
		}
		return total;
	}
				
	
	/**
	 * Interacts with given entityId
	 * @param entityId	Id of entity to be interacted with
	 * @return	DungeonResponse dungeon of after interaction
	 * @throws IllegalArgumentException	If cannot interact with entity
	 * @throws InvalidActionException	If cannot bribe Mercenary
	 * 									If out of range for bribery
	 * 									If out of range for ZombieToastSpawner destruction
	 */
	public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
		checkValidInteract(entityId);
		Entity ent = currentDungeon.getEntity(entityId);
		
		/* Destroy entity if entityId matches a spawner, bribe the entity if the entityId matches a mercenary or assassin */
		if (ent instanceof ZombieToastSpawner) {
			currentDungeon.removeEntity(ent);
		} else if (ent instanceof Mercenary) {
			Mercenary merc = (Mercenary) ent;
			merc.bribe(currentDungeon);
		} return getDungeonInfo(currentDungeon.getId());
	}


	/**
	 * Checks if given entity is a valid interaction
	 * @param entityId
	 * @throws IllegalArgumentException
	 * @throws InvalidActionException
	 */
	public void checkValidInteract(String entityId) throws IllegalArgumentException, InvalidActionException{
		if (currentDungeon.getEntity(entityId) == null) {
			throw new IllegalArgumentException("Cannot Interact With Requested Entity; Entity Does Not Exist In The Map");
		}

		Entity interactEntity = currentDungeon.getEntity(entityId);

		List<CollectableEntity> currentInventory = currentDungeon.getInventory();

		boolean hasGold = false;
		boolean hasWeapon = false;
		boolean hasRing = false;
		boolean hasSceptre = false;

		for (CollectableEntity item : currentInventory) {
			if (item instanceof Treasure || item instanceof SunStone) {
				hasGold = true;
			} else if (item instanceof Sword || item instanceof Bow) {
				hasWeapon = true;
			} else if (item instanceof OneRing) {
				hasRing = true;
			} else if (item instanceof Sceptre) {
				hasSceptre = true;
			}
		}

		Position playerPosition = currentDungeon.getPlayerPosition();
		Position entityPosition = currentDungeon.getEntity(entityId).getPosition();


		if (interactEntity.getType().equals("zombie_toast_spawner")) {
			if (!Position.isCardinallyAdjacent(playerPosition, entityPosition)) {
				throw new InvalidActionException("Player Out Of Range To Destroy Zombie Toast Spawner");
			} else if (!hasWeapon) {
				throw new InvalidActionException("Player Does Not Have A Weapon To Destroy Spawner");
			}
		} else if (interactEntity.getType().equals("mercenary")) {
			if (!Position.inBribingRange(playerPosition, entityPosition)) {
				throw new InvalidActionException("Player Out Of Bribing Range Of Mercenary");
			} else if (!hasGold && !hasSceptre) {
				throw new InvalidActionException("Player Does Not Have Sufficient Gold To Bribe Mercenary");
			}
		} else if (interactEntity.getType().equals("assassin")) {
			if (!Position.inBribingRange(playerPosition, entityPosition)) {
				throw new InvalidActionException("Player Out Of Bribing Range Of Assassin");
			} else if (!hasSceptre && (!hasRing || !hasGold)) {
				throw new InvalidActionException("Player Does Not Have Sufficient Resources To Bribe Assassin");
			}
		}
	}

	/**
	 * Build a given entity
	 * @param buildable	entity to be built
	 * @return		DungeonResponse after build
	 * @throws IllegalArgumentException	If entity cannot be used in build
	 * @throws InvalidActionException	If not enough items to build
	 */
	public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
		checkValidBuild(buildable);
		List<CollectableEntity> currentInventory = currentDungeon.getInventory();
		int newId = currentDungeon.getHistoricalEntCount();				
		if (buildable.equals("bow")) {
			CollectableEntity bow = (CollectableEntity) currentDungeon.getFactory().createEntity(String.valueOf(newId), "bow", currentDungeon.getPlayerPosition()); 
			currentDungeon.setHistoricalEntCount(newId + 1);
			currentInventory.add(bow);
			Bow bowBuilt = (Bow) bow;
			bowBuilt.build(currentDungeon);			
		} else if (buildable.equals("shield")) {			
			CollectableEntity shield = (CollectableEntity) currentDungeon.getFactory().createEntity(String.valueOf(newId), "shield", currentDungeon.getPlayerPosition());
			currentDungeon.setHistoricalEntCount(newId + 1);
			currentInventory.add(shield);
			Shield shieldBuilt = (Shield) shield;
			shieldBuilt.build(currentDungeon);		
		} else if (buildable.equals("sceptre")) {
			CollectableEntity sceptre = (CollectableEntity) currentDungeon.getFactory().createEntity(String.valueOf(newId), "sceptre", currentDungeon.getPlayerPosition());
			currentDungeon.setHistoricalEntCount(newId + 1);
			currentInventory.add(sceptre);
			Sceptre sceptreBuilt = (Sceptre) sceptre;
			sceptreBuilt.build(currentDungeon);
		} else if (buildable.equals("midnight_armour")) {
			CollectableEntity midnightArmour = (CollectableEntity) currentDungeon.getFactory().createEntity(String.valueOf(newId), "midnight_armour", currentDungeon.getPlayerPosition());
			currentDungeon.setHistoricalEntCount(newId + 1);
			currentInventory.add(midnightArmour);
			MidnightArmour midnightArmourBuilt = (MidnightArmour) midnightArmour;
			midnightArmourBuilt.build(currentDungeon);
		}
		return getDungeonInfo(currentDungeon.getId());
	}

	public void checkValidBuild(String buildable) throws IllegalArgumentException, InvalidActionException{
		List<String> permittedBuild = new ArrayList<String>();
		permittedBuild.add("bow");
		permittedBuild.add("shield");
		permittedBuild.add("sceptre");
		permittedBuild.add("midnight_armour");

		if (!permittedBuild.contains(buildable)) {
			throw new IllegalArgumentException("Cannot Build The Desired Item; Only Bows, Shields, Sceptres and Midnight Armours Can Be Built");
		}

		List<String> currentBuildable = currentDungeon.getBuildables();
		if (!currentBuildable.contains(buildable)) {
			throw new InvalidActionException("Cannot Build The Desired Item; Not Enough Items To Complete The Recipe");
		}
	}

	public DungeonResponse rewind(int ticks) throws IllegalArgumentException {
		if (ticks <= 0) {
			throw new IllegalArgumentException("Invalid Ticks Passed; Ticks Strictly <= 0.");
		}

		int tickNo = (currentDungeon.getTickNumber() - ticks);

		if (tickNo <= 0) {
			return getDungeonInfo(currentDungeon.getId());
		}

		try {
			String rewindPath = currentDungeon.getRewindPath() + "tick-" + tickNo + ".json";

			File loadFile = new File("persistence" + rewindPath);
			byte[] byteArray = Files.readAllBytes(loadFile.toPath());
			String path = new String(byteArray);

			// String path = FileLoader.loadResourceFile(rewindPath);

			Dungeon rewindDungeon = GameInOut.fromJSON("rewind", path, currentDungeon.getName(), lastUsedDungeonId, null, ticks);
			
			for (Entity ent : rewindDungeon.getEntities()) {
				if (ent instanceof Switch) {
					Position entityPos = ent.getPosition();
					List<Entity> entOnCell = rewindDungeon.getEntitiesOnCell(entityPos);
					for (Entity entCell : entOnCell) {
						if (entCell instanceof Boulder) {
							Switch entSwitch = (Switch) ent;
							entSwitch.setStatus(true);
						}
					}
				}
			}
			rewindDungeon.setHistoricalEntCount(currentDungeon.getHistoricalEntCount());
			Player actualPlayer = currentDungeon.getPlayer();
			String aPId = String.valueOf(currentDungeon.getHistoricalEntCount());
			actualPlayer.setId(aPId);
			rewindDungeon.addEntity(actualPlayer);
			rewindDungeon.setInventory(currentDungeon.getInventory());

			for (Entity ent : rewindDungeon.getEntities()) {
				if (ent instanceof OlderPlayer) {
					OlderPlayer oP = (OlderPlayer) ent;
					oP.setTrackingList(actualPlayer.getTraceList());
				}
			}

			currentDungeon = rewindDungeon;
			games.add(currentDungeon);
			evalGoal(currentDungeon);

			return getDungeonInfo(currentDungeon.getId());
		} catch (IOException e) {
			e.printStackTrace();
		} return null;
	}

	/**
	 * Save a game into a file in /resources/rewind
	 * @throws IllegalArgumentException	If the given file name is not a real file
	 * @return	DungeonResponse
	 */
	public void saveRewind(String rewindPath, int tick, Dungeon currentDundeon) throws IllegalArgumentException {
		String feed = "tick-" + tick;

		String path = (rewindPath + feed + ".json"); 

		try {
			GameInOut.saveRewind("persistence" + path, currentDungeon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DungeonResponse generateDungeon(int xStart, int yStart, int xEnd, int yEnd, String gameMode) throws IllegalArgumentException {
		if (!this.getGameModes().contains(gameMode)) {
			throw new IllegalArgumentException("Invalid Game Mode Passed; Supported Game Modes: Standard, Peaceful, Hard.");
		}
		Position startPos = new Position(xStart, yStart);
		Position endPos = new Position(xEnd, yEnd);

		Dungeon primsDungeon = Prims.generateDungeon(startPos, endPos, gameMode, lastUsedDungeonId);
		currentDungeon = primsDungeon;
		games.add(currentDungeon);
		lastUsedDungeonId++;
		
		return getDungeonInfo(currentDungeon.getId());
	}

	



	public int getLastUsedDungeonId() {
		return lastUsedDungeonId;
	}

	public void setLastUsedDungeonId(int lastUsedDungeonId) {
		this.lastUsedDungeonId = lastUsedDungeonId;
	}

	public Dungeon getCurrentDungeon() {
		return currentDungeon;
	}
	
}