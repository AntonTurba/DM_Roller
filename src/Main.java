import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

	private static Scanner scanner = new Scanner(System.in);
	private static DM dm;
	private static Connection dbConn = null;
	private static PreparedStatement pst;
	private static ResultSet rs;
	static String dbPath = "jdbc:sqlite:DM_Helper_DB.db";
	static String dbPath2 = "jdbc:sqlite:D:/School/Fall 2018/Java 4/Final/DM_Roller-master/DM_Roller-master/resources/DM_Helper_DB.db";
	static String dbPath3 = "jdbc:sqlite:C:/Users/23991820/Downloads/DM_Roller-master/DM_Roller-master/resources/DM_Helper_DB.db";

	
	public static void main(String[] args) {
		
		// Tests if the JDBC driver is setup correctly
		// If it is, then the database is initialized
		try {
			
			Class.forName("org.sqlite.JDBC");
			dbConn = DriverManager.getConnection(dbPath3);
			
		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		print("Welcome, please load or create new DM profile");
		userSelectDm();
	}
	
	// Allows the user to select the DM profile they want to load
	private static void userSelectDm() {
		boolean validProfile = false;
		
		while (!validProfile) {
			print("1: Create new DM profile");
			print("2: Load DM profile");
			print("3: Exit");
			
			String userInput = getStringInput();
			
			switch (userInput) {
				// Sends the user to a method for creating a new DM then calls the load DM method
				case "1":
					if (createNewDM(userInput)) {
						getDms();
						validProfile = true;
					} else {
						print("Failed to create new profile, try again");
					}
					break;
					
				case "2":
					// Goes directly to the load DM method
					getDms();
					validProfile = true;
					break;
					
				case "3":
					closeProgram();
					break;
					
				default:
					print("Input not recognized, please try again");
					break;
			}
		}
	}
	

	// Grabs a list of dm names and ids and iterates through it for the user to select one.
	private static void getDms() {

		HashMap<Integer, String> dmData = new HashMap<>();
		boolean isValid = false;
		int input = 0;
		
		
		try {
			
			pst = dbConn.prepareStatement("select * from DM");
			rs = pst.executeQuery();
			
			if (rs.next()) {
				do {
					dmData.put(rs.getInt("DM_ID"), rs.getString("DMName"));
					print(rs.getInt("DM_ID") + ":" + rs.getString("DMName"));
					
				} while (rs.next());
			}
			
			print("Enter the dm id you want to load");
			
			while (!isValid) {
			    input = getIntInput();
				
				for (Integer key : dmData.keySet()) {
					if (input == key) {
						isValid = true;
						break;
					} else if (input == -1) {
						closeProgram();
					} 
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		loadDm(input);
	}
	
	// Creates new DM based on user input
	private static boolean createNewDM(String userInput) {
		print("Enter the name of the new DM");
		String name = getStringInput();
		try {
			pst = dbConn.prepareStatement("insert into DM values (null , '"+name+"')");
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	// Starts the process of building the DM object and it's sub objects
	private static void loadDm(Integer dmId) {
		
		try {
			pst = dbConn.prepareStatement("select * from DM where DM_ID =?");
			pst.setInt(1, dmId);
			
			rs = pst.executeQuery();
			dm = new DM(rs.getString("DMName"), rs.getInt("DM_ID"));
			
			// Get campaigns associated with the dm
			pst = dbConn.prepareStatement("select * from Campaign where DM_ID =?");
			pst.setInt(1, dmId);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				do {
					Campaign c = new Campaign(rs.getInt("CampID"), rs.getString("CampName"));
					dm.addCampaign(c);
				} while (rs.next());
			}
			
			// Get encounters associated with the campaign
			List<Campaign> campaigns = dm.getCampaigns();
			
			for (Campaign c : campaigns) {
				c.setCampaignEncounters(getEncounters(c.getCampId()));
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		showGeneralOptions();
	}
	
	// Gets all of the encounters associated with a DM
	private static List<Encounter> getEncounters(int campId) {
		ResultSet rsEnc;
		List<Encounter> encounters = new ArrayList<Encounter>();
		
		try {
			pst = dbConn.prepareStatement("select * from Camp_Enc where CampaignID =?");
			pst.setInt(1, campId);
			rsEnc = pst.executeQuery();
			
			List<Integer> encIds = new ArrayList<Integer>();
			
			// 
			if (rsEnc.next()) {
				do {
					encIds.add(rsEnc.getInt("EncounterID"));
				} while (rsEnc.next());
			}
			
			for (int e : encIds) {
				pst = dbConn.prepareStatement("select * from Encounter where EncounterID =?");
				pst.setInt(1, e);
				rsEnc = pst.executeQuery();
				
				if (rsEnc.next()) {
					do {
						Encounter encounter = new Encounter(rsEnc.getInt("EncounterID"), rsEnc.getString("EncounterName"));
						encounters.add(encounter);
					} while (rsEnc.next());
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return encounters;
	}
	
	private static void showGeneralOptions() {
		print("1: Opponent");
		print("2: Encounter");
		print("3: Campaign");
		print("4: Start game");
		print("4: Log out");
		
		boolean validSelection = false;
		
		while (!validSelection) {
			String userInput = getStringInput();
			
			switch (userInput) {
				case "1":
					showOpponentOptions();
					break;
				case "2":
					showEncounterOptions();
					break;
				case "3":
					showCampaignOptions();
					break;
				case "4":
					startGame();
					break;
				case "5":
					dm = null;
					userSelectDm();
					break;
				default:
					print("Input not recognized, please try again");
					break;
			}
		}
	}
	
	private static void startGame() {
		print("1: Select campaign");
		print("2: Go back");
	}
	
	private static void showOpponentOptions() {
		print("1: Create new opponent");
		print("2: Modify opponent");
		print("3: Delete opponent");
		print("4: Go back");
		
		boolean validSelection = false;
		
		while (!validSelection) {
			String userInput = getStringInput();
			
			switch (userInput) {
				case "1":
					createNewOpponent();
					break;
				case "2":
					modifyOpponent();
					break;
				case "3":
					deleteOpponent();
					break;
				case "4":
					showGeneralOptions();
					break;
				default:
					print("Input not recognized, please try again");
					break;
			}
		}
	}
	
	private static void createNewOpponent() {
		
		print("\n\n  Enter Opponent name");
		String name = getStringInput();
		
		print("Enter number of attacks you want to add");
		int numAttacks = getIntInput();
		String allAttacks = "";
		
		// Need to consume newline character before it reads the input
		scanner.nextLine();
		
		for (int i = 0; i < numAttacks;i++) {
			print("Enter attack name");
			String attackName = getStringInput();
			
			print("Enter number of damage dice (In integer form)");
			int numDamageDice = getIntInput();
			
			print("Enter dice type (d4 would be written as just 4)");
			int diceType = getIntInput();
			
			print("Enter attack bonus (+2 would be written as just 2, if no bonus, write 0)");
			int bonus = getIntInput();
			
			// Need to consume newline character before it reads the input
			scanner.nextLine();
			
			print("Enter any attack information you want to store");
			String attackInfo = getStringInput();
			
			// TODO CHECK DATA FOR MISTAKES
			String compiledAttack = attackName + "-" + numDamageDice + "-" 
			+ diceType  + "-" + bonus + "-" + attackInfo;
			
			allAttacks += compiledAttack + "|";
		}
		
		print("Enter hitpoints");
		int hitpoints = getIntInput();
		
		print("Enter Armorclass");
		int ac = getIntInput();
		
		print("Enter size as a number (0 is small, 1 medium, 2 large)");
		int size = getIntInput();
		
		print("Enter speed");
		int speed = getIntInput();
		
		try {
			
			dbConn.close();
			dbConn = DriverManager.getConnection(dbPath2);
			pst = dbConn.prepareStatement("insert into Opponent "
					+ "(Name, Attacks, HitPoints, "
					+ "ArmorClass, Size, Speed) values (?,?,?,?,?,?)");
			
			pst.setString(1, name);
			pst.setString(2, allAttacks);
			pst.setInt(3, hitpoints);
			pst.setInt(4, ac);
			pst.setInt(5, size);
			pst.setInt(6, speed);
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			showOpponentOptions();
		}
		
		showOpponentOptions();
	}
	
	// Update opponent record
	private static void modifyOpponent() {
		
		List<Opponent> opponents = new ArrayList<Opponent>();
		
		try {
			pst = dbConn.prepareStatement("select * from Opponent");
			rs = pst.executeQuery();
			
			if (rs.next()) {
				do {
					
					Opponent o = new Opponent(
							rs.getInt("OpponentID"), 
							rs.getString("Name"), 
							rs.getString("Attacks"),
							rs.getInt("HitPoints"),
							rs.getInt("ArmorClass"),
							rs.getInt("Size"),
							rs.getInt("Speed"));
					
					opponents.add(o);
					print(rs.getInt("OpponentID") + ":" + rs.getString("Name"));
				} while (rs.next());
			}
			
			print("Select the opponent you want to modify by inputting the number next to its name");
			int input = getIntInput();
			
			boolean isValid = false;
			
			while (!isValid) {
				for (Opponent o : opponents) {
					if (o.getOpponentId() == input) {
						
						// Grabs the changes that the users wants and returns them
						o = changeOpponent(o);
						
						pst = dbConn.prepareStatement("update Opponent set Name =?, Attacks =?,"
								+ " HitPoints =?, ArmorClass =?, Size =?, Speed =? where OpponentID =?");
						
						pst.setString(1, o.getName());
						pst.setString(2, o.getOriginalAttackStringFormat());
						pst.setInt(3, o.getHealthPoints());
						pst.setInt(4, o.getArmorClass());
						pst.setInt(5, o.getSize());
						pst.setInt(6, o.getSpeed());
						pst.setInt(7, o.getOpponentId());
						pst.executeUpdate();
						
						showOpponentOptions();
					}
				}
				
				print("No such record found");
				showOpponentOptions();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static Opponent changeOpponent(Opponent o) {
		
		print("Do you want to change opponent name?");
		print("Enter 0 for no, 1 for yes");
		boolean isTrue = false;
		
		isTrue = getBoolInput();
		// Consume newline character
		scanner.nextLine();
		
		if (isTrue) {
			print("Enter the new name");
			o.setName(getStringInput());
			isTrue = false;
		}
		
		print("Do you want to change opponent attacks?");
		isTrue = getBoolInput();
		
		if(isTrue) {
			
			print("Enter number of attacks you want to add");
			int numAttacks = getIntInput();
			String allAttacks = "";
			
			scanner.nextLine();
			
			for (int i = 0; i < numAttacks;i++) {
				print("Enter attack name");
				String attackName = getStringInput();
				
				print("Enter number of damage dice (In integer form)");
				int numDamageDice = getIntInput();
				
				print("Enter dice type (d4 would be written as just 4)");
				int diceType = getIntInput();
				
				print("Enter attack bonus (+2 would be written as just 2, if no bonus, write 0)");
				int bonus = getIntInput();
				
				// Need to consume newline character before it reads the input
				scanner.nextLine();
				
				print("Enter any attack information you want to store");
				String attackInfo = getStringInput();
							
				String compiledAttack = attackName + "-" + numDamageDice + "-" 
				+ diceType + "-" + diceType + "-" + bonus + "-" + attackInfo;
				
				allAttacks += compiledAttack + "|";
			}
			
			o.initAttackList(allAttacks);
			isTrue = false;
		}
		
		print("Do you want to change Hitpoints?");
		isTrue = getBoolInput();
		
		if (isTrue) {
			print("Enter the new Hitpoints as an integer");
			o.setHealthPoints(getIntInput());
			isTrue = false;
		}
		
		print("Do you want to change ArmorClass?");
		isTrue = getBoolInput();
		
		if (isTrue) {
			print("Enter the new ArmorClass");
			o.setArmorClass(getIntInput());
			isTrue = false;
		}
		
		print("Do you want to change Size");
		isTrue = getBoolInput();
		
		if (isTrue) {
			print("Enter the new size as an integer");
			o.setSize(getIntInput());
			isTrue = false;
		}
		
		print("Do you want to change Speed?");
		isTrue = getBoolInput();
		
		if (isTrue) {
			print("Enter the new speed");
			o.setSpeed(getIntInput());
		}
		
		return o;
	}
	
	// TODO test
	private static void deleteOpponent() {
		
		List<Integer> opponents = new ArrayList<Integer>();
		
		try {
			pst = dbConn.prepareStatement("select * from Opponent");
			rs = pst.executeQuery();
			
			if (rs.next()) {
				do {
					
					opponents.add(rs.getInt("OpponentID"));
					
					print(rs.getInt("OpponentID") + ":" + rs.getString("Name"));
					
				} while (rs.next());
			}
			
			print("Select the opponent you want to delete by inputting the number next to its name");
			int input = getIntInput();
			
			boolean isValid = false;
			
			// TODO do I need these while loops?
			while (!isValid) {
				for (int i : opponents) {
					if (i == input) {
						
						// sql delete statement here
						pst = dbConn.prepareStatement("delete from Enc_Opp where OpponentID = ?");
						pst.setInt(1, i); 
						pst.executeUpdate();
						
						pst = dbConn.prepareStatement("delete from opponent where OpponentID = ?");
						pst.setInt(1, i); 
						pst.executeUpdate();
						
						showOpponentOptions();
					}
				}
				
				print("No such record found");
				showOpponentOptions();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void showEncounterOptions() {
		print("1: Create new encounter");
		print("2: Modify encounter");
		print("3: Delete encounter");
		print("4: Go back");
		
		boolean validSelection = false;
		
		while (!validSelection) {
			String userInput = getStringInput();
			
			switch (userInput) {
				case "1":
					createNewEncounter();
					break;
				case "2":
					modifyEncounter();
					break;
				case "3":
					deleteEncounter();
					break;
				case "4":
					showGeneralOptions();
					break;
				default:
					print("Input not recognized, please try again");
					break;
			}
		}
	}
	
	//TODO test
	private static void createNewEncounter() {
		print("Enter the encounter name");
		String encounterName = getStringInput();
		
		try {
			pst = dbConn.prepareStatement("insert into encounter (EncounterName) values (?)");
			pst.setString(1, encounterName);
			pst.executeUpdate();
			
			showEncounterOptions();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	// TODO test/ check what happens to this method and others when there are no records
	private static void modifyEncounter() {
		List<Integer> encounterIDs = new ArrayList<Integer>();
		
		try {
			
			pst = dbConn.prepareStatement("select * from Encounter");
			rs = pst.executeQuery();
			
			if (rs.next()) {
				do {
					encounterIDs.add(rs.getInt("EncounterID"));
					print(rs.getInt("EncounterID") + ":" + rs.getString("EncounterName"));
				} while (rs.next());
			}
			
			//scanner.nextLine();
			
			print("Input the number of the encounter you want to modify");
			int input = getIntInput();
			
			for (int i : encounterIDs) {
				if (i == input) {
					
					print("Enter the new encounter name");
					String name = scanner.nextLine();
					
					pst = dbConn.prepareStatement("update Encounter set EncounterName =? where EncounterID = ?");
					pst.setString(1, name);
					pst.setInt(2, i);
					pst.executeUpdate();
					
					showEncounterOptions();
				}
			}
			
			print("No record found");
			showEncounterOptions();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	// TODO test/ check what happens to this method and others when there are no records
	private static void deleteEncounter() {
		List<Integer> encounterIDs = new ArrayList<Integer>();
		
		try {
			
			pst = dbConn.prepareStatement("select * from encounter");
			rs = pst.executeQuery();
			
			if (rs.next()) {
				do {
					
					print(rs.getInt("EncounterID") + ":" + rs.getString("EncounterName"));
					encounterIDs.add(rs.getInt("EncounterID"));
					
				} while (rs.next());
			}
			
			print("Select the encounter you want to delete");
			int userInput = getIntInput();
			
			for (int i : encounterIDs) {
				if (i == userInput) {
					pst = dbConn.prepareStatement("delete from Enc_Opp where EncounterID = ?");
					pst.setInt(1, i);
					pst.executeUpdate();
					
					pst = dbConn.prepareStatement("delete from Encounter where EncounterID = ?");
					pst.setInt(1, i);
					pst.executeUpdate();
					
					showEncounterOptions();
				}
			}
			
			print("No record found");
			showEncounterOptions();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void showCampaignOptions() {
		print("1: Create new campaign");
		print("2: Modify campaign");
		print("3: Delete campaign");
		print("4: Go back");
		
		boolean validSelection = false;
		
		while (!validSelection) {
			String userInput = getStringInput();
			
			switch (userInput) {
				case "1":
					createNewCamp();
					break;
				case "2":
					modifyCamp();
					break;
				case "3":
					deleteCamp();
					break;
				case "4":
					showGeneralOptions();
					break;
				default:
					print("Input not recognized, please try again");
					break;
			}
		}
	}
	
	// TODO test/ check what happens to this method and others when there are no records
	private static void createNewCamp() {
		print("Enter campaign name");
		String campName = getStringInput();
		
		try {
			
			pst = dbConn.prepareStatement("insert into Campaign (DM_ID, CampName) values (?,?)");
			pst.setInt(1, dm.getDmId());
			pst.setString(2, campName);
			pst.executeUpdate();
			
			showCampaignOptions();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	// TODO test/ check what happens to this method and others when there are no records
	private static void modifyCamp() {
		List<Integer> campIDs = new ArrayList<Integer>();
		
		try {
			pst = dbConn.prepareStatement("select * from Campaign");
			rs = pst.executeQuery();
			
			if (rs.next()) {
				do {
					campIDs.add(rs.getInt("CampID"));
					print(rs.getInt("CampID") + ":" + rs.getString("CampName"));
				} while (rs.next());
			}
			
			print("Enter the number of the campaign you want to edit");
			int campID = getIntInput();
			
			for (int i : campIDs) {
				if (i == campID) {
					print("Enter the new campaign name");
					String campName = scanner.nextLine();
					
					pst = dbConn.prepareStatement("update campaign set CampName =? where CampID = ?");
					pst.setString(1, campName);
					pst.setInt(2, i);
					pst.executeUpdate();
					
					showCampaignOptions();
				}
			}
			
			print("No record found");
			showCampaignOptions();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// TODO test/ check what happens to this method and others when there are no records
	private static void deleteCamp() {
		List<Integer> campIDs = new ArrayList<Integer>();
		
		try {
			
			pst = dbConn.prepareStatement("select * from campaign");
			rs = pst.executeQuery();
			
			if (rs.next()) {
				do {
					campIDs.add(rs.getInt("CampID"));
					print(rs.getInt("CampID") + ":" + rs.getString("CampName"));
				} while (rs.next());
			}
			
			print("Enter the number of the campaign you want to delete");
			int campID = getIntInput();
			
			for (int i : campIDs) {
				if (i == campID) {
					
					pst = dbConn.prepareStatement("delete from Camp_Enc where CampaignID = ?");
					pst.setInt(1, i);
					pst.executeUpdate();
					
					pst = dbConn.prepareStatement("delete from campaign where CampID = ?");
					pst.setInt(1, i);
					pst.executeUpdate();
					
					
					showCampaignOptions();
				}
			}
			
			print("No record found");
			showCampaignOptions();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static String getStringInput() {
		
		String input = scanner.nextLine().trim();
		return input;
	}
	
	private static int getIntInput() {
		boolean isValid = false;
		int input = 0;
		
		while (!isValid) {
	
			try {
				input = scanner.nextInt();
				isValid = true;
			} catch (InputMismatchException e) {
				print("Input not valid, try again");
				isValid = false;
				scanner.nextLine();
			}
		}
	
		return input;
	}
	
	private static boolean getBoolInput() {
		
		boolean isValid = false;
		boolean input;
		
		while (!isValid) {
			
			try {
				input = scanner.nextBoolean();
				isValid = true;
			} catch (InputMismatchException e) {
				print("Input not valid, try again");
				isValid = false;
				scanner.nextLine();
			}
		}
		return false;
	}
	
	// So I don't have to do the long a** command every time
	private static void print(String input) {
		System.out.println(input);
	}
	
	private static void closeProgram() {
		System.exit(0);
	}
}
