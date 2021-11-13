package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import dungeonmania.allEntities.*;


public class Battle {
	/**
	 * Battle the enemy<p>
	 * The player is received from the dungeon<p>
	 * The player health has the formula:
	 * 		playerHealth -= (enemyHealth * enemyAttack) / 10
	 * The enemy health has the formula:
	 * 		enemyHealth -= (enemyHealth * enemyAttack) / 10
	 * @param entity - Enemy to be fought
	 * @param dungeon - Dungeon where battle is taking place
	 */
	public static void battle(Entity entity, Dungeon dungeon) {
		MovingEntity enemy = (MovingEntity) entity;
		while (enemy.getHealth() > 0 && dungeon.getPlayer().getHealth() > 0) {
			// if player invincible
			if (dungeon.getPlayer().getInvincibleTickDuration() > 0) {
				dungeon.removeEntity(entity);
				break;
			}

			dungeon.getPlayer().setAttack(dungeon.getPlayer().getInitialAttack());
			int playerHp = dungeon.getPlayer().getHealth();
			int enemyHp = enemy.getHealth();

			int playerAtk = dungeon.getPlayer().getAttack();
			int enemyAtk = enemy.getBaseAttack();
			List<CollectableEntity> toBeRemoved = new ArrayList<CollectableEntity>();

			// Chance of hydra spawning head
			boolean chanceTwoHeads = true;
		
			for (CollectableEntity item : dungeon.getInventory()) {
				if (item instanceof Sword) {						
					Sword sword = (Sword) item;
					if (sword.getDurability() == 0) {
						toBeRemoved.add(item);
						continue;
					}
					playerAtk += sword.getExtraDamage();
					sword.setDurability(sword.getDurability() - 1);						
				}

				if (item instanceof Anduril) {						
					Anduril anduril = (Anduril) item;
					if (anduril.getDurability() == 0) {
						dungeon.getPlayer().setAttack(dungeon.getPlayer().getInitialAttack());		
						toBeRemoved.add(item);
						continue;
					}
					anduril.setDurability(anduril.getDurability() - 1);						
				}

				if (item instanceof Bow) {
					Bow bow = (Bow) item;
					if (bow.getDurability() == 0) {
						toBeRemoved.add(item);
						continue;
					}
					playerAtk += bow.getExtraDamage()*2;
					bow.setDurability(bow.getDurability() - 1);	
				}

				if (item instanceof Armour) {						
					Armour armour = (Armour) item;
					if (armour.getDurability() == 0) {
						toBeRemoved.add(item);
						continue;
					}
					enemyAtk /= 2;
					armour.setDurability(armour.getDurability() - 1);
				}

				if (item instanceof Shield) {
					Shield shield = (Shield) item;
					if (shield.getDurability() == 0) {
						toBeRemoved.add(item);
						continue;
					}
					enemyAtk /= 5;
					shield.setDurability(shield.getDurability() - 1);
				}

				if (item instanceof MidnightArmour) {
					MidnightArmour midnightArmour = (MidnightArmour) item;
					if (midnightArmour.getDurability() == 0) {
						toBeRemoved.add(item);
						continue;
					}
					playerAtk += midnightArmour.getExtraDamage();
					enemyAtk /= 3;
					midnightArmour.setDurability(midnightArmour.getDurability() - 1);
				}
			}

			// remove items w/ no durability
			dungeon.getInventory().removeAll(toBeRemoved);

			// Character Health = Character Health - ((Enemy Health * Enemy Attack Damage) / 10)
			// Enemy Health = Enemy Health - ((Character Health * Character Attack Damage) / 5)
			dungeon.getPlayer().setHealth(playerHp - ((enemyHp * enemyAtk) / 10));
			
			// Change player attack
			dungeon.getPlayer().setAttack(playerAtk);


			// Enemy attacks the players
			enemy.attack(dungeon, playerHp);

			// If player dies
			if(Battle.playerDead(dungeon)) return;

			// if (dungeon.getPlayer().getHealth() <= 0) {
			// 	List<CollectableEntity> ringDelete = new ArrayList<> ();
			// 	for (CollectableEntity item : dungeon.getInventory()) {
			// 		if (item instanceof OneRing) {
			// 			dungeon.getPlayer().setHealth(dungeon.getPlayer().getInitialHealth());

			// 			ringDelete.add(item);
			// 		}
			// 	}
			// 	if (ringDelete.size() == 1) {
			// 		dungeon.getInventory().remove(ringDelete.get(0));
			// 	}
			// 	if (dungeon.getPlayer().getHealth() <= 0) {
			// 		dungeon.removeEntity(dungeon.getPlayer());	
			// 		return;				
			// 	}			
			// } 


			// If enemy dies
			if(enemyDead(dungeon, entity)) continue;

			// if (enemy.getHealth() <= 0) {
			// 	// drop armour 
			// 	if (enemy instanceof Mercenary || enemy instanceof ZombieToast) {
			// 		Random rand = new Random();
			// 		if (rand.nextInt(20) == 1) {
			// 			// Armour armour = new Armour(String.valueOf(dungeon.getHistoricalEntCount()), enemy.getPosition());
			// 			Entity armo = dungeon.getFactory().createEntity(String.valueOf(dungeon.getHistoricalEntCount()), "armour", enemy.getPosition());
			// 			Armour armour = (Armour) armo;
			// 			dungeon.addItemToInventory(armour);
			// 			dungeon.setHistoricalEntCount(dungeon.getHistoricalEntCount() + 1);
			// 		}

			// 	}

			// 	// drop one ring
			// 	OneRing ring = new OneRing(String.valueOf(dungeon.getHistoricalEntCount()), dungeon.getPlayerPosition());
			// 	if (ring.doesSpawn()) {
			// 		int check = 0;
			// 		for (CollectableEntity item : dungeon.getInventory()) {
			// 			if (item instanceof OneRing) {
			// 				check = 1;
			// 				break;
			// 			}
			// 		}
			// 		if (check == 0) {
			// 			dungeon.addItemToInventory(ring);
			// 			dungeon.setHistoricalEntCount(dungeon.getHistoricalEntCount() + 1);
			// 		}
			// 	}

			// 	// remove
			// 	dungeon.removeEntity(entity);
			// 	break;
			// }
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
			if (enemy instanceof Mercenary || enemy instanceof ZombieToast) {
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
