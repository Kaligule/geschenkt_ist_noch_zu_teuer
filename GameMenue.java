import java.util.*;
import java.util.Scanner;
import java.lang.String;

public class GameMenue {

	public static void main(String[] args) {
		int mode = 0;

		Scanner myScanner = new Scanner(System.in);
		System.out.println("Do you want to play \"Geschenkt ist noch zu teuer\"?");
		System.out.println("0 = exit");
		System.out.println("1 = play");
		System.out.println();
		int[] allowedModes = {0,1};
		mode = letUserChoose(allowedModes, myScanner);
		
		if (mode!=0) {	

			//What to do when mode is inserted
			int[] omitted = {};

			System.out.println("How many players will there be? (2-4)");
			System.out.println();
			int[] allowedNumbers = {2,3,4};
			int numPlayers = letUserChoose(allowedNumbers, myScanner);

			Game newGame = new Game(numPlayers, omitted);
			while (!newGame.enoughPlayers()){
				System.out.print("A new Player is created. What will his name be? ");
				String name = myScanner.next();
				System.out.println("What will his stratgie be like? ");
				System.out.println("0 = Human");
				System.out.println("1 = Dagobert");
				System.out.println("2 = Coward");
				System.out.println("3 = Sonja");
				System.out.println("4 = Greedy");
				System.out.println("");
				System.out.println("(112 = tell me about the strategies)");
				int[] allowedStrategies = {112,0,1,2,3,4};
				int stratgie = letUserChoose(allowedStrategies, myScanner);
				while (stratgie == 112){
					System.out.println("\"Dagobert\" never pays");
					System.out.println("\"Coward\" just pays always");
					System.out.println("\"Sonja\" pays, until card/2 >= coinsInTheMiddle");
					System.out.println("\"Greedy\" pays, if card > coinsInTheMiddle");
					stratgie = letUserChoose(allowedStrategies, myScanner);
				}
				newGame.addPlayer(name, stratgie);
			}
			
			System.out.println("Game starts in mode "+mode);		
			newGame.run();
		} else {
			System.out.println("game is closing!");
			System.exit(0);
		}

	}

	private static int justAnInt(String string){
		int filtered;
		String temp2 = "";
		for (int i = 0; i < string.length(); i++){
			if ((int) string.charAt(i) >= 48 && (int) string.charAt(i) <= 57){
				temp2 += string.charAt(i);	
			}
		}
		if (temp2 == ""){
			filtered = -1;
		} else {
			filtered = Integer.parseInt(temp2);
		}
		return filtered;
	}

	private static int letUserChoose(int[] allowed, Scanner myScanner){
		System.out.print("choose: ");
		boolean correct;
		int chosen;
		do {
			String temp = myScanner.next();
			chosen = justAnInt(temp);

			correct = false;
			for (int i : allowed) {
				if (chosen == i){
					correct = true;	
				}
			}
			
			if (!correct){
				System.out.println("wrong input! try again...");
				System.out.println();
				System.out.print("chosse: ");
			}
		} while (!correct);
		return chosen;
	}

}