import java.util.Comparator;
import java.util.List;

public class Encounter {
	
	private String encounterName;
	
	private List<Opponent> adversaries;
		
	public Encounter(String name, List<Opponent> adversaries) {
		this.setEncounterName(name);
		this.adversaries = adversaries;
	}
	
	// Every time a new opponent is added, the sort method is called
	public void addOpponent(Opponent o) {
		adversaries.add(o);
		
		sortOpponents();
	}
	
	public void sortOpponents() {
		adversaries.sort(Comparator.comparing(Opponent::getEnemyName));
	}

	public String getEncounterName() {
		return encounterName;
	}

	public void setEncounterName(String encounterName) {
		this.encounterName = encounterName;
	}
}
