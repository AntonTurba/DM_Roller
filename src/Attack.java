
public class Attack {
	
	private String attackName;
	
	private int numOfDice;
	
	private int typeOfDice;
	
	private int diceModifier;
	
	public Attack(String name, int numOfDice, int typeOfDice, int diceModifier) {
		this.attackName = name;
		this.numOfDice = numOfDice;
		this.typeOfDice = typeOfDice;
		this.diceModifier = diceModifier;
	}
}
