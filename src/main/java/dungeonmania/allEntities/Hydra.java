package dungeonmania.allEntities;

import dungeonmania.*;
import dungeonmania.util.Position;

public class Hydra extends ZombieToast {

	public Hydra (String id, Position position, boolean enemyAttack) {
		super(id, position, enemyAttack);
		super.setHealth(30);
		super.setBaseAttack(5);
	}
		
}
