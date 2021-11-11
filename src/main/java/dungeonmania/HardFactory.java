package dungeonmania;

import dungeonmania.allEntities.*;
import dungeonmania.util.Position;

public class HardFactory extends EntityFactory {
	@Override
	public Player createPlayer(String id, Position position) {
		Player player = new Player(id, position, 60, true, 0);
		return player;
	}

	@Override
	public ZombieToastSpawner createSpawner(String id, Position position) {
		return new ZombieToastSpawner(id, position, 15);
	}

	@Override
	public Spider createSpider(String id, Position position) {
		return new Spider(id, position, true);
	}

	@Override
	public ZombieToast createZombieToast(String id, Position position) {
		return new ZombieToast(id, position, true);
	}

	@Override
	public Mercenary createMercenary(String id, Position position) {
		return new Mercenary(id, position, true);
	}

	@Override
	public Assassin createAssassin(String id, Position position) {
		return new Assassin(id, position, true);
	}
}
