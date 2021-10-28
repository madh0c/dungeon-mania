package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.*;

public class InitialiseEntity {
	public Entity createEntity(String type, Position position) {
		switch (type) {
			case "wall":
				return new Wall(position);
			case "exit":
				return new Exit(position); 
			case "boulder":
				return new Boulder(position); 
			case "switch":
				return new Switch(position); 

			case "door":
				Door door = new Door(position);
				result.put(String.valueOf(i), door);
				break;

			case "portal":
				String colour = currentEntity.get("colour");
				Portal portal = new Portal(position, colour);
				result.put(String.valueOf(i), portal);
				break;

			case "zombie_toast_spawner":
				ZombieToastSpawner spawner = new ZombieToastSpawner(position);
				result.put(String.valueOf(i), spawner);
				break;

			case "spider":
				Spider spider = new Spider(position);
				result.put(String.valueOf(i), spider);
				break;

			case "zombie_toast":
				ZombieToast zombie = new ZombieToast(position);
				result.put(String.valueOf(i), zombie);
				break;

			case "mercenary":
				Mercenary merc = new Mercenary(position);
				result.put(String.valueOf(i), merc);
				break;

			case "treasure":
				Treasure treasure = new Treasure(position);
				result.put(String.valueOf(i), treasure);
				break;

			case "key":
				Key key = new Key(position);
				result.put(String.valueOf(i), key);
				break;

			case "health_potion":
				HealthPotion health = new HealthPotion(position);
				result.put(String.valueOf(i), health);
				break;

			case "invincibility_potion":
				InvincibilityPotion invic = new InvincibilityPotion(position);
				result.put(String.valueOf(i), invic);
				break;

			case "invisibility_potion":
				InvisibilityPotion invis = new InvisibilityPotion(position);
				result.put(String.valueOf(i), invis);
				break;

			case "wood":
				Wood wood = new Wood(position);
				result.put(String.valueOf(i), wood);
				break;

			case "arrow":
				Arrow arrow = new Arrow(position);
				result.put(String.valueOf(i), arrow);
				break;

			case "bomb":
				Bomb bomb = new Bomb(position);
				result.put(String.valueOf(i), bomb);
				break;

			case "sword":
				Sword sword = new Sword(position);
				result.put(String.valueOf(i), sword);
				break;

		}

		

		return null;
	}
}
