package dungeonmania.allEntities;

import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovableEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class ZombieToast extends MovableEntity {

	private int randSeed;

    public ZombieToast(String id, Position position) {
        super(id, position, "zombie_toast");
        super.setHealth(20);
        super.setBaseAttack(10);
    }

	public void setSeed(int randSeed) {
		this.randSeed = randSeed;
	}

	public int getSeed() {
		return randSeed;
	}

	// @Override
	public void move(Dungeon dungeon) {
		// Generate random number

		// Get the seed of the random sequence, so can use for testing

		Random rand = new Random();
		setSeed(rand.nextInt());

		Random random = new Random(getSeed());
		int num = random.nextInt(3);

		Direction dir = Direction.NONE;
		switch (num) {
			case 0:
				dir = Direction.UP;
				break;
			case 1:
				dir = Direction.DOWN;
				break;
			case 2:
				dir = Direction.LEFT;
				break;
			case 3:
				dir = Direction.RIGHT;
				break;
			default:
				break;
		}

		// Check if collideable with the direciton
		Position newPos = getPosition().translateBy(dir);
		for (Entity entity : dungeon.getEntitiesOnCell(newPos)) {
			if (!collide(entity, dungeon)) {
				return;
			}
		}
		// If yes, then set pos
		setPosition(newPos);
		

	}

}
