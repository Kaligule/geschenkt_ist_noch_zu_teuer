import java.util.*;
import java.util.Scanner;


public class Player {

	private Game game;
	private String name;
	private int strategie;
	private int coins;
	private int[] collectedCards;
	private Scanner myScanner;


	public Player(Game game, String name, int strategie, int coins) {
		this.game = game;
		this.name = name;
		this.strategie = strategie;
		if (strategie == 4){
			//humans need scanner
			myScanner = new Scanner(System.in);
		}
		this.coins = coins;
		this.collectedCards = new int[game.getMaxCardValue() + 1];
	}

	public String getName() {
		return name;
	}

	public int[] getCollectedCards(){
		return collectedCards;
	}

		public void pay(){
		coins--;
	}

	public void take(int newCard, int newCoins){
		collectedCards[newCard] = newCard;
		coins += newCoins;
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


	public boolean wouldYouPay(int cardInTheMiddle, int coinsInTheMiddle){
		//Abkürzung
		int card = cardInTheMiddle;

		if (coins == 0) {
			return false;
		} else if (strategie == 1) {
			// "Sonja" pays, until card/2 >= coinsInTheMiddle
			return (card >= coinsInTheMiddle*2);
		} else if (strategie == 2) {
			// "Greedy" pays, if card > coinsInTheMiddle
			return (card > coinsInTheMiddle);
		} else if (strategie == 3) {
			// "Dagobert" never pays
			return false;
		} else if (strategie == 4) {
			// "human" asks always for permission
			System.out.println("There are " + coinsInTheMiddle+ " Coins in the middle now!");
			System.out.println(name + ", do you want to take card and coins or pay 1 coin?");
			System.out.println("0 = take card and coins");
			System.out.println("1 = pay");
			int[] allowedActions = {0,1};
			int action = letUserChoose(allowedActions, myScanner);
			return (action == 1);
		} else if (false) {
			// "???" pays, if ...
			return true;
		} else {
			// "Coward" just pays always
			return true;
		}
	}



	private boolean iOwnTheCartd(int card){
		return (collectedCards[card] != 0);
	}
}