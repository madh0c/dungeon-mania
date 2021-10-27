package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.Entity;
import dungeonmania.Dungeon;
import dungeonmania.jsonExporter;




import java.io.IOException;
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

        Dungeon newDungeon = new Dungeon(makeDungeonMap("/dungeons/" + dungeonName ".json"), gameMode, );

        return null;
    }
    
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        return null;
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        return null;
    }

    public List<String> allGames() {
        ArrayList<String> gameList = new ArrayList<String>();
        for (game : games) {
            gameList.add(game.getId);
        }
        return new ArrayList<>();
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