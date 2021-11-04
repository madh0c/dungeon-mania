package dungeonmania.allEntities;

import java.util.Random;

import dungeonmania.CollectibleEntity;
import dungeonmania.util.Position;


public class OneRing extends CollectibleEntity {

    private Random random;
	
	public OneRing(Position position, long seed) {
        super(position, "one_ring");
		random = new Random(seed);
    }

	public boolean doesSpawn() {
		//Ring spawns 20% chance 
		if (random.nextInt(100) % 5 == 0) {
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
