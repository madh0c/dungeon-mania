package dungeonmania;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			for (int y = 0; y < currentDungeon.getWidth(); y++) {
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
			int bogFactor = 0;
			if (!currentDungeon.validPos(pos)) {
				break;
			}
			List<String> entTypesAdjCell = new ArrayList<>();
			List<Entity> entCell = currentDungeon.getEntitiesOnCell(pos);

			for (Entity ent : entCell) {
				if (ent instanceof SwampTile) {
					SwampTile swamp = (SwampTile) ent;
					bogFactor = swamp.getBogFactor();
				}
				entTypesAdjCell.add(ent.getType());
			}

			if (!Collections.disjoint(entTypesAdjCell, mercIllegal)) {
				break;
			}

			if (bogFactor != 0) {
				outlets.put(pos, bogFactor);
			} else {
				outlets.put(pos, 1);
			}
 		}
		return outlets;
	}



	public static Position traverse(Position source, Position destination, Map<Position, Map<Position, Integer>> dungeonMap) {
		Position nextPosition = null;

		


	return nextPosition;
}
	
}
