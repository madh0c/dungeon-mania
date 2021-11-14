package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.*;

public abstract class EntityFactory {
	/**
	 * Create and return a new Entity, depending on the given type
	 * @param id		Id of returned Entity
	 * @param type		Type of returned Entity
	 * @param position	Position of returned Entity
	 * @return a new instance of the entity that should be created
	 */
	public Entity createEntity(String id, String type, Position position) {
		if (type.contains("wall"))
			return createWall(id, position);
		else if (type.contains("exit"))
			return createExit(id, position);
		else if (type.contains("boulder"))
			return createBoulder(id, position);	
		else if (type.contains("switch"))
			return createSwitch(id, position);
		// else if (type.contains("door"))
		// 	return createDoor(id, position);
		else if (type.contains("zombie_toast_spawner"))
			return createSpawner(id, position);
		else if (type.contains("spider"))
			return createSpider(id, position);
		else if (type.contains("zombie_toast"))
			return createZombieToast(id, position);
		else if (type.contains("mercenary"))
			return createMercenary(id, position);
		else if (type.contains("treasure"))
			return createTreasure(id, position);
		// else if (type.contains("key"))
		// 	return createKey(id, position);
		else if (type.contains("health_potion"))
			return createHealthPotion(id, position);
		else if (type.contains("invincibility_potion"))
			return createInvincibilityPotion(id, position);
		else if (type.contains("invisibility_potion"))
			return createInvisibilityPotion(id, position);
		else if (type.contains("wood"))
			return createWood(id, position);
		else if (type.contains("arrow"))
			return createArrow(id, position);
		else if (type.contains("bomb"))
			return createBomb(id, position);
		else if (type.contains("sword"))
			return createSword(id, position);
		else if (type.contains("bow"))
			return createBow(id, position);
		else if (type.contains("shield"))
			return createShield(id, position);
		else if (type.contains("midnight_armour"))
			return createMidnightArmour(id, position);
		else if (type.contains("armour"))
			return createArmour(id, position);
		else if (type.contains("one_ring"))
			return createOneRing(id, position);
		else if (type.contains("anduril"))
			return createAnduril(id, position);
		else if (type.contains("sun_stone"))
			return createSunStone(id, position);
		else if (type.contains("assassin"))
			return createAssassin(id, position);
		else if (type.contains("sceptre"))
			return createSceptre(id, position);
		else if (type.contains("hydra"))
			return createHydra(id, position);
		else if (type.contains("older_player"))
			return createOlderPlayer(id, position);
		else if (type.contains("time_turner"))
			return createTimeTurner(id, position);
		else if (type.contains("time_travelling_portal"))
			return createTimeTravellingPortal(id, position);

		return null;
	}

	/**
	 * Create and return a new player
	 * @param id		Id of player
	 * @param position	Position of player
	 * @return
	 */
	public abstract Player createPlayer(String id, Position position);

	/**
	 * Create and return a new portal
	 * @param id		Id of portal
	 * @param position	Position of portal
	 * @param colour	Colour of portal
	 * @return
	 */
	public Portal createPortal(String id, Position position, String colour) {
		Portal portal = new Portal(id, position, colour);
		return portal;
	}

	/**
	 * Create and return a new key
	 * @param id		Id of portal
	 * @param position	Position of portal
	 * @param corresponding	Corresponding door String for key
	 * @return
	 */
	public Key createKey(String id, Position position, int keyId) {
		Key key = new Key(id, position, keyId);
		return key;
	}

	/**
	 * Create and return a new door
	 * @param id		Id of door
	 * @param position	Position of door
	 * @param corresponding	Corresponding key String for door
	 * @return
	 */
	public Door createDoor(String id, Position position, int keyId) {
		Door door = new Door(id, position, keyId);
		return door;
	}
	
	/**
	 * Creates an instance of Wall object
	 * @param id		Id of wall
	 * @param position	Position of wall
	 * @return Wall		
	 */
	public Wall createWall(String id, Position position) {
		return new Wall(id, position);
	}

	/**
	 * Creates an instance of Exit object
	 * @param id		Id of exit
	 * @param position	Position of exit
	 * @return Exit
	 */
	public Exit createExit(String id, Position position) {
		return new Exit(id, position);
	}

	/**
	 * Creates an instance of Boulder object
	 * @param id		Id of boulder
	 * @param position	Position of boulder
	 * @return Boulder
	 */
	public Boulder createBoulder(String id, Position position) {
		return new Boulder(id, position);
	}

	/**
	 * Creates an instance of Switch object
	 * @param id		Id of switch
	 * @param position	Position of switch
	 * @return Switch
	 */
	public Switch createSwitch(String id, Position position) {
		return new Switch(id, position);
	}

	/**
	 * Creates an instance of ZombieToastSpawner object
	 * @param id		Id of zombie_toast_spawner
	 * @param position	Position of zombie_toast_spawner
	 * @return ZombieToastSpawner
	 */
	public abstract ZombieToastSpawner createSpawner(String id, Position position);

	/**
	 * Creates an instance of Spider object
	 * @param id		Id of spider
	 * @param position	Position of spider
	 * @return Spider
	 */
	public abstract Spider createSpider(String id, Position position);

	/**
	 * Creates an instance of ZombieToast object
	 * @param id		Id of zombie_toast
	 * @param position	Position of zombie_toast
	 * @return ZombieToast
	 */
	public abstract ZombieToast createZombieToast(String id, Position position);

	/**
	 * Creates an instance of Mercenary object
	 * @param id		Id of mercenary
	 * @param position	Position of mercenary
	 * @return Mercenary
	 */
	public abstract Mercenary createMercenary(String id, Position position);

