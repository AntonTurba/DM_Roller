import java.util.ArrayList;
import java.util.List;

public class Opponent {
	
	private int opponentId;
	
	private String name;
	
	private int size;
	
	private int armorClass;
	
	private int healthPoints;
	
	private int currentHealthPoints;
	
	private List<Attack> attacks;
	
	private int speed;
	
	private int initiative;
	
	public Opponent(int id, String name, List<Attack> attacks, int hp, int ac, int size, int speed) {
		this.setOpponentId(id);
		this.setName(name);
		this.setSize(size);
		this.setArmorClass(ac);
		this.setHealthPoints(hp);
		this.setAttacks(attacks);
		this.setCurrentHealthPoints(hp);
		this.setSpeed(speed);
	}
	
	public Opponent(int id, String name, String attacks, int hp, int ac, int size, int speed) {
		this.setOpponentId(id);
		this.setName(name);
		this.setSize(size);
		this.setArmorClass(ac);
		this.setHealthPoints(hp);
		this.setAttacks(initAttackList(attacks));
		this.setCurrentHealthPoints(hp);
		this.setSpeed(speed);
	}
	
	public List<Attack> initAttackList(String unParsedAttacks) {
		Attack tempA;
		List<Attack> parsedAttacks = new ArrayList<Attack>();
		
		String[] completeAttacks = unParsedAttacks.split("|");
		
		for (String s : completeAttacks) {
			if (s.length() > 1) {
				String[] attack = s.split("-");
				tempA = new Attack(
						attack[0],
						Integer.parseInt(attack[1]),
						Integer.parseInt(attack[2]),
						Integer.parseInt(attack[3]),
						attack[4]);
				
				parsedAttacks.add(tempA);		}
		}
		
		return parsedAttacks;
	}
	
	public String getOriginalAttackStringFormat() {
		
		String deParsedAttacks = "";
		
		for (Attack a : attacks) {
			deParsedAttacks += a.getAttackName() 
					+ "-" + a.getNumOfDice() 
					+ "-" + a.getTypeOfDice() 
					+ "-" + a.getDiceModifier() 
					+ "-" + a.getAttackInfo() 
					+ "|"; 
		}
		
		return deParsedAttacks;
	}

	public String getName() {
		return name;
	}

	public int getOpponentId() {
		return opponentId;
	}

	public void setOpponentId(int opponentId) {
		this.opponentId = opponentId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getArmorClass() {
		return armorClass;
	}

	public void setArmorClass(int armorClass) {
		this.armorClass = armorClass;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public int getCurrentHealthPoints() {
		return currentHealthPoints;
	}

	public void setCurrentHealthPoints(int currentHealthPoints) {
		this.currentHealthPoints = currentHealthPoints;
	}

	public List<Attack> getAttacks() {
		return attacks;
	}

	public void setAttacks(List<Attack> attacks) {
		this.attacks = attacks;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
