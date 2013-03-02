import java.util.*;
import java.util.Scanner;
import java.lang.String;

public class GameMenue {

	private static void startGameMenue(int presetMode){
		int mode = 0;
		Scanner myScanner = new Scanner(System.in);
		if (presetMode == 0){
			System.out.println("How do you want to play \"Geschenkt ist noch zu teuer\"?");
			System.out.println("0 = exit");
			System.out.println("1 = play");
			System.out.println("2 = standart Game for debugging");
			System.out.println();
			int[] allowedModes = {0,1,2};
			mode = letUserChoose(allowedModes, myScanner);
		} else {
			mode = presetMode;
		}

		//Prepare the first game now
		int gamesAlreadyPlayed = 0;
		Player[] players;
		Game firstGame = null;
		int omit = 9;
		int numPlayers = 0;

		// Get a first Game and its Players but don't run the Game yet
		if (mode == 1) {	
			System.out.println("How many players will there be? (2-4)");
			System.out.println();
			int[] allowedNumbers = {2,3,4};
			numPlayers = letUserChoose(allowedNumbers, myScanner);
			firstGame = new Game(numPlayers, omit);

			while (!firstGame.enoughPlayers()){
				System.out.print("A new Player is created. What will his name be? ");
				String name = myScanner.next();
				System.out.println("What will " + name + "\'s stratgie be like? ");
				System.out.println("0 = Human");
				System.out.println("1 = Dagobert");
				System.out.println("2 = Coward");
				System.out.println("3 = Sonja");
				System.out.println("4 = Businessman");
				System.out.println("5 = Greedy");
				System.out.println("6 = Stefan");
				System.out.println("(112 = tell me about the strategies)");
				int[] allowedStrategies = {112,0,1,2,3,4,5,6};
				int stratgie = letUserChoose(allowedStrategies, myScanner);
				while (stratgie == 112){
					System.out.println("\"Human\" is you playing yourself");
					System.out.println("\"Dagobert\" never pays");
					System.out.println("\"Coward\" just pays always");
					System.out.println("\"Sonja\" pays, until card/2 <= coinsInTheMiddle");
					System.out.println("\"Businessman\" pays, if card > coinsInTheMiddle");
					System.out.println("\"Greedy\" takes the card, if that decreases his collected points");
					System.out.println("\"Stefan\" tries to get a deflation at the start");
					stratgie = letUserChoose(allowedStrategies, myScanner);
				}
				firstGame.addPlayer(name, stratgie);
			}
		} else if (mode == 2) {
			numPlayers = 4;
			firstGame = new Game(numPlayers, omit);
			firstGame.addPlayer(new Player(firstGame, "Jøhannes", 5));
			firstGame.addPlayer(new Player(firstGame, "Sandra", 4));
			firstGame.addPlayer(new Player(firstGame, "Sonja", 3));
			firstGame.addPlayer(new Player(firstGame, "Stefan", 6));
		} else {
			// mode == 0 is easy
			System.out.println("game is closing!");
			System.exit(0);
		}

		int gamesPlayed = 0;
		int gamesToPlay = 1;

		Game game = firstGame;

		while (gamesPlayed < gamesToPlay) {
			System.out.println("Game starts in mode " + mode + ".");
			System.out.println();
			players = game.run();
			gamesPlayed++;

			if (gamesPlayed == gamesToPlay) {
				System.out.println("We have played enough, didn't we?");
				System.out.println("0 = Thats enough!");
				System.out.println("1 = Play again.");
				System.out.println("x = Play x further Games.");
				System.out.println();
				int[] allowedRevanches = {0,1,2,4,5,6,7,8,9,10};
				gamesToPlay += letUserChoose(allowedRevanches, myScanner);
			}

			game = new Game(numPlayers, omit);
			if (gamesPlayed < gamesToPlay) {
				for (Player player : players ) {
					game.addPlayer(player);
				}
			}
		}

		System.out.println("We would love to show you a final ranking here.");
		System.out.println("Unfortunatelly it has not been programmed yet.");



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

	public static void main(String[] args) {
		// The presetMode becomes the mode of the Game.
		// If presetMode is 0, the Player can choose.
		int presetMode = 2;
		startGameMenue(presetMode);
	}	

}