package dungeonmania;
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
}
