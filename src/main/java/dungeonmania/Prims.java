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

		boolean[][] maze = new boolean[50][50];

		maze[xStart][yStart] = true;
		maze[xEnd][yEnd] = true;

		List<Position> options = new ArrayList<>();

		List<Position> initialNeighbours = new ArrayList<>();
		
		initialNeighbours.addAll(startPos.getPositionsTwoTilesAway());

		for (Position neighPos : initialNeighbours) {
			if (isValidWall(neighPos, maze)) {
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
				if (isValidNotWall(potNeigh, maze)) {
					neighbours.add(potNeigh);
				} 
			} potentialNeighbours.clear();

			Random random = new Random();

			if (!neighbours.isEmpty()) {
				int randomInd = random.nextInt(neighbours.size());

				Position neighbour = neighbours.get(randomInd);
				Position between = Position.calculateMedianPosition(next, neighbour);

				maze[between.getX()][between.getY()] = true;
				maze[neighbour.getX()][neighbour.getY()] = true;
				
				List<Position> wallNeighbours = new ArrayList<>();
				potentialNeighbours.addAll(next.getPositionsTwoTilesAway());
				
				for (Position potNeigh : potentialNeighbours) {
					if (isValidWall(potNeigh, maze)) {
						wallNeighbours.add(potNeigh);
					} 
				} 
				options.addAll(wallNeighbours);
			}
		} 
		
		if (!maze[endPos.getX()][endPos.getY()]) {
			maze[endPos.getX()][endPos.getY()] = true;
		}

		List<Position> neighbours = endPos.getCardinallyAdjPositions();

		for (Position neighPos : neighbours) {
			if (isValidWall(neighPos, maze) || isValidNotWall(neighPos, maze)) {
				maze[neighPos.getX()][neighPos.getY()] = true;
			} break;
		}

		return createDungeon(startPos, endPos, maze, gameMode, lastUsedDungeonId);
	}

	static boolean isValidWall(Position position, boolean[][] maze) {
		int x = position.getX();
		int y = position.getY();

		if (x > 0 && x < 49 && y > 0 && y < 49 && !maze[x][y]) {
			return true;
		} return false;
	}

	static boolean isValidNotWall(Position position, boolean[][] maze) {
		int x = position.getX();
		int y = position.getY();

		if (x > 0 && x < 49 && y > 0 && y < 49 && maze[x][y]) {
			return true;
		} return false;
	}

	public static Dungeon createDungeon(Position startPos, Position endPos, boolean[][] maze, String gameMode, int id) {
		List<Entity> entityList = new ArrayList<>();
		int historicalEntCount = 0;

		// Player player = new Player(String.valueOf(historicalEntCount), startPos, gameMode);
		EntityFactory factory = null;
		if (gameMode.equals("peaceful")) {
			factory = new PeacefulFactory();
		} else if (gameMode.equals("standard")) {
			factory = new StandardFactory();
		} else if (gameMode.equals("hard")) {
			factory = new HardFactory();
		}
		Player player = factory.createPlayer(String.valueOf(historicalEntCount), startPos);
		
		entityList.add(player);
		historicalEntCount++;

		for (int x = 0; x < 50; x++) {
			for (int y = 0; y < 50; y++) {
				if (!maze[x][y]) {
					Wall wall = new Wall(String.valueOf(historicalEntCount), new Position(x, y));
					entityList.add(wall);
					historicalEntCount++;
				}
			}
		}

		Exit exit = new Exit(String.valueOf(historicalEntCount), endPos);
		entityList.add(exit);
		historicalEntCount++;

		Dungeon newDungeon = new Dungeon(id, "primsDungeon", entityList, gameMode, ":exit", new GoalLeaf("exit"), "exit");
		return newDungeon;
	}
	
}
