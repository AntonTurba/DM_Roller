import java.util.List;

public class Opponent {
	
	private String enemyName;
	
	private String size;
	
	private int armorClass;
	
	private int healthPoints;
	
	private int currentHealthPoints;
	
	private List<Attack> attacks;
	
	public Opponent(String name, String size, int ac, int hp, List<Attack> attacks) {
		this.enemyName = name;
		this.size = size;
		this.armorClass = ac;
		this.healthPoints = hp;
		this.attacks = attacks;
		this.currentHealthPoints = hp;
	}

	public String getEnemyName() {
		return enemyName;
	}
}
