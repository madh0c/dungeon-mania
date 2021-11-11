package dungeonmania.allEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.util.Position;


public class BombStatic extends Entity {

	/**
	 * This bomb has been placed and will be exploded, thus cannot be moved or moved into
	 * @param position	where the bomb is located
	 */
    public BombStatic(String id, Position position) {
        super(id, position, "bomb_static");
    }

	/**
	 * Explode a bomb, removing all entities in its blast radius.
	 */
	public List<Entity> explode(Dungeon currentDungeon) {
		List<Entity> entitiesToBeRemoved = new ArrayList<>();	

		for (Position cardinal : this.getPosition().getCardinallyAdjPositions()) {
			Switch sw = (Switch)currentDungeon.getEntity("switch", cardinal);
			if (sw != null) {
				if (sw.getStatus()) {
					entitiesToBeRemoved.addAll(toBeDetonated(currentDungeon, this.getPosition()));							
				}
			}
		} return entitiesToBeRemoved;
	}

	/**
	 * returns a list of entity IDs surrounding an activated bomb which should be removed
	 * @param centre
	 * @return
	 */
	public List<Entity> toBeDetonated(Dungeon currentDungeon, Position centre) {

		List<Entity> result = new ArrayList<>();
		for (Position adjacentPos : centre.getAdjacentPositions()) {
			for (Entity cellEnt : currentDungeon.getEntitiesOnCell(adjacentPos)) {
				if (cellEnt != null && !(cellEnt instanceof Player) && !(cellEnt instanceof Portal)) {
					result.add(cellEnt);
				}
			}	
		}
		
		for (Entity cellEnt : currentDungeon.getEntitiesOnCell(centre)) {
			if (cellEnt != null && !(cellEnt instanceof Player)) {
				result.add(cellEnt);
			}
		} return result; 
	}

}
