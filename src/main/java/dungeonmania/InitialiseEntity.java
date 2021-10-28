package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.*;

public class InitialiseEntity {
	public Entity createEntity(String type, Position position) {
		// Hey can we get our lab marked in Dragonfruit?
		if (type.equals("wall"))
			return new Wall(position);
		else if (type.equals("exit"))
			return new Exit(position);
		else if (type.equals("boulder"))
			return new Boulder(position);	
		else if (type.equals("switch"))
			return new Switch(position);
		else if (type.equals("Door"))
			return new Door(position);
		else if (type.equals("zombie_toast_spawner"))
			return new ZombieToastSpawner(position);
		else if (type.equals("spider"))
			return new Spider(position);
		else if (type.equals("zombie_toast"))
			return new ZombieToast(position);
		else if (type.equals("mercenary"))
			return new Mercenary(position);
		else if (type.equals("treasure"))
			return new Treasure(position);
		else if (type.equals("key"))
			return new Key(position);
		else if (type.equals("health_potion"))
			return new HealthPotion(position);
		else if (type.equals("invincibility_potion"))
			return new InvincibilityPotion(position);
		else if (type.equals("invisibility_potion"))
			return new InvisibilityPotion(position);
		else if (type.equals("wood"))
			return new Wood(position);
		else if (type.equals("arrow"))
			return new Arrow(position);
		else if (type.equals("bomb"))
			return new Bomb(position);
		else if (type.equals("sword"))
			return new Sword(position);

		return null;
	}
}
