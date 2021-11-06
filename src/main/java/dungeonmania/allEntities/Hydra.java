package dungeonmania.allEntities;

import dungeonmania.*;
import dungeonmania.util.Position;

public class Hydra extends MovingEntity {

	public Hydra (String id, Position position) {
			super(id, position, "hydra");
			super.setHealth(30);
			super.setBaseAttack(5);
		}
		
}
