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
		int[] cardsOnStack = new int[ maxCardValue - minCardValue - omitted.length + 1 ];
		int zufuellen = 0;
		for(int i : hilfsliste){
			if (i != 0){
				cardsOnStack[zufuellen] = i;
				zufuellen++;
			}
		}
		return cardsOnStack;
	}

	public int getNumberOfPlayers() {
		return numPlayers;
	}


	public Player addPlayer(String name) {
		if (numPlayers < reqPlayers){
				Player newPlayer = new Player (this, name, startCoins);
				players [numPlayers] = newPlayer; 
				numPlayers += 1;
				return newPlayer;
		} else {
			return null;
		}
	}
	

	public Player getPlayer(int position) {
		return players[position % numPlayers];
	}


	/*public int getPosition(Player current) {
		// TODO
		return null;
	}*/

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
		System.out.println("There are " + numCardsOnStack + " Cards on the Stack.");
		cardInTheMiddle = cardsOnStack[numCardsOnStack];
		System.out.println("The card in the middle is the " + cardInTheMiddle);
	}

	public void run(){
		System.out.println("The Game beginns…");
		int numCardsOnStack = cardsOnStack.length;
		System.out.println("I was here!");

		int move = 0;
		coinsInTheMiddle = 0;

		while(numCardsOnStack != 0){
			Player currentPlayer = getPlayer(move);
			if (currentPlayer.wouldYouPay(cardInTheMiddle, coinsInTheMiddle)){
				//TODO
				currentPlayer.pay();
			} else {
				//TODO
				currentPlayer.take(cardInTheMiddle);
				numCardsOnStack -= 1;
				updateCardInTheMiddle(numCardsOnStack);
			}




			move += 1;
		}
	}
}
