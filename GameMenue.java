import java.util.*;
import java.util.Scanner;
import java.lang.String;

public class GameMenue {

	// The presetMode becomes the mode of the Game.
	// If presetMode is 0, the user can choose.
	private static int presetMode = 0;


	private static void startGameMenue(int presetMode){
		// Set mode now
		int mode = 0;
		Scanner myScanner = new Scanner(System.in);
		if (presetMode == 0){
			System.out.println("How do you want to play \"Geschenkt ist noch zu teuer\"?");
			System.out.println("0 = exit");
			System.out.println("1 = play");
			System.out.println("2 = standart game for debugging");
			System.out.println("3 = many standart games");
			System.out.println();
			int[] allowedModes = {0,1,2,3};
			mode = letUserChoose(allowedModes, myScanner);
		} else {
			mode = presetMode;
		}

		// Prepare the first game now
		int gamesAlreadyPlayed = 0;
		Player[] players;
		Game firstGame = null;
		int omit = 9;
		int numPlayers = 4;
		int gamesPlayed = 0;
		int gamesToPlay = 1;

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
			firstGame = standartGame(numPlayers, omit);
		} else if (mode == 3) {
			firstGame = standartGame(numPlayers, omit);

			//how many Games
			System.out.println();
			System.out.println("How many standart games do you want to be played? (1-100)");
			gamesToPlay = letUserChoose(1, 100, myScanner);
		} else {
			// mode == 0 is easy
			System.out.println("The Game is closing!");
			System.exit(0);
		}


		Game game = firstGame;

		do {
			System.out.println("Game starts in mode " + mode + ".");
			System.out.println();
			players = game.run();
			gamesPlayed++;

			if (gamesPlayed == gamesToPlay) {
				System.out.println("We have played " + gamesPlayed + " games now.");
				System.out.println("That's enough, isn't it?");
				System.out.println();
				System.out.println("0 = Thats enough!");
				System.out.println("1 = Play again.");
				System.out.println("x = Play x further games. (1-100)");
				System.out.println();
				gamesToPlay += letUserChoose(0, 100, myScanner);
			}

			game = new Game(numPlayers, omit);
			if (gamesPlayed < gamesToPlay) {
				for (Player player : players ) {
					game.addPlayer(player);
				}
			}
		} while (gamesPlayed < gamesToPlay);

		System.out.println();
		System.out.println("Yea, I think so.");
		System.out.println("This is your final ranking table:");
		System.out.println();
		medalTable(players);

	}


	private static void medalTable (Player[] players){
		players = sortByAverageRank(players);

		int tabLength = 8;
		int spaceBetwRanks = 3;

		//longest name
		int nameLength = 0;
		for (Player player : players) {
			if (nameLength < player.getName().length()){
				nameLength = player.getName().length();
			}
		}

		//Legend
		System.out.print("Name");
		for (int i = 1; i<= nameLength - 4 + 1; i++ ) {
			System.out.print(" ");
		}
		System.out.print("Ø     ");
		for (int i = 1; i <= Player.getPossibleRanks(); i++){
			for (int j = 0; j < spaceBetwRanks ; j++ ) {System.out.print(" ");}
			System.out.print(i);
		}
		System.out.println();

		//table
		for (int i = 0; i < players.length; i++){
			Player player = players[i];
			String name = player.getName();
			name = makeStringThislong(name, nameLength + 1);
			System.out.print(name);

			String avRank = String.valueOf(player.getAverageRank());
			avRank = makeStringThislong(avRank, 4);
			System.out.print(avRank + "  ");

			int number;
			String strNumber;
			for (int j = 0; j < Player.getPossibleRanks(); j++ ) {
				number = player.getRankCollector()[j];
				strNumber = String.valueOf(number);
				while (strNumber.length() < spaceBetwRanks + 1){
					strNumber = (" " + strNumber);
				}
				System.out.print(strNumber);

			}
			System.out.println();
		}
	}

	private static Game standartGame(int numPlayers, int omit){
		Game game = new Game(numPlayers, omit);
		game.addPlayer(new Player(game, "Jøhannes", 5));
		game.addPlayer(new Player(game, "Sandra", 4));
		game.addPlayer(new Player(game, "Sonja", 3));
		game.addPlayer(new Player(game, "Stefan", 6));
		return game;
	}

	private static Player[] sortByAverageRank(Player[] liste){
		int anzahl = liste.length;

		boolean sortet = false;
		Player tmp;
		while (sortet == false) {
			sortet = true;
			for (int i = 0; i < anzahl - 1 ; i++) {
				if (liste[i].getAverageRank() > liste[i+1].getAverageRank()){
					sortet = false;
					tmp = liste[i]; liste[i] = liste[i+1]; liste[i+1] = tmp;
				}
			}
		}
		return liste;
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

	private static String makeStringThislong(String string, int length){
		while (string.length() < length){
			string = string + " ";
		}
		string = string.substring(0,length);
		return string;
	}

	private static int letUserChoose(int minAllowed, int maxAllowed, Scanner myScanner){
		int[] allowed = new int[maxAllowed - minAllowed + 1];
		for (int i = 0; i < allowed.length; i++ ) {
			allowed[i] = minAllowed + i;
		}
		return letUserChoose(allowed, myScanner);
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
		startGameMenue(presetMode);
	}


}