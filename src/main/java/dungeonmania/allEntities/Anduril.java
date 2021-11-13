package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.*;
import dungeonmania.util.Position;

public class Anduril extends UsableEntity {

	private final int dmgMultiplier = 3;

	public Anduril (String id, Position position) {
		super(id, position, "anduril", 15);
	}

	public int getDmgMultiplier() {
		return dmgMultiplier;
	}

	@Override
	public void use(Dungeon dungeon, List<CollectableEntity> toBeRemoved) {
				

		// Anduril anduril = (Anduril) item;
		// if (anduril.getDurability() == 0) {
		// 	dungeon.getPlayer().setAttack(dungeon.getPlayer().getInitialAttack());		
		// 	toBeRemoved.add(item);
		// 	continue;
		// }
		// anduril.setDurability(anduril.getDurability() - 1);	
	}

}
