package dungeonmania.allEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;


public class Player extends Entity {
    int health;
    boolean visible;

    public Player(Position position) {
        super(position, "player");
    }

    public void setHealth(int newHealth) {
        health = newHealth;
    }

    public int getHealth() {
        return health;
    }

    public boolean isVisible() {
        return visible;
    }

    
}
