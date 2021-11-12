package dungeonmania.allEntities;

import java.util.Random;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class ZombieToast extends MovingEntity {

	private int randSeed;
	private Random random;

    public ZombieToast(String id, Position position, boolean enemyAttack) {
        super(id, position, "zombie_toast", enemyAttack);
        super.setHealth(20);
        super.setBaseAttack(10);
		Random rand = new Random();
		setSeed(rand.nextInt());
		this.randSeed = rand.nextInt(100);
		this.random = new Random(randSeed);
    }

	public void setSeed(int randSeed) {
		this.randSeed = randSeed;
	}

	public int getSeed() {
		return randSeed;
	}

	@Override
	public void move(Dungeon dungeon) {
		// Generate random number
		int num = random.nextInt(4);

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

	@Override
	public void moveScared(Dungeon dungeon) {
		// direction
		Direction dir = Direction.NONE;
		Player player = dungeon.getPlayer();

		if (player.getPosition().getY() != getPosition().getY()) {
			// If player is to the up of merc
			if (player.getPosition().getY() < getPosition().getY()) {
				dir = Direction.DOWN;				
			} 
			// If on down side
			else {
				dir = Direction.UP;
			}
		}

		// left right movement
		if (player.getPosition().getX() != getPosition().getX()) {
			// If player is to the left of merc
			if (player.getPosition().getX() < getPosition().getX()) {
				dir = Direction.RIGHT;	
			} 
			// If on right side
			else {
				dir = Direction.LEFT;	
			}
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
