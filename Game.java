import java.util.*;

public class Game {

	private int reqPlayers;
	private int numPlayers = 0;
	private int startCoins = 11;
	private int minCardValue = 3;
	private int maxCardValue = 35;
	private int[] omitted;
	private Player[] players;
	private int[] cardsOnStack;
	private int cardInTheMiddle;
	private int coinsInTheMiddle;

	public Game(int reqPlayers, int[] omitted) {
		this.reqPlayers = reqPlayers;
		this.players = new Player[reqPlayers];
		this.omitted = omitted;
		cardsOnStack = prepareCardsOnStack();
	}

	private int[] prepareCardsOnStack(){
		int[] hilfsliste = new int[maxCardValue + 1];
		for (int i = minCardValue; i <= maxCardValue; i++){
			hilfsliste[i] = i;
		}
		for (int i : omitted) {
			hilfsliste[i] = 0;
		}
		int[] cardsOnStack = takeAwayZeros(hilfsliste);
		//Doesn't work TODO
		//Collections.shuffel(Arrays.asList(cardsOnStack));
		return cardsOnStack;
	}

	public int getNumberOfPlayers() {
		return numPlayers;
	}


	public Player addPlayer(String name, int strategie) {
		if (numPlayers < reqPlayers ){
				Player newPlayer = new Player (this, name, strategie, startCoins);
				players [numPlayers] = newPlayer; 
				numPlayers += 1;
				System.out.println("We have created " + name + ".");
				return newPlayer;
		} else {
			return null;
		}
	}

	public Player getPlayer(int position) {
		return players[position % numPlayers];
	}

	public int getMinCardValue(){
		return minCardValue;
	}

	public int getMaxCardValue(){
		return maxCardValue;
	}

	public boolean enoughPlayers(){
		return (numPlayers == reqPlayers);
	}

	private void updateCardInTheMiddle(int numCardsOnStack){
		if (numCardsOnStack == 0){
			System.out.println("This was the last card.");
		} else {
			cardInTheMiddle = cardsOnStack[numCardsOnStack - 1];
			System.out.println("There are " + numCardsOnStack + " Cards on the Stack.");
		System.out.println("The card in the middle is the " + cardInTheMiddle);
		}
	}

	public void run(){
		System.out.println("The Game beginns…");
		int numCardsOnStack = cardsOnStack.length;
		updateCardInTheMiddle(numCardsOnStack);
		int move = 0;
		coinsInTheMiddle = 0;

		while(numCardsOnStack != 0){
			Player currentPlayer = getPlayer(move);
			if (currentPlayer.wouldYouPay(cardInTheMiddle, coinsInTheMiddle)){

				currentPlayer.pay();
				coinsInTheMiddle++;
				System.out.println(currentPlayer.getName() + " pays 1 coin!");
				System.out.println("There are " + coinsInTheMiddle+ " Coins in the middle now!");

			} else {
				System.out.println(currentPlayer.getName() + " takes the Card and the " + coinsInTheMiddle + " Coins from the middle.");
				currentPlayer.take(cardInTheMiddle, coinsInTheMiddle);
				numCardsOnStack -= 1;
				coinsInTheMiddle = 0;
				updateCardInTheMiddle(numCardsOnStack);
			}
			move += 1;
		}
		System.out.println();
		System.out.println("/////////////////////////   Game Over   /////////////////////////");
		System.out.println();
		System.out.println("Name\tPoints\tCards");

		for (int i = 0; i < numPlayers; i++){
			Player player = getPlayer(i);
			String name = player.getName();
			int points = player.getCollectedPoints();
			String cards = Arrays.toString(takeAwayZeros(player.getCollectedCards()));
			System.out.println(name + "\t" + points + "\t" + cards);
		}

	}

	private void showCards(int[] cards){
		System.out.println(Arrays.toString(takeAwayZeros(cards)));
	}

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

}
