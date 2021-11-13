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
	public int use(Dungeon dungeon, List<CollectableEntity> toBeRemoved, int enemyAtk) {
		if (getDurability() == 0) {
			dungeon.getPlayer().setAttack(dungeon.getPlayer().getInitialAttack());
			toBeRemoved.add(this);
			return enemyAtk;
		}
		useDurability();
		return enemyAtk;
	}

}
