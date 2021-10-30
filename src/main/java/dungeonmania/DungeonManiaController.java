package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
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
		Dungeon newDungeon = jsonExporter.makeDungeon(lastUsedDungeonId, dungeonName, gameMode);
				
		List<EntityResponse> entities = new ArrayList<EntityResponse>();
		for (Map.Entry<String, Entity> entry : newDungeon.getEntities().entrySet()) {
			Entity currentEntity = entry.getValue();
			EntityResponse er = new EntityResponse(entry.getKey(), currentEntity.getType(), currentEntity.getPosition(), currentEntity.isInteractable());
			entities.add(er);
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

		/*
		System.out.println(
			String.valueOf(target.getId())
			+ target.getName()
			+ entities
			+ inventory
			+ target.getBuildables()
		); */

		return new DungeonResponse(
			String.valueOf(target.getId()), 
			target.getName(), 
			entities, 
			inventory, 
			target.getBuildables(),             
			target.getGoals()
		);
	}

	public Dungeon getDungeon(int dungeonId) {
		return games.get(dungeonId);
	}

	public DungeonResponse saveGame(String name) throws IllegalArgumentException {
		return null;
    }

	public DungeonResponse loadGame(String name) throws IllegalArgumentException {
		return null;
	}

	public List<String> allGames() {
		// ArrayList<String> gameList = new ArrayList<String>();
		// for (game : games) {
		//     gameList.add(game.getId);
		// }
		// return new ArrayList<>();
		return null;
	}

	public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
		Move moveStrategy = new StandardMove();

		// Move player
		moveStrategy.move(currentDungeon.getPlayer(), currentDungeon, movementDirection);

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
				moveStrategy.move(currentEntity, currentDungeon, movementDirection);
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
		
		return getDungeonInfo(currentDungeon.getId());
	}

	public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
		return null;
	}

	public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
		return null;
	}
}