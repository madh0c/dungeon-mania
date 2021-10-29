package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.*;

public class EntityFactory {
	public static Entity createEntity(String type, Position position) {
		if (type.contains("wall"))
			return new Wall(position);
		else if (type.contains("exit"))
			return new Exit(position);
		else if (type.contains("boulder"))
			return new Boulder(position);	
		else if (type.contains("switch"))
			return new Switch(position);
		else if (type.contains("Door"))
			return new Door(position);
		else if (type.contains("zombie_toast_spawner"))
			return new ZombieToastSpawner(position);
		else if (type.contains("spider"))
			return new Spider(position);
		else if (type.contains("zombie_toast"))
			return new ZombieToast(position);
		else if (type.contains("mercenary"))
			return new Mercenary(position);
		else if (type.contains("treasure"))
			return new Treasure(position);
		else if (type.contains("key"))
			return new Key(position);
		else if (type.contains("health_potion"))
			return new HealthPotion(position);
		else if (type.contains("invincibility_potion"))
			return new InvincibilityPotion(position);
		else if (type.contains("invisibility_potion"))
			return new InvisibilityPotion(position);
		else if (type.contains("wood"))
			return new Wood(position);
		else if (type.contains("arrow"))
			return new Arrow(position);
		else if (type.contains("bomb"))
			return new Bomb(position);
		else if (type.contains("sword"))
			return new Sword(position);
		else if (type.contains("player"))
			return new Player(position);
		return null;
	}
}
