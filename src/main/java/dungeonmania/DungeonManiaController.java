package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import dungeonmania.Entity;
import dungeonmania.Dungeon;
import dungeonmania.jsonExporter;
import dungeonmania.allEntities.*;
import dungeonmania.Move;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;




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
		return Arrays.asList("Standard", "Peaceful", "Hard");
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

	public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
		checkValidNewGame(dungeonName, gameMode);

		Dungeon newDungeon = jsonExporter.makeDungeon(lastUsedDungeonId, dungeonName, gameMode);
				
		List<EntityResponse> entities = new ArrayList<EntityResponse>();
		for (Map.Entry<String, Entity> entry : newDungeon.getEntities().entrySet()) {
			Entity currentEntity = entry.getValue();
			EntityResponse er = new EntityResponse(entry.getKey(), currentEntity.getType(), currentEntity.getPosition(), currentEntity.isInteractable());
			entities.add(er);
		}

		// Check if switch is coincided with boulder
		for (Map.Entry<String, Entity> entry : newDungeon.getEntities().entrySet()) {
			Entity currentEntity = entry.getValue();
			if (currentEntity instanceof Switch) {
				Position pos = currentEntity.getPosition();
				Position newPos = new Position(pos.getX(), pos.getY(), 0);
				Boulder boulder = (Boulder) newDungeon.getEntity("boulder", newPos);
				if (boulder != null) {
					Switch sw = (Switch) currentEntity;
					sw.setStatus(true);
				}
				
			}
		}
		

		DungeonResponse result = new DungeonResponse(
			String.valueOf(newDungeon.getId()), 
			newDungeon.getName(), 
			entities, 
			new ArrayList<ItemResponse>(), 
			newDungeon.getBuildables(),             
			newDungeon.getGoals() 
		);

		lastUsedDungeonId++;

		currentDungeon = newDungeon;
		games.add(newDungeon);
		return result;
	}
		
	// helper
	public DungeonResponse getDungeonInfo(int dungeonId) {
		Dungeon target = null;
		for (Dungeon dungeon : games) {
			if (dungeon.getId() == dungeonId) {
				target = dungeon;
			}
		}

		List<EntityResponse> entities = new ArrayList<EntityResponse>();
		for (Map.Entry<String, Entity> entry : target.getEntities().entrySet()) {
			Entity currentEntity = entry.getValue();
			EntityResponse er = new EntityResponse(entry.getKey(), currentEntity.getType(), currentEntity.getPosition(), currentEntity.isInteractable());
			entities.add(er);
		}

		List<ItemResponse> inventory = new ArrayList<ItemResponse>();

		for (CollectibleEntity collectibleEntity : target.getInventory()) {
			inventory.add(new ItemResponse(collectibleEntity.getId(), collectibleEntity.getType()));
		}
		
		return new DungeonResponse(
			String.valueOf(target.getId()), 
			target.getName(), 
			entities, 
			inventory, 
			target.getBuildables(),             
			target.getGoals()
		);
	}

	public void checkValidNewGame(String dungeonName, String gameMode) throws IllegalArgumentException {
		boolean gameExists = false;
		for (String dungeon : dungeons()) {
			String dungeonWJson = dungeon + ".json";
			if (dungeonWJson.equals(dungeonName)) {
				gameExists = true;
				break;
			}
		}
		
		if (gameExists == false) {
			// return;
			throw new IllegalArgumentException("Invalid Dungeon Map Passed; Requested Dungeon Does Not Exist");
		}

		if (!this.getGameModes().contains(gameMode)) {
			throw new IllegalArgumentException("Invalid Game Mode Passed; Supported Game Modes: Standard, Peaceful, Hard");
		}
	}

	public Dungeon getDungeon(int dungeonId) {
		return games.get(dungeonId);
	}

	public DungeonResponse saveGame(String name) throws IllegalArgumentException {
		return null;
    }

	public DungeonResponse loadGame(String name) throws IllegalArgumentException {
		checkValidLoadGame(name);
		return null;
	}

	public void checkValidLoadGame(String name) throws IllegalArgumentException {
		boolean gameExists = false;
		int dungeonIdAsInt = Integer.parseInt(name);

		for (Dungeon game : games) {
			if (game.getId() == dungeonIdAsInt) {
				gameExists = true;
			}
		}
		
		if (gameExists == false) {
			throw new IllegalArgumentException("Invalid Dungeon Name Passed; Requested Dungeon Cannot Be Loaded As It Does Not Exist");
		}
	}


	public List<String> allGames() {
		ArrayList<String> gameList = new ArrayList<String>();
		for (Dungeon game : games) {
			String gameIdAsString = Integer.toString(game.getId());
		    gameList.add(gameIdAsString);
		}
		return gameList;
	}

	public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
		checkValidTick(itemUsed);
		// First tick of game, some actions to do
		if (currentDungeon.getTickNumber() == 0) {
			// If player exists
			if (currentDungeon.getPlayer() != null) {
				currentDungeon.setSpawnpoint(currentDungeon.getPlayerPosition());
			}
		}

		// Spawn in new entities after period of ticks
		if (currentDungeon.getTickNumber() % 10 == 0 && currentDungeon.getTickNumber() > 0) {
			// If there is a spawnpoint
			if (currentDungeon.getSpawnpoint() != null) {
				// Merc spawn every 10 ticks
				Mercenary merc = new Mercenary(currentDungeon.getSpawnpoint());
				currentDungeon.addEntity(merc);
			}

		}

		
		currentDungeon.tickOne();
		Move moveStrategy = new PlayerMove();
		// Move player
		if (currentDungeon.getPlayer() != null) {
			// make sure invincibility wears off
			int invicibleTicksLeft = currentDungeon.getPlayer().getInvincibleTickDuration();
			currentDungeon.getPlayer().setInvincibleTickDuration(invicibleTicksLeft - 1);
			moveStrategy.move(currentDungeon.getPlayer(), currentDungeon, movementDirection);			
		}
		// Move everything else
		for (Map.Entry<String, Entity> entry : currentDungeon.getEntities().entrySet()) {
			Entity currentEntity = entry.getValue();
			if (currentEntity instanceof Spider) {
				moveStrategy = new SpiderMove();
				moveStrategy.move(currentEntity, currentDungeon);
			} else if (currentEntity instanceof Mercenary) {
				moveStrategy = new MercenaryMove();
				moveStrategy.move(currentEntity, currentDungeon);
			} else if (currentEntity instanceof Boulder) {
				moveStrategy = new StandardMove();
				// Get position of switch, layer -1
				Position prevPos = currentEntity.getPosition();
				Position prevPosSwitch = new Position(prevPos.getX(), prevPos.getY(), -1);
				moveStrategy.move(currentEntity, currentDungeon, movementDirection);

				Position currPos = currentEntity.getPosition();
				Position currPosSwitch = new Position(prevPos.getX(), prevPos.getY(), -1);

				// Check if switch is being activated
				if (currentDungeon.entityExists("switch", currPosSwitch)) {
					Switch sw = (Switch) currentDungeon.getEntity("switch", currPosSwitch);
					sw.setStatus(true);
				}
				// Check if switch is being deactivated
				if (currentDungeon.entityExists("switch", prevPosSwitch)) {
					Switch sw = (Switch) currentDungeon.getEntity("switch", prevPosSwitch);
					sw.setStatus(false);
				}

			} else if (currentEntity instanceof ZombieToast) {
				moveStrategy = new StandardMove();
				Random random = new Random();
				int dir = random.nextInt(3);
				Direction currDir = Direction.NONE;
				switch (dir) {
					case 0:
						currDir = Direction.UP;
						break;
					case 1:
						currDir = Direction.DOWN;
						break;
					case 2:
						currDir = Direction.LEFT;
						break;
					case 3:
						currDir = Direction.RIGHT;
						break;
				}
				moveStrategy.move(currentEntity, currentDungeon, currDir);
			}
		}

		// Use item
		currentDungeon.useItem(itemUsed);
		

		
		return getDungeonInfo(currentDungeon.getId());
	}

	public void checkValidTick(String itemUsed) throws IllegalArgumentException, InvalidActionException {
		List<String> permittedItems = new ArrayList<String>();
		permittedItems.add("bomb");
		permittedItems.add("health_potion");
		permittedItems.add("invincibility_potion");
		permittedItems.add("invisibility_potion");

		if (!permittedItems.contains(itemUsed) && !(itemUsed == null)) {
			throw new IllegalArgumentException("Cannot Use Requested Item; Ensure Item Is Either a Bomb, Health Potion, " +
			"Invincibility Potion, Invisibility Potion or null");
		}

		boolean itemInInventory = false;
		List<CollectibleEntity> currentInventory = currentDungeon.getInventory();
		for (CollectibleEntity item : currentInventory) {
			if (!(itemUsed == null) && item.getType().equals(itemUsed)) {
				itemInInventory = true;
			}
		}

		if (itemUsed == null) {
			itemInInventory = true;
		}

		if (!itemInInventory) {
			throw new InvalidActionException("Cannot Use Requested Item; Item Does Not Exist In Inventory");
		}
	}

	public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
		checkValidInteract(entityId);
		Entity ent = currentDungeon.getEntity(entityId);
		// Attempt bribe if mercenary
		if (ent instanceof Mercenary) {
			Mercenary merc = (Mercenary) ent;
			if (!merc.bribeable(currentDungeon)) {
				throw new InvalidActionException("Cannot Bribe Mercenary; Not Enough Gold");
			}
		}
		return getDungeonInfo(currentDungeon.getId());
	}


	public void checkValidInteract(String entityId) throws IllegalArgumentException, InvalidActionException{
		if (currentDungeon.getEntity(entityId) == null) {
			throw new IllegalArgumentException("Cannot Interact With Requested Entity; Entity Does Not Exist In The Map");
		}

		Entity interactEntity = currentDungeon.getEntity(entityId);

		List<CollectibleEntity> currentInventory = currentDungeon.getInventory();

		boolean hasGold = false;
		boolean hasWeapon = false;

		for (CollectibleEntity item : currentInventory) {
			if (item.getType().equals("treasure")) {
				hasGold = true;
			} else if (item.getType().equals("sword") || item.getType().equals("bow")) {
				hasWeapon = true;
			}
		}

		Position playerPosition = currentDungeon.getPlayerPosition();
		Position entityPosition = currentDungeon.getEntity(entityId).getPosition();


		if (interactEntity.getType().equals("mercenary")) {
			if (!Position.inBribingRange(playerPosition, entityPosition)) {
				throw new InvalidActionException("Player Out Of Bribing Range Of Mercenary");
			} else if (!hasGold) {
				throw new InvalidActionException("Player Does Not Have Sufficient Gold To Mercenary");
			}
		}

		if (interactEntity.getType().equals("zombie_toast_spawner")) {
			if (!Position.isCardinallyAdjacent(playerPosition, entityPosition)) {
				throw new InvalidActionException("Player Out Of Range To Destroy Zombie Toast Spawner");
			} else if (!hasWeapon) {
				throw new InvalidActionException("Player Does Not Have A Weapon To Destroy Spawner");
			}
		}
	}

	public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
		checkValidBuild(buildable);
		return null;
	}

	public void checkValidBuild(String buildable) throws IllegalArgumentException, InvalidActionException{
		List<String> permittedBuild = new ArrayList<String>();
		permittedBuild.add("bow");
		permittedBuild.add("shield");

		if (!permittedBuild.contains(buildable)) {
			throw new IllegalArgumentException("Cannot Build The Desired Item; Only Bows and Shields Can Be Built");
		}

		List<String> currentBuildable = currentDungeon.getBuildables();
		if (!currentBuildable.contains(buildable)) {
			throw new InvalidActionException("Cannot Build The Desired Item; Not Enough Items To Complete The Recipe");
		}
		
	}


}