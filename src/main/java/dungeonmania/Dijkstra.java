package dungeonmania;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				} entHelper(dungeonMap, currPos, currentDungeon, entOnCell, mercIllegal);
			}
		} return dungeonMap;
	}

	public static void entHelper(Map<Position, Map<Position, Integer>> dungeonMap, Position currPos, Dungeon currentDungeon, List<Entity >entOnCell, List<String> mercIllegal) {
		List<Position> adjPos = currPos.getCardinallyAdjPositions();

		for (Position pos : adjPos) {
			int swamp = 0;
			if (!checkValidPos(pos, currentDungeon)) {
				break;
			}
			List<String> entTypesAdjCell = new ArrayList<>();
			List<Entity> entCell = currentDungeon.getEntitiesOnCell(pos);

			for (Entity ent : entCell) {
				if (ent instanceof SwampTile) {
					swamp = ent.getBogFactor();

				}
				entTypesAdjCell.add(ent.getType());
			}

			if (!Collections.disjoint(entTypesAdjCell, mercIllegal)) {
				break;
			}

			if (entTypesAdjCell.contains("swamp_tile")) {

			}
 		}
	}

	public static boolean checkValidPos(Position pos, Dungeon currentDungeon){
		int posX = pos.getX();
		int posY = pos.getY();

		if (posX < 0 || posX > currentDungeon.getWidth()) {
			return false;
		} else if (posY < 0 || posY > currentDungeon.getHeight()){
			return false;
		}

		return true;
	}



	public static Position traverse(Position source, Position destination, Dungeon currentDungeon) {
		Position nextPosition = null;

		Map<Position, Integer> dungeonMap = new HashMap<>();

		List<String> mercIllegal = new ArrayList<>();
		mercIllegal.add("door");
		mercIllegal.add("wall");
		mercIllegal.add("boulder");

		for (int x = 0; x < currentDungeon.getWidth(); x++) {
			for (int y = 0; y < currentDungeon.getWidth(); y++) {
				Position currPos = new Position(x, y);
				List<Entity> entOnCell = currentDungeon.getEntitiesOnCell(currPos);
				if (entOnCell.containsAll(mercIllegal)) {
					break;
				}
				
			}
		}


	return nextPosition;
}
	
}
