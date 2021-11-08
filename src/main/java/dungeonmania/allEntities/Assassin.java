package dungeonmania.allEntities;

import dungeonmania.*;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {

	public Assassin (String id, Position position) {
		super(id, position, true);
		super.setType("assasin");
		super.setHealth(30);
		super.setBaseAttack(20);
	}

}
