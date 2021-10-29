package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GoalSub implements GoalNode {

	// private String goal;
	private Map<String, Object> subgoal;

	public GoalSub(Map<String, Object> subgoal) {
		this.subgoal = subgoal;
	}
	@Override
	public String evaluate() {
		List<String> delimList = new ArrayList<> ();
		List<String> moreGoals = new ArrayList<> ();
		
		for (Map.Entry<String, Object> sub : subgoal.entrySet()) {
			String key = sub.getKey();
			String delim = "";
			String goal1 = "";
			String goal2 = "";

			if (key.equals("goal")) {
				GoalGoal goal = new GoalGoal(sub.getValue());
				delim = goal.evaluate();
				if (!delim.equals("AND") && !delim.equals("OR")) {
					return delim;
				}
				delimList.add(delim);
			} else if (key.equals("subgoals")) {
				List<Map<String, Object>> temp = (List<Map<String, Object>>)sub.getValue();

				Map<String, Object> sGoals1 = temp.get(0);
				GoalSub sGoal1 = new GoalSub(sGoals1);
				goal1 = sGoal1.evaluate();

				Map<String, Object> sGoals2 = temp.get(1);
				GoalSub sGoal2 = new GoalSub(sGoals2);
				goal2 = sGoal2.evaluate();
				moreGoals.add(goal1);
				moreGoals.add(goal2);
			}
		}
		
		for (int i = 0; i < moreGoals.size(); i++) {
			return "(" + moreGoals.get(i)+ " " + delimList.get(i) + " " + moreGoals.get(i+1) + ")";
		}
		return null;
	}
	
}
