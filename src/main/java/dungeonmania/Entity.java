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
}
