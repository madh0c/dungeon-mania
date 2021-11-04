package dungeonmania.allEntities;

import java.util.Random;

import dungeonmania.CollectibleEntity;
import dungeonmania.util.Position;


public class OneRing extends CollectibleEntity {

    private Random random;

	public OneRing(String id, Position position) {
        super(id, position, "one_ring");
    }

	public boolean doesSpawn() {
		random = new Random();
		int rand = random.nextInt(100);
		//Ring spawns 10% chance 
		if (rand % 10 == 0) {
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
