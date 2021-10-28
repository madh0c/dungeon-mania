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

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
		Map<String, Entity> currentMap = jsonExporter.makeDungeonMap(dungeonName);
		Dungeon newDungeon = new Dungeon(lastUsedDungeonId, dungeonName, currentMap, gameMode, "");
				
		List<EntityResponse> entities = new ArrayList<EntityResponse>();
		for (Map.Entry<String, Entity> entry : currentMap.entrySet()) {
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
			"" //jsonExporter.getGoals()
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
		for (Map.Entry<String, Entity> entry : target.getAllEntities().entrySet()) {
			Entity currentEntity = entry.getValue();
			EntityResponse er = new EntityResponse(entry.getKey(), currentEntity.getType(), currentEntity.getPosition(), currentEntity.isInteractable());
			entities.add(er);
		}

		List<ItemResponse> inventory = new ArrayList<ItemResponse>();

		return new DungeonResponse(
			String.valueOf(target.getId()), 
			target.getName(), 
			entities, 
			inventory, 
			target.getBuildables(),             
			"" //jsonExporter.getGoals()
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
		return null;
	}

	public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
		return null;
	}

	public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
		return null;
	}
}