import java.util.Comparator;
import java.util.List;

public class Encounter {
	
	private int encounterId;
	
	private String encounterName;
	
	private List<Opponent> adversaries;
		
	public Encounter(int id, String name) {
		this.setEncounterName(name);
		this.encounterId = id;
	}
	
	// Every time a new opponent is added, the sort method is called
	public void addOpponent(Opponent o) {
		adversaries.add(o);
		
		sortOpponents();
	}
	
	public void sortOpponents() {
		adversaries.sort(Comparator.comparing(Opponent::getName));
	}

	public String getEncounterName() {
		return encounterName;
	}

	public void setEncounterName(String encounterName) {
		this.encounterName = encounterName;
	}
}
