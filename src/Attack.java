
public class Attack {
	
	private String attackName;
	
	private int numOfDice;
	
	private int typeOfDice;
	
	private int diceModifier;
	
	private String attackInfo;
	
	public Attack(String name, int numOfDice, int typeOfDice, int diceModifier, String info) {
		this.setAttackName(name);
		this.setNumOfDice(numOfDice);
		this.setTypeOfDice(typeOfDice);
		this.setDiceModifier(diceModifier);
		this.setAttackInfo(info);
	}

	public int getNumOfDice() {
		return numOfDice;
	}

	public void setNumOfDice(int numOfDice) {
		this.numOfDice = numOfDice;
	}

	public String getAttackName() {
		return attackName;
	}

	public void setAttackName(String attackName) {
		this.attackName = attackName;
	}

	public int getTypeOfDice() {
		return typeOfDice;
	}

	public void setTypeOfDice(int typeOfDice) {
		this.typeOfDice = typeOfDice;
	}

	public int getDiceModifier() {
		return diceModifier;
	}

	public void setDiceModifier(int diceModifier) {
		this.diceModifier = diceModifier;
	}

	public String getAttackInfo() {
		return attackInfo;
	}

	public void setAttackInfo(String attackInfo) {
		this.attackInfo = attackInfo;
	}
}
