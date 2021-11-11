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

/**
 * The Dijksta Interface handles all things Dijkstra-related for Mercenary and Assassin movement.
 */
public interface Dijkstra {

	public static Position move(Position source, Dungeon currentDungeon) {
		Map<Position, Map<Position, Integer>> dungeonMap = createGraph(currentDungeon);

		return traverse(currentDungeon, source, currentDungeon.getPlayerPosition(), dungeonMap);
	}

	/**
	 * createGraph creates a weighted graph of the current dungeon. If the Mercenary/Assassin is unable to traverse through
	 * a cell during the the tick, an edge will not exist between the two cell. Edges only exist between adjacent 
	 * cell on the physical map. The weight of each edge represents twice the number of ticks required for the 
	 * Mercenary/Assassin to traverse from one cell to the other.
	 * @param currentDungeon
	 * @return
	 */
	public static Map<Position, Map<Position, Integer>> createGraph(Dungeon currentDungeon) {
		Map<Position, Map<Position, Integer>> dungeonMap = new HashMap<>();
		
		/*A list of entity types Mercenary/Assasins cannot coincide with. If at least one of these entities exist on a cell,
		there will be no edges between this cell and its adjacent cells in the weighted graph */
		List<String> mercIllegal = new ArrayList<>();
		mercIllegal.add("door");
		mercIllegal.add("wall");
		mercIllegal.add("boulder");
		mercIllegal.add("zombie_toast_spawner");
		mercIllegal.add("bomb_static");

		/* iterating through the current dungeon to check if each cell should be able traversed by the Merc/Assasin */
		for (int x = currentDungeon.getMinX(); x < currentDungeon.getMaxX(); x++) {
			for (int y = currentDungeon.getMinY(); x < currentDungeon.getMaxY(); y++) {
				Position currPos = new Position(x, y);

				List<Entity> entOnCell = currentDungeon.getEntitiesOnCell(currPos);
				List<String> entTypesOnCell = new ArrayList<>();

				for (Entity ent : entOnCell) {
					entTypesOnCell.add(ent.getType());
				}

				/* If the cell contains entities the Merc/Assassin cannot coincide with, the current cell will be disregarded */
				if (!Collections.disjoint(entOnCell, mercIllegal)) {
					continue;
				} 
				
				/* A helper function returns a map with the key of the entries being positions of the adjacent cells 
				traversable by the Merc/Assasin during the current tick, and the values being the appropriate weight of the c
				orresponding edge as the value of each entry */
				Map<Position, Integer> outPaths = entHelper(currPos, currentDungeon, entOnCell, mercIllegal);

				/* If there exist at least one traversable adjacent cell for the current position, the information will be 
				put inside the dungeonMap */
				if (outPaths.size() != 0) {
					dungeonMap.put(currPos, outPaths);
				}
			}
		} return dungeonMap;
	}


	/**
	 * A helper function returns a map with the key of the entries being positions of the adjacent cells 
	 * traversable by the Merc/Assasin during the current tick, and the values being the appropriate weight of the
	 * corresponding edge as the value of each entry.
	 * @param currPos
	 * @param currentDungeon
	 * @param entOnCell
	 * @param mercIllegal
	 * @return
	 */
	public static Map<Position, Integer> entHelper(Position currPos, Dungeon currentDungeon, List<Entity >entOnCell, List<String> mercIllegal) {
		List<Position> adjPos = currPos.getCardinallyAdjPositions();
		
		Map<Position, Integer> outPaths = new HashMap<>();

		/* Iterating through the list of positions that adjacent to the current cell */
		for (Position pos : adjPos) {
			int traverseSpeed = 2;

			/* Obtaining a list of entities on the cell, and their types */
			List<Entity> entCell = currentDungeon.getEntitiesOnCell(pos);
			List<String> entTypesAdjCell = new ArrayList<>();

			for (Entity ent : entCell) {
				if (ent instanceof SwampTile) {
					SwampTile swampTile = (SwampTile) ent;
					traverseSpeed = swampTile.getMoveFactor();
				} entTypesAdjCell.add(ent.getType());
			}

			if (!Collections.disjoint(entTypesAdjCell, mercIllegal)) {
				continue;
			} outPaths.put(pos, traverseSpeed);
 		} return outPaths;
	}


	/**
	 * A Method that traverses through the above created weighted graph and executes Dijkstra's algortithm, returning the next
	 * position the Merc/Assasin should go to next tot maximise efficiency chasing the player. Pseudocode sourced from the 
	 * project specification: https://gitlab.cse.unsw.edu.au/COMP2511/21T3/project-specification/-/blob/master/M3.md.
	 * @param height
	 * @param width
	 * @param source
	 * @param destination
	 * @param dungeonMap
	 * @return
	 */
	public static Position traverse(Dungeon currentDungeon, Position source, Position destination, Map<Position, Map<Position, Integer>> dungeonMap) {
		Map<Position, Double> dist = new HashMap<>();
		Map<Position, Position> prev = new HashMap<>();

		for (int x = currentDungeon.getMinX(); x < currentDungeon.getMaxX(); x++) {
			for (int y = currentDungeon.getMinY(); x < currentDungeon.getMaxY(); y++) {
				Position pos = new Position(x, y);
				dist.put(pos, Double.POSITIVE_INFINITY);
				prev.put(pos, null);
			}
		} dist.put(source, 0.0);

		Map<Position, Double> dijkstraQueue = new HashMap<>();

		for (Position entry : dungeonMap.keySet()) {
			dijkstraQueue.put(entry, dist.get(entry));
		}

		while (!dijkstraQueue.isEmpty()) {
			Position u = null;
			double min = Double.POSITIVE_INFINITY;

			for (Position entry : dijkstraQueue.keySet()) {
				if (dist.get(entry) < min) {
					min = dist.get(entry);
					u = entry;
				}
			} 

			if (u == null) {
				break;
			}
			
			dijkstraQueue.remove(u);
			
			Map<Position, Integer> compPos = dungeonMap.get(u);
			
			for (Map.Entry<Position, Integer> entry : compPos.entrySet()) {
				Double v = Double.valueOf(entry.getValue());

				if (dist.get(entry.getKey()) != null) {
					if (dist.get(u) + v < dist.get(entry.getKey())) {	
						dist.put(entry.getKey(), dist.get(u) +  v);
						prev.put(entry.getKey(), u);
					}
				}
			}
		}
		
		if (dist.get(destination) != 0.0) {
			return nextPos(destination, source, prev, destination); 		
		} return null;
	}
	
	/**
	 * A recursive helper function that ensures the most optimal next position for the Merc/Assassin is returned.
	 * @param currPos
	 * @param source
	 * @param prev
	 * @return
	*/
	public static Position nextPos(Position currPos, Position source, Map<Position, Position> prev, Position returnPos) {
		while (!prev.get(currPos).equals(source)) {
			currPos = prev.get(currPos);
		} if (prev.get(currPos).equals(source)) {
			returnPos = currPos;
			return returnPos;
		} else {
			return null;
		}
	}
}
