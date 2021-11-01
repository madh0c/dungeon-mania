package dungeonmania;

public class StandardMode implements Mode {

	@Override
	public int getZombieTick() {
		return 20;
	}

	@Override
	public boolean enemyAttack() {
		return true;
	}

	@Override
	public int getHealth() {
		return 100;
	}

	@Override
	public int getInvincDuration() {
		return 8;
	}
	
}
