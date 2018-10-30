import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static Scanner scanner = new Scanner(System.in);
	private static DM dm;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		print("Welcome, please load or create new DM profile");
		userSelectDm();
	}
	
	private static void userSelectDm() {
		boolean validProfile = false;
		
		while (!validProfile) {
			print("1: Create new DM profile");
			print("2: Load DM profile");
			print("3: Exit");
			
			String userInput = scanner.nextLine();
			
			switch (userInput) {
				case "1":
					if (createNewDM(userInput)) {
						loadDm(userInput);
						validProfile = true;
					} else {
						print("Failed to create new profile, try again");
					}
					break;
					
				case "2":
					outputDmNames(loadDmNames());
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
		// TODO
		// SQL to find DM data 
	}
	
	private static void showGeneralOptions() {
		print("1: Opponent");
		print("2: Encounter");
		print("3: Campaign");
		print("4: Start fight");
		print("4: Log out");
		
		boolean validSelection = false;
		
		while (!validSelection) {
			String userInput = scanner.nextLine();
			
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
		print("1: Select encounter");
		print("2: Select campaign");
		print("3: Go back");
	}
	
	private static void showOpponentOptions() {
		print("1: Create new opponent");
		print("2: Modify opponent");
		print("3: Delete opponent");
		print("4: Go back");
		
		boolean validSelection = false;
		
		while (!validSelection) {
			String userInput = scanner.nextLine();
			
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
		// Throw exception if create doesn't work
	}
	
	private static void modifyOpponent() {
		print("1: Search opponents");
		print("2: Show all opponents");
		
		
	}
	
	private static void deleteOpponent() {
		print("1: Search opponents");
		print("2: Show all opponents");
	}
	
	private static void showEncounterOptions() {
		print("1: Create new encounter");
		print("2: Modify encounter");
		print("3: Delete encounter");
		print("4: Go back");
		
		boolean validSelection = false;
		
		while (!validSelection) {
			String userInput = scanner.nextLine();
			
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
	
	private static void createNewEncounter() {
		// Throw exception if create doesn't work
	}
	
	private static void modifyEncounter() {
		
	}
	
	private static void deleteEncounter() {
		
	}
	
	private static void showCampaignOptions() {
		print("1: Create new campaign");
		print("2: Modify campaign");
		print("3: Delete campaign");
		print("4: Go back");
		
		boolean validSelection = false;
		
		while (!validSelection) {
			String userInput = scanner.nextLine();
			
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
	
	private static void createNewCamp() {
		// Throw exception if create doesn't work
	}
	
	private static void modifyCamp() {
		
	}
	
	private static void deleteCamp() {
		
	}
	
	private static void print(String input) {
		System.out.println(input);
	}
	
	
	private static void outputDmNames(ArrayList<String> names) {
		if (names.size() > 0) {
			for (String s : names) {
				print(s);
			}
		} else {
			// throw exception here
		}
	}
	
	private static ArrayList<String> loadDmNames() {
		ArrayList<String> dmNames = new ArrayList<String>();
		// Append a number to each entry
		
		return dmNames;
	}
	
	private static boolean createNewDM(String userInput) {
		// Throw exception if create doesn't work
		return true;
	}
	
	private static DM loadDm(String dmName) {
		
		return null;
	}
	
	private static void closeProgram() {
		System.exit(0);
	}
}
