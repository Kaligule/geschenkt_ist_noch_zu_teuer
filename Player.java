
import java.util.*;
import java.util.Scanner;


public class Player {

	private Game game;
	private String name;
	private int strategie;
	private int coins;
	private int[] collectedCards;
	private Scanner myScanner;
	private static int possibleRanks = 4;
	private int[] rankCollector;

	//Consturctor and similar things
	public Player(Game game, String name, int strategie) {
		this.game = game;
		this.rankCollector = new int[possibleRanks];
		this.name = name;
		this.strategie = strategie;
		if (strategie == 0){
			//humans need scanner
			myScanner = new Scanner(System.in);
		}
		this.coins = game.getStartCoins();
		this.collectedCards = new int[game.getMaxCardValue() + 1];
	}

	public void resetPlayerForNewGame(Game game){
		//ranking is not touched
		this.game = game;
		this.coins = game.getStartCoins();
		this.collectedCards = new int[game.getMaxCardValue() + 1];
		System.out.println(name + " is ready for the new Game.");
	}

	public void youWereRanked(int rank){
		rankCollector[rank-1]++;
	}

	//For playing

	public void pay(){

		coins--;
	}

	public void take(int newCard, int newCoins){
		collectedCards[newCard] = newCard;
		coins += newCoins;
	}

	//For Userinterface

	private int[] takeAwayZeros(int[] array){
		int anzNichtNuller = 0;
		for(int i : array){
			if (i != 0){
				anzNichtNuller++;
			}
		}

		int [] newArray = new int[anzNichtNuller];

		int zufuellen = 0;
		for(int i : array){
			if (i != 0){
				newArray[zufuellen] = i;
				zufuellen++;
			}
		}
		return newArray;
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

	private void showYourCoins(){
		System.out.print("These are your " + coins + " coins:");
		for (int i = 1; i <= coins; i++ ) {
			System.out.print(" o");
		}
		System.out.println();
	}

	private void showYourCards(){
		System.out.print("The cards you have collected so far are:");
		String myCards = Arrays.toString(takeAwayZeros(collectedCards));
		System.out.println(myCards);
		System.out.println("That sums up to " + getCollectedPoints() + " points.");
	}

	public int[] getRankCollector(){
		return rankCollector;
	}

	public static int getPossibleRanks(){
		return possibleRanks;
	}

	public double getAverageRank(){
		double sumRanks = 0;
		double gamesPlayed = 0;

		for (int i = 0; i < rankCollector.length; i++) {
			sumRanks += (i+1)*(rankCollector[i]);
			gamesPlayed += rankCollector[i];
		}
		return (sumRanks/gamesPlayed);
	}

	//Here be intelligence
	public boolean wouldYouPay(int cardInTheMiddle, int coinsInTheMiddle){
		//Shortcut
		int card = cardInTheMiddle;

		while (true){
			if (coins == 0) {
				return false;

			} else if (strategie == 0) {
				// "human" always asks wether to pay or not
				System.out.println("There are " + coinsInTheMiddle+ " coins and the card " + card + " in the middle now!");
				System.out.println(name + ", do you want to take card and coins or pay 1 coin?");
				System.out.println("0 = take card and coins");
				System.out.println("1 = pay");
				System.out.println("2 = 'Er, how many coins do I have?'");
				System.out.println("3 = 'What cards did I take until now?'");
				System.out.println("4 = 'What do my opponents have?'");

				int[] allowedActions = {0,1,2,3,4};
				int action = letUserChoose(allowedActions, myScanner);
				if(action == 0){
					return false;
				} else if (action == 1) {
					return true;
				} else if (action == 2) {
					showYourCoins();
				} else if (action == 3) {
					showYourCards();
				} else if (action == 4) {

					Player[] playersByPosition = new Player[game.getNumPlayers()];
					for (int position = 0; position < playersByPosition.length; position++){
						playersByPosition[position] = game.getPlayer(game.getMove() + position);
					}
					game.table(playersByPosition, "Position");
				} 
			// "Dagobert" never pays
			} else if (strategie == 1) {
				return false;

			// "Coward" just pays always
			} else if (strategie == 2) {
				return true;
			// "Sonja" pays, until card/2 >= coinsInTheMiddle
			} else if (strategie == 3) {
				if(doYouNeedThatCard(card)){
					boolean othersNeedCard = false;
					for (int i = 1; i < game.getNumPlayers(); i++){
						Player afterMe = game.getPlayer(game.getMove() + 1);
						if (afterMe.doYouNeedThatCard(card)){
							othersNeedCard = true;
						}
					}
					if (othersNeedCard){
						return true;
					} else {
						return (card >= coinsInTheMiddle*3);
					}
				} else {
					return (card >= coinsInTheMiddle*2);
				}

			// "Businessman" pays, if card > coinsInTheMiddle
			} else if (strategie == 4) {
				return (card > coinsInTheMiddle);

			// "Greedy" takes the card, if that decreases his collected points
			} else if (strategie == 5) {
				if (card < coinsInTheMiddle) {
					return false;
				} else if (this.doYouOwnThatCard(card + 1)) {
					return false;
				} else {
					return true;
				}

			// "Stefan" pays sometimes...
			} else if (strategie == 6) {
				if(doYouOwnThatCard(card+1) || doYouOwnThatCard(card-1)){
					return(card/2 - 4 > coinsInTheMiddle);
				} else {
					if(game.getNumCardsOnStack() >= 10){
						if(card >= 26){
							return(card/2 - game.getNumPlayers() > coinsInTheMiddle);
						}
						else if(16 <= card && card < 26){
							return(card/2 +2 - game.getNumPlayers() > coinsInTheMiddle);
						} else {
							return(card/2 > coinsInTheMiddle);
						}
					} else {
						return(card > coinsInTheMiddle);
					}
				}

			} else if (false) {
				// "???" pays, if ...
				return false;
			}
		}
	}

	//Human programmers meight find these usefull
	// getters

	public String getName() {
		return name;
	}

	public int getCoins() {
		return coins;
	}

	public int getCollectedPoints(){
		int collectedPoints = 0;
		for (int i = collectedCards.length - 1; i >= 1; i--){
			if (collectedCards[i] != 0 &&  collectedCards[i-1] == 0) {
				collectedPoints += collectedCards[i];
			}
		}
		return collectedPoints;
	}

	public boolean doYouNeedThatCard(int card){
		return (doYouOwnThatCard(card+1) || doYouOwnThatCard(card-1));
	}

	public int[] getCollectedCards(){

		return collectedCards;
	}

	public boolean doYouOwnThatCard(int card){
		if (card >= collectedCards.length){
			return false;
		} else {
			return (collectedCards[card] != 0);
		}
	}
}