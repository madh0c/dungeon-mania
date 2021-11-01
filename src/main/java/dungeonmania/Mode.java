package dungeonmania;

public interface Mode {
	/**
	 * Get the number of ticks a zombie spawns:
	 * @return 	Peaceful: 20
	 * 			Standard: 20
	 * 			Hard: 15
	 */			
	public int getZombieTick();

	/**
	 * Returns in the enemies attack the player or not:
	 * @return	Peaceful: No
	 * 			Standard: Yes
	 * 			Hard: Yes
	 */
	public boolean enemyAttack();

	/**
	 * Returns starting health of player (also reviving health)
	 * @return	Peaceful: 100
	 * 			Standard: 100
	 * 			Hard: 60
	 */
	public int getHealth();

	/**
	 * Returns how long the invincibility potion lasts
	 * @return	Peaceful: 8
	 * 			Standard: 8
	 * 			Hard: 0
	 */
	public int getInvincDuration();

}
