package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContextAttributeListener;
import javax.xml.stream.events.EndElement;

import dungeonmania.CollectibleEntity;
import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovableEntity;
import dungeonmania.allEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class Battle {
	public static void battle(Entity entity, Dungeon dungeon) {
		MovableEntity enemy = (MovableEntity)entity;
		while (enemy.getHealth() > 0 && dungeon.getPlayer().getHealth() > 0) {
		// while (enemy != null && dungeon.getPlayer().getHealth() > 0) {
			// if player invincible
			if (dungeon.getPlayer().getInvincibleTickDuration() > 0) {
				dungeon.removeEntity(entity);
				break;
			}

			int playerHp = dungeon.getPlayer().getHealth();
			int enemyHp = enemy.getHealth();

			int playerAtk = dungeon.getPlayer().getAttack();
			int enemyAtk = enemy.getBaseAttack();
			List<CollectibleEntity> toBeRemoved = new ArrayList<CollectibleEntity>();

			
			for (CollectibleEntity item : dungeon.getInventory()) {
				if (item instanceof Sword) {						
					Sword sword = (Sword) item;
					if (sword.getDurability() == 0) {
						toBeRemoved.add(item);
						continue;
					}
					playerAtk += sword.getExtraDamage();
					sword.setDurability(sword.getDurability() - 1);						
				}

				if (item instanceof Bow) {
					Bow bow = (Bow) item;
					if (bow.getDurability() == 0) {
						toBeRemoved.add(item);
						continue;
					}
					playerAtk += bow.getExtraDamage();
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
			}

			// remove items w/ no durability
			dungeon.getInventory().removeAll(toBeRemoved);

			// Character Health = Character Health - ((Enemy Health * Enemy Attack Damage) / 10)
			// Enemy Health = Enemy Health - ((Character Health * Character Attack Damage) / 5)
			dungeon.getPlayer().setHealth(playerHp - ((enemyHp * enemyAtk) / 10));
			enemy.setHealth(enemyHp - ((playerHp * playerAtk) / 5));

			if (dungeon.getPlayer().getHealth() <= 0) {
				for (CollectibleEntity item : dungeon.getInventory()) {
					if (item instanceof OneRing) {
						dungeon.getPlayer().setHealth(100);
					}
				}
				dungeon.removeEntity(dungeon.getPlayer());	
				break;				
			} 

			if (enemy.getHealth() <= 0) {
				if (enemy instanceof Mercenary || enemy instanceof ZombieToast) {
					Random rand = new Random();
					if (rand.nextInt(20) % 20 == 1) {
						Armour armour = new Armour(enemy.getPosition());
						dungeon.addEntity(armour);
					}
				}
				dungeon.removeEntity(entity);
				break;
			}
		}
	}
}
