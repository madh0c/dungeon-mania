package dungeonmania.allEntities;

import dungeonmania.*;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {

	public Assassin (String id, Position position) {
		super(id, position, true);
		super.setType("assassin");
		super.setHealth(30);
		super.setBaseAttack(20);
	}

	@Override 
	public void bribe(Dungeon dungeon) {		
		// Remove the first gold OR one ring if player doesnt have sunstone
		CollectableEntity gold = null;
		CollectableEntity ring = null;

		for (CollectableEntity ent : dungeon.getInventory()) {
			if (ent instanceof Treasure) {
				gold = ent;			
			}
			if (ent instanceof OneRing) {
				ring = ent;			
			}
		}

		dungeon.getInventory().remove(ring);
		
		if (!dungeon.getPlayer().getSunstoneStatus()) {			
			dungeon.getInventory().remove(gold);
		} 
		
		super.setAlly(true);
	}

}
