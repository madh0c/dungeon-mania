package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Player extends Entity {
    int health;
	int attack;
    boolean visible;

    public Player(Position position) {
        super(position, "player");
    }

    public void setHealth(int newHealth) {
        health = newHealth;
    }

	public int getAttack() {
		return attack;
	}

	public void setAttack(int newAttack) {
        attack = newAttack;
    }

    public int getHealth() {
        return health;
    }

    public boolean isVisible() {
        return visible;
    }

    
}
