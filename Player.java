import java.util.*;
import java.util.Scanner;


public class Player {

	private Game game;
	private String name;
	private int strategie;
	private int coins;
	private int[] collectedCards;
	private Scanner myScanner;

	//Consturctor
	public Player(Game game, String name, int strategie, int coins) {
		this.game = game;
		this.name = name;
		this.strategie = strategie;
		if (strategie == 0){
			//humans need scanner
			myScanner = new Scanner(System.in);
		}
		this.coins = coins;
		this.collectedCards = new int[game.getMaxCardValue() + 1];
	}

	public String getName() {

		return name;
	}

	public int getCoins() {
		//This is allowed only for the ranking.
		return coins;
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

				int[] allowedActions = {0,1,2,3};
				int action = letUserChoose(allowedActions, myScanner);
				if(action == 0){
					return false;
				} else if (action == 1) {
					return true;
				} else if (action == 2) {
					showYourCoins();
				} else if (action == 3) {
					showYourCards();
				}
			} else if (strategie == 1) {
				// "Dagobert" never pays
				return false;
			} else if (strategie == 2) {
				// "Coward" just pays always
				return true;
			} else if (strategie == 3) {
				// "Sonja" pays, until card/2 >= coinsInTheMiddle
				return (card >= coinsInTheMiddle*2);
			} else if (strategie == 4) {
				// "Greedy" pays, if card > coinsInTheMiddle
				return (card > coinsInTheMiddle);
			} else if (false) {
				// "???" pays, if ...
				return false;
			}
		}
	}

	//Human programmers meight find these usefull

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
		System.out.println("That sums up to " + getCollectedPoints() + " Points.");
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

	public int[] getCollectedCards(){

		return collectedCards;
	}

	private boolean doYouOwnThatCard(int card){
		
		return (collectedCards[card] != 0);
	}
}