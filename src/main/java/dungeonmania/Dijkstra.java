package dungeonmania;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import dungeonmania.allEntities.SwampTile;
import dungeonmania.util.Position;

public interface Dijkstra {

	public static Map<Position, Map<Position, Integer>> createGraph(Dungeon currentDungeon) {
		Map<Position, Map<Position, Integer>> dungeonMap = new HashMap<>();

		List<String> mercIllegal = new ArrayList<>();
		mercIllegal.add("door");
		mercIllegal.add("wall");
		mercIllegal.add("boulder");

		for (int x = 0; x < currentDungeon.getWidth(); x++) {
			for (int y = 0; y < currentDungeon.getHeight(); y++) {
				Position currPos = new Position(x, y);
				List<Entity> entOnCell = currentDungeon.getEntitiesOnCell(currPos);

				List<String> entTypesOnCell = new ArrayList<>();

				for (Entity ent : entOnCell) {
					entTypesOnCell.add(ent.getType());
				}

				if (!Collections.disjoint(entOnCell, mercIllegal)) {
					break;
				} 
				
				Map<Position, Integer> outlets = entHelper(currPos, currentDungeon, entOnCell, mercIllegal);

				if (outlets.size() != 0) {
					dungeonMap.put(currPos, outlets);
				}
			}
		} return dungeonMap;
	}

	public static Map<Position, Integer> entHelper(Position currPos, Dungeon currentDungeon, List<Entity >entOnCell, List<String> mercIllegal) {
		List<Position> adjPos = currPos.getCardinallyAdjPositions();
		
		Map<Position, Integer> outlets = new HashMap<>();

		for (Position pos : adjPos) {
			int traverseSpeed = 2;
			if (!currentDungeon.validPos(pos)) {
				break;
			}

			List<String> entTypesAdjCell = new ArrayList<>();
			List<Entity> entCell = currentDungeon.getEntitiesOnCell(pos);

			for (Entity ent : entCell) {
				if (ent instanceof SwampTile) {
					SwampTile swampTile = (SwampTile) ent;
					traverseSpeed = swampTile.moveFactor();
				} entTypesAdjCell.add(ent.getType());
			}

			if (!Collections.disjoint(entTypesAdjCell, mercIllegal)) {
				break;
			} outlets.put(pos, traverseSpeed);
 		} return outlets;
	}



	public static Position traverse(int height, int width, Position source, Position destination, Map<Position, Map<Position, Integer>> dungeonMap) {
		Position nextPosition = null;

		Map<Position, Double> dist = new HashMap<>();
		Map<Position, Position> prev = new HashMap<>();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Position pos = new Position(x, y);
				dist.put(pos, Double.POSITIVE_INFINITY);
				prev.put(pos, null);
			}
		}

		dist.put(source, 0.0);


		Queue<Position> dijkstraQueue = new PriorityQueue<>();

		while (!dijkstraQueue.isEmpty()) {
			// Position u = dijkstraQueue
			Map<Position, Integer> compPos = dungeonMap.get(u);
			
			for (Map.Entry<Position,Integer> entry : compPos.entrySet()) {
				if(dist.get(u) +  entry.getValue() < dist.get(entry.getKey()) {
					dist.put(entry.getKey(), dist.get(u) +  entry.getValue());
					prev.put(entry.getKey(), u);
				}
			}
		}
		
		return nextPosition;
	}
	
}
