package dungeonmania;
import dungeonmania.allEntities.Boulder;
import dungeonmania.allEntities.Door;
import dungeonmania.allEntities.Wall;
import dungeonmania.allEntities.ZombieToastSpawner;
import dungeonmania.util.Position;


public abstract class Entity {
    
    Position position;
    String type;

    public Entity(Position position, String type) {
        this.position = position;
        this.type = type;
    }

	public Position getPosition() {
		return position;
	}

	public String getType() {
		return type;
	}

    public boolean isInteractable() {
        return (
            type == "player"
            || type == "boulder"
            || type == "switch"
            || type == "door"
            || type == "treasure"
            || type == "wood"
            || type == "key"
            || type == "bomb"
            || type == "health_potion"
            || type == "invisibility_potion"
            || type == "invincibility_potion"
            || type == "shield"
            || type == "sword"
            || type == "armour"
            || type == "one_ring"            
        );
    }

	public boolean collide(Entity entity) {
		if (entity instanceof Boulder) {
			return false;
		} else if (entity instanceof Wall) {
			return false;
		} else if (entity instanceof ZombieToastSpawner) {
			return false;
		} else if (entity instanceof Door) {
			return false;
		}
		return true;
	}

}
