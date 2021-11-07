package dungeonmania.allEntities;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.MovingEntity;
import dungeonmania.util.Position;


public class ZombieToastSpawner extends Entity {

	private int tickrate;

    public ZombieToastSpawner(String id, Position position, int tickrate) {
        super(id, position, "zombie_toast_spawner");
		this.tickrate = tickrate;
    }

	/**
	 * Spawns in a Zombie in a cardinally-adjacent square to this spawner periodically<p>
	 * Peaceful Mode - Every 20 ticks<p>
	 * Standard Mode - Every 20 ticks<p>
	 * Hard Mode - Every 20 ticks
	 * @param currentDungeon	Current dungeon of spawner
	 */
    public void spawnZombie(Dungeon currentDungeon) {

		if (currentDungeon.getTickNumber() % tickrate != 1 || currentDungeon.getTickNumber() <= 1) {
			return;
		}

		List<Position> spawnOrder = this.getPosition().getCardinallyAdjPositions();

		for (Position spawnPoint : spawnOrder) {
			List<Entity> conflictEntities = currentDungeon.getEntitiesOnCell(spawnPoint);
			boolean canSpawn = true;
			for (Entity conflictE : conflictEntities) {
				if (conflictE.getType().equals("boulder") || conflictE.getType().equals("wall") || conflictE instanceof MovingEntity) {
					canSpawn = false;

				}
			}
			if (canSpawn) {
				int newId = currentDungeon.getHistoricalEntCount();				
				Entity zombie = currentDungeon.getFactory().createEntity(String.valueOf(newId), "zombie_toast", spawnPoint);
				currentDungeon.addEntity(zombie);
                break;
			}
		}
	}
}