package dungeonmania.allEntities;

import java.util.Random;

import dungeonmania.CollectableEntity;
import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Hydra extends ZombieToast {

	private Random rng;

	public Hydra (String id, Position position, boolean enemyAttack) {
		super(id, position, enemyAttack);
		super.setType("hydra");
		super.setHealth(30);
		super.setBaseAttack(5);
		rng  = new Random();
	}

	@Override
	public void attack(Dungeon dungeon) {
		Player player = dungeon.getPlayer();
		boolean chance = true;
		// Check if anduril
		for (CollectableEntity item : dungeon.getInventory()) {
			if (item.getType().equals("Anduril")) {
				chance = false;
				break;
			}
		}

		if (chance) {
			if (rng.nextInt(2) == 0) {
				// Gain health
				setHealth(getHealth() + ((player.getHealth() * player.getAttack()) / 5));
				return;
			}
		}
		super.attack(dungeon);
	}
		
}
