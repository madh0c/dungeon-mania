package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import dungeonmania.allEntities.*;


public class Battle {
	/**
	 * Battle the enemy<ul>
	 * <li>The player is received from the dungeon<
	 * <li>The player health has the formula:<ul>
	 * 		<li>playerHealth -= (enemyHealth * enemyAttack) / 10</ul>
	 * <li>The enemy health has the formula:<ul>
	 * 		<li>enemyHealth -= (enemyHealth * enemyAttack) / 10</ul>
	 * </ul>
	 * @param entity - Enemy to be fought
	 * @param dungeon - Dungeon where battle is taking place
	 */
	public static void battle(Entity entity, Dungeon dungeon) {
		MovingEntity enemy = (MovingEntity) entity;

		// Each loop occurrence is a battle occurring
		while (enemy.getHealth() > 0 && dungeon.getPlayer().getHealth() > 0) {
			Player player = dungeon.getPlayer();
			// if player invincible
			if (player.getInvincibleTickDuration() > 0) {
				dungeon.removeEntity(entity);
				break;
			}

			// PLAYER ATTACKING 
			player.setAttack(player.getInitialAttack());
			int enemyHp = enemy.getHealth();

			int enemyAtk = enemy.getBaseAttack();
			List<CollectableEntity> toBeRemoved = new ArrayList<CollectableEntity>();

			// Reduce the durability of the items in the player's inventory
			for (CollectableEntity item : dungeon.getInventory()) {
				if (item instanceof UsableEntity) {
					UsableEntity usable = (UsableEntity) item;
					enemyAtk = usable.use(dungeon, toBeRemoved, enemyAtk);
				}
			}

			// remove items w/ no durability
			dungeon.getInventory().removeAll(toBeRemoved);

			// Get player's health before any damages
			int playerHp = player.getHealth();
			// Character Health = Character Health - ((Enemy Health * Enemy Attack Damage) / 10)
			// Enemy Health = Enemy Health - ((Character Health * Character Attack Damage) / 5)

			player.setHealth(playerHp - ((enemyHp * enemyAtk) / 10));

			// ENEMIES ATTACK
			enemy.attack(dungeon, playerHp);

			// POST-BATTLE CHECKS
			// Check if player is dead
			if(playerDead(dungeon)) return;

			// Check if enemy is dead
			if(enemyDead(dungeon, entity)) continue;
		}
		
	}

	/**
	 * Check if the player dies<p>
	 * If they have:<ul>
	 * <li>Check if player has a OneRing, respawn player if they have
	 * </ul>
	 * @param dungeon	Dungeon of player
	 * @return	true if player is dead
	 * 			<li> false if otherwise
	 */
	private static boolean playerDead(Dungeon dungeon) {
		if (dungeon.getPlayer().getHealth() <= 0) {
			List<CollectableEntity> ringDelete = new ArrayList<> ();
			for (CollectableEntity item : dungeon.getInventory()) {
				if (item instanceof OneRing) {
					dungeon.getPlayer().setHealth(dungeon.getPlayer().getInitialHealth());

					ringDelete.add(item);
				}
			}
			if (ringDelete.size() == 1) {
				dungeon.getInventory().remove(ringDelete.get(0));
			}
			if (dungeon.getPlayer().getHealth() <= 0) {
				dungeon.removeEntity(dungeon.getPlayer());	
				return true;				
			}			
		} 
		return false;
	}

	/**
	 * Check if the enemy is dead
	 * Checks if:<ul>
	 * <li> Mercenary or ZombieToast, then 20% chance of dropping armour
	 * <li> Drops a OneRing, which it has a 10% chance of doing so
	 * @param dungeon	Dungeon of enemy
	 * @param entity	Entity superclass object of enemhy
	 * @return	true if the enemy is dead
	 * 			<li> false if otherwise
	 */
	private static boolean enemyDead(Dungeon dungeon, Entity entity) {
		MovingEntity enemy = (MovingEntity) entity;
		if (enemy.getHealth() <= 0) {
			// drop armour 
			if ((enemy instanceof Mercenary || enemy instanceof ZombieToast) && !(enemy instanceof Hydra)) {
				Random rand = new Random();
				if (rand.nextInt(5) == 1) {
					// Armour armour = new Armour(String.valueOf(dungeon.getHistoricalEntCount()), enemy.getPosition());
					Entity armo = dungeon.getFactory().createEntity(String.valueOf(dungeon.getHistoricalEntCount()), "armour", enemy.getPosition());
					Armour armour = (Armour) armo;
					dungeon.addItemToInventory(armour);
					dungeon.setHistoricalEntCount(dungeon.getHistoricalEntCount() + 1);
				}

			}

			// drop one ring
			OneRing ring = new OneRing(String.valueOf(dungeon.getHistoricalEntCount()), dungeon.getPlayerPosition());
			if (ring.doesSpawn()) {
				int check = 0;
				for (CollectableEntity item : dungeon.getInventory()) {
					if (item instanceof OneRing) {
						check = 1;
						break;
					}
				}
				if (check == 0) {
					dungeon.addItemToInventory(ring);
					dungeon.setHistoricalEntCount(dungeon.getHistoricalEntCount() + 1);
				}
			}

			// remove
			dungeon.removeEntity(entity);
			return true;
		}
		return false;
	}

}