	/**
	 * Creates an instance of Treasure object
	 * @param id		Id of treasure
	 * @param position	Position of treasure
	 * @return Treasure
	 */
	public Treasure createTreasure(String id, Position position) {
		return new Treasure(id, position);
	}

	/**
	 * Creates an instance of HealthPotion object
	 * @param id		Id of health_potion
	 * @param position	Position of health_potion
	 * @return HealthPotion
	 */
	public HealthPotion createHealthPotion(String id, Position position) {
		return new HealthPotion(id, position);
	}

	/**
	 * Creates an instance of InvincibilityPotion object
	 * @param id		Id of invincibility_potion
	 * @param position	Position of invincibility_potion
	 * @return InvincibilityPotion
	 */
	public InvincibilityPotion createInvincibilityPotion(String id, Position position) {
		return new InvincibilityPotion(id, position);
	}

	/**
	 * Creates an instance of InvisibilityPotion object
	 * @param id		Id of invisibility_potion
	 * @param position	Position of invisibility_potion
	 * @return InvisibilityPotion
	 */
	public InvisibilityPotion createInvisibilityPotion(String id, Position position) {
		return new InvisibilityPotion(id, position);
	}

	/**
	 * Creates an instance of Wood object
	 * @param id		Id of wood
	 * @param position	Position of wood
	 * @return Wood
	 */
	public Wood createWood(String id, Position position) {
		return new Wood(id, position);
	}

	/**
	 * Creates an instance of Arrow object
	 * @param id		Id of arrow
	 * @param position	Position of arrow
	 * @return Arrow
	 */
	public Arrow createArrow(String id, Position position) {
		return new Arrow(id, position);
	}

	/**
	 * Creates an instance of BombItem object
	 * @param id		Id of bomb_item
	 * @param position	Position of bomb_item
	 * @return BombItem
	 */
	public Bomb createBomb(String id, Position position) {
		return new Bomb(id, position);
	}

	/**
	 * Creates an instance of Sword object
	 * @param id		Id of sword
	 * @param position	Position of sword
	 * @return Sword
	 */
	public Sword createSword(String id, Position position) {
		return new Sword(id, position);
	}

	/**
	 * Creates an instance of Bow object
	 * @param id		Id of bow
	 * @param position	Position of bow
	 * @return Bow
	 */
	public Bow createBow(String id, Position position) {
		return new Bow(id, position);
	}

	/**
	 * Creates an instance of Shield object
	 * @param id		Id of shield
	 * @param position	Position of shield
	 * @return shield
	 */
	public Shield createShield(String id, Position position) {
		return new Shield(id, position);
	}

	/**
	 * Creates an instance of MidnightArmour object
	 * @param id		Id of midnight_armour
	 * @param position	Position of midnight_armour
	 * @return MidnightArmour
	 */
	public MidnightArmour createMidnightArmour(String id, Position position) {
		return new MidnightArmour(id, position);
	}

	/**
	 * Creates an instance of Armour object
	 * @param id		Id of armour
	 * @param position	Position of armour
	 * @return Armour
	 */
	public Armour createArmour(String id, Position position) {
		return new Armour(id, position);
	}

	/**
	 * Creates an instance of OneRing object
	 * @param id		Id of one_ring
	 * @param position	Position of one_ring
	 * @return OneRing
	 */
	public OneRing createOneRing(String id, Position position) {
		return new OneRing(id, position);
	}

	/**
	 * Creates an instance of Anduril object
	 * @param id		Id of anduril
	 * @param position	Position of anduril
	 * @return Anduril
	 */
	public Anduril createAnduril(String id, Position position) {
		return new Anduril(id, position);
	}

	/**
	 * Creates an instance of SunStone object
	 * @param id		Id of sun_stone
	 * @param position	Position of sun_stone
	 * @return SunStone
	 */
	public SunStone createSunStone(String id, Position position) {
		return new SunStone(id, position);
	}

	/**
	 * Creates an instance of Assassin object
	 * @param id		Id of assassin
	 * @param position	Position of assassin
	 * @return Assassin
	 */
	public abstract Assassin createAssassin(String id, Position position);

	/**
	 * Creates an instance of Sceptre object
	 * @param id		Id of sceptre
	 * @param position	Position of sceptre
	 * @return Sceptre
	 */
	public Sceptre createSceptre(String id, Position position) {
		return new Sceptre(id, position);
	}

	/**
	 * Creates an instance of Hydra object
	 * @param id		Id of hydra
	 * @param position	Position of hydra
	 * @return Hydra
	 */
	public abstract Hydra createHydra(String id, Position position);

	/**
	 * Creates an instance of OlderPlayer object
	 * @param id		Id of older_player
	 * @param position	Position of older_player
	 * @return OlderPlayer
	 */
	public abstract OlderPlayer createOlderPlayer(String id, Position position);

	/**
	 * Creates an instance of TimeTurner object
	 * @param id		Id of time_turner
	 * @param position	Position of time_turner
	 * @return TimeTurner
	 */
	public TimeTurner createTimeTurner(String id, Position position) {
		return new TimeTurner(id, position);
	}

	/**
	 * Creates an instance of SwampTile object
	 * @param id		Id of swamp_tile
	 * @param position	Position of swamp_tile
	 * @return SwampTile
	 */
	public SwampTile createSwampTile(String id, Position position, int movementFactor) {
		return new SwampTile(id, position, movementFactor);
	}

	/**
	 * Creates an instance of TimeTravellingPortal object
	 * @param id		Id of time_travelling_portal
	 * @param position	Position of time_travelling_portal
	 * @return TimeTravellingPortal
	 */
	public TimeTravellingPortal createTimeTravellingPortal(String id, Position position) {
		return new TimeTravellingPortal(id, position);
	}
}
