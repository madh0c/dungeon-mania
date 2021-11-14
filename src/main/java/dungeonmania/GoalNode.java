package dungeonmania;


public interface GoalNode {
	
	/**
	 * Evaluates each node dynamically to see if the goal has been achieved.
	 * @param dungeon the dungeon for which the goals are being evaluated for
	 * @return if the goal has been achieved.
	 */
	public Boolean evaluate(Dungeon dungeon);

	/**
	 * Returns the goal in string form.
	 * @return the goal as a string.
	 */
	public String remainingString();
}

