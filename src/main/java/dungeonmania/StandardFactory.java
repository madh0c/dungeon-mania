package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.Position;

public class StandardFactory extends EntityFactory {
	@Override
	public Entity createEntity(String id, String type, Position position) {
		if (type.contains("wall"))
			return new Wall(id, position);
		else if (type.contains("exit"))
			return new Exit(id, position);
		else if (type.contains("boulder"))
			return new Boulder(id, position);	
		else if (type.contains("switch"))
			return new Switch(id, position);
		else if (type.contains("door"))
			return new Door(id, position);
		else if (type.contains("zombie_toast_spawner"))
			return new ZombieToastSpawner(id, position, 20);
		else if (type.contains("spider"))
			return new Spider(id, position, true);
		else if (type.contains("zombie_toast"))
			return new ZombieToast(id, position, true);
		else if (type.contains("mercenary"))
			return new Mercenary(id, position, true);
		else if (type.contains("treasure"))
			return new Treasure(id, position);
		else if (type.contains("key"))
			return new Key(id, position);
		else if (type.contains("health_potion"))
			return new HealthPotion(id, position);
		else if (type.contains("invincibility_potion"))
			return new InvincibilityPotion(id, position);
		else if (type.contains("invisibility_potion"))
			return new InvisibilityPotion(id, position);
		else if (type.contains("wood"))
			return new Wood(id, position);
		else if (type.contains("arrow"))
			return new Arrow(id, position);
		else if (type.contains("bomb"))
			return new BombItem(id, position);
		else if (type.contains("sword"))
			return new Sword(id, position);
		else if (type.contains("bow"))
			return new Bow(id, position);
		else if (type.contains("shield"))
			return new Shield(id, position);
		else if (type.contains("midnight_armour"))
			return new MidnightArmour(id, position);
		else if (type.contains("armour"))
			return new Armour(id, position);
		else if (type.contains("one_ring"))
			return new OneRing(id, position);
		else if (type.contains("anduril"))
			return new Anduril(id, position);
		else if (type.contains("sun_stone"))
			return new SunStone(id, position);
		else if (type.contains("assassin"))
			return new Assassin(id, position, true);
		else if (type.contains("sceptre"))
			return new Sceptre(id, position);
		return null;
	}

	@Override
	public Player createPlayer(String id, Position position) {
		Player player = new Player(id, position, 100, true, 8);
		return player;
	}
}
