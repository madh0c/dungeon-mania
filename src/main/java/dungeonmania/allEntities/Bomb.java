package dungeonmania.allEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.UtilityEntity;
import dungeonmania.util.Position;


public class Bomb extends UtilityEntity {

	boolean isActive;

	/**
	 * This bomb is a Collectible, as can be picked up
	 * @param position	where the bomb is located
	 */
    public Bomb(String id, Position position) {
        super(id, position, "bomb");
		this.isActive = false;
	}

	public void use(Player player) {
		this.setPosition(player.getPosition());
		this.isActive = true;
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

	public boolean isActive() {
		return this.isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
