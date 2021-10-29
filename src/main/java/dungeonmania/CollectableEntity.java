package dungeonmania;

import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entity {

	private int counter = 0;

    public CollectableEntity(Position position, String type) {
        super(position, type);
		this.counter = counter;
    }

	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
}
