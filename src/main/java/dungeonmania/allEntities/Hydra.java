package dungeonmania.allEntities;

import java.util.Random;

import dungeonmania.CollectableEntity;
import dungeonmania.Dungeon;
import dungeonmania.DurableEntity;
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
	public void attack(Dungeon dungeon, int playerHp) {
		// Check if anduril
		if (!haveAnduril(dungeon)) {
			// Chance of gaining health
			if (rng.nextInt(2) == 0) {
				// Gain health
				gainHealth(dungeon);
				return;
			}
		}
		super.attack(dungeon, playerHp);
	}

	/**
	 * Checks if player currently has an anduril
	 * @param dungeon	Dungeon of Player
	 * @return	true if player has an anduril
	 * 			<li>false if otherwise
	 */
	private boolean haveAnduril(Dungeon dungeon) {
		for (CollectableEntity item : dungeon.getInventory()) {
			if (item.getType().equals("anduril")) {
				DurableEntity dur = (DurableEntity) item;
				if (dur.getDurability() > 0)
					return true;
			}
		}
		return false;
	}

	/**
	 * Gain health instead of losing
	 * @param dungeon	Dungeon of Hydra
	 */
	private void gainHealth(Dungeon dungeon) {
		Player player = dungeon.getPlayer();
		setHealth(getHealth() + ((player.getHealth() * player.getAttack()) / 5));
	}
		
}
