package dungeonmania.allEntities;

import java.util.Random;

import dungeonmania.CollectableEntity;
import dungeonmania.util.Position;


public class OneRing extends CollectableEntity {

    private Random random;

	public OneRing(String id, Position position) {
        super(id, position, "one_ring");
    }

	/**
	 * 10% chance of spawning
	 * @return
	 */
	public boolean doesSpawn() {
		random = new Random();
		int rand = random.nextInt(100);
		//Ring spawns 5% chance 
		if (rand % 20 == 0) {
			return true;
		} 
		return false;
	}
	/*
	public static void main(String[] args) {
        OneRing g = new OneRing(new Position(0,0), 0);
        for (int i = 0; i < 100; i++) {
            if (g.doesSpawn()) {
                System.out.println("Ring has spawned");
            } else {
                System.out.println("No ring for you");
            }
        }
    }
	*/
}
