package dungeonmania;

import dungeonmania.util.Position;
import dungeonmania.allEntities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Prims {

	public static Dungeon generateDungeon(Position startPos, Position endPos, String gameMode, int lastUsedDungeonId) {

		int xStart = startPos.getX();
		int yStart = startPos.getY();
		int xEnd = endPos.getX();
		int yEnd = endPos.getY();

		Dungeon newDungeon = null;
		boolean[][] maze = new boolean[50][50];

		maze[xStart][yStart] = true;
		maze[xEnd][yEnd] = true;

		List<Position> options = new ArrayList<>();

		List<Position> initialNeighbours = new ArrayList<>();
		
		initialNeighbours.addAll(startPos.getPositionsTwoTilesAway());
		initialNeighbours.addAll(endPos.getPositionsTwoTilesAway());

		for (Position neighPos : initialNeighbours) {
			if (isValid(neighPos, maze)) {
				options.add(neighPos);
			}
		}

		while (!options.isEmpty()) {
			Random rand = new Random();
			int randomIndex = rand.nextInt(options.size());
			Position next = options.get(randomIndex);
			options.remove(randomIndex);

			maze[next.getX()][next.getY()] = true;

			List<Position> neighbours = new ArrayList<>();
			List<Position> potentialNeighbours = new ArrayList<>();
			potentialNeighbours.addAll(next.getPositionsTwoTilesAway());
			
			for (Position potNeigh : potentialNeighbours) {
				if (validPotNeigh(potNeigh, maze)) {
					neighbours.add(potNeigh);
				} potentialNeighbours.remove(potNeigh);
			}

			Random random = new Random();
			int randomInd = random.nextInt(neighbours.size());
			Position neighbour = options.get(randomInd);

			Position between = Position.calculatePositionBetween(next, neighbour);

			maze[between.getX()][between.getY()] = true;
			maze[neighbour.getX()][neighbour.getY()] = true;

			potentialNeighbours.addAll(neighbour.getPositionsTwoTilesAway());
			
			for (Position potNeigh : potentialNeighbours) {
				if (validPotNeigh(potNeigh, maze)) {
					neighbours.add(potNeigh);
				} potentialNeighbours.remove(potNeigh);
			}

			newDungeon = createDungeon(startPos, endPos, maze, gameMode, lastUsedDungeonId);
		}

		return newDungeon;
	}

	static boolean isValid(Position position, boolean[][] maze) {
		int x = position.getX();
		int y = position.getY();

		if (x >= 0 && x < 50 && y >= 0 && y < 50 && !maze[x][y]) {
			return true;
		} return false;
	}

	static boolean  validPotNeigh(Position position, boolean[][] maze) {
		int x = position.getX();
		int y = position.getY();

		if (x != 0 && x != 49 && y != 0 && y != 49 && !maze[x][y]) {
			return true;
		} return false;
	}

	public static Dungeon createDungeon(Position startPos, Position endPos, boolean[][] maze, String gameMode, int id) {
		List<Entity> entityList = new ArrayList<>();
		int historicalEntCount = 0;

		Player player = new Player(String.valueOf(historicalEntCount), startPos, gameMode);
		entityList.add(player);
		historicalEntCount++;

		for (int x = 0; x < 50; x++) {
			for (int y = 0; y < 50; y++) {
				if (maze[x][y] == false) {
					Wall newWall = new Wall(String.valueOf(historicalEntCount), new Position(x, y));
					entityList.add(newWall);
					historicalEntCount++;
				}
			}
		}

		Exit exit = new Exit(String.valueOf(historicalEntCount), endPos);
		entityList.add(exit);
		historicalEntCount++;

		return new Dungeon(id, "primsDungeon", entityList, gameMode, ":exit");
	}
	
}
