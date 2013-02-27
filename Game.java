import java.util.*;

public class Game {

	private int reqPlayers;
	private int numPlayers = 0;
	private int startCoins = 11;
	private int minCardValue = 3;
	private int maxCardValue = 35;
	private int omit;
	private Player[] players;
	private int[] cardsOnStack;
	private int cardInTheMiddle;
	private int coinsInTheMiddle;
	private int numCardsOnStack;

	public Game(int reqPlayers, int omit) {
		this.reqPlayers = reqPlayers;
		this.players = new Player[reqPlayers];
		this.omit = omit;
		cardsOnStack = prepareCardsOnStack();
		numCardsOnStack = cardsOnStack.length;

	}

	private int[] prepareCardsOnStack(){
		int[] hilfsliste = new int[maxCardValue + 1];
		for (int i = minCardValue; i <= maxCardValue; i++){
			hilfsliste[i] = i;
		}
		int[] cardsOnStack = takeAwayZeros(hilfsliste);
		cardsOnStack = shuffelCards(cardsOnStack);
		cardsOnStack = omitLastCards(cardsOnStack, omit);
		return cardsOnStack;
	}

	private int[] shuffelCards(int[] cards) { 
		int tmp; 
		int rand; 
		Random generator = new Random(); 
		for (int i = 0; i < cards.length; i++) { 
			rand = generator.nextInt(cards.length); 
			tmp = cards[i]; 
			cards[i] = cards[rand]; 
			cards[rand] = tmp; 
		} 
		return cards; 
	}

	private int[] omitLastCards(int[] oldStack, int number){
		int[] newStack = new int[oldStack.length - number];
		for (int i = 0; i < newStack.length ; i++) {
			newStack[i] = oldStack[i] ;
		}
		return newStack;
	}

	public int getNumberOfPlayers() {

		return numPlayers;
	}

	public Player addPlayer(String name, int strategie) {
		if (numPlayers < reqPlayers ){
			Player newPlayer = new Player (this, name, strategie, startCoins);
			players [numPlayers] = newPlayer; 
			numPlayers += 1;
			return newPlayer;
		} else {
			return null;
		}
	}

	public boolean enoughPlayers(){

		return (numPlayers == reqPlayers);
	}

	public int getNumCardsOnStack(){
		return numCardsOnStack;
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

	private void updateCardInTheMiddle(int numCardsOnStack){
		if (numCardsOnStack == 0){
			System.out.println("This was the last card.");
		} else {
			cardInTheMiddle = cardsOnStack[numCardsOnStack - 1];
			System.out.println("The card in the middle is the " + cardInTheMiddle);
			System.out.println("There are " + (numCardsOnStack -1)+ " other cards on the Stack.");

		}
	}

	public void run(){
		System.out.println("The Game beginns…");
		updateCardInTheMiddle(numCardsOnStack);
		int move = 0;
		coinsInTheMiddle = 0;

		while(numCardsOnStack != 0){
			Player currentPlayer = getPlayer(move);
			if (currentPlayer.wouldYouPay(cardInTheMiddle, coinsInTheMiddle)){
				currentPlayer.pay();
				coinsInTheMiddle++;
				System.out.println(currentPlayer.getName() + " pays 1 coin!");
			} else {
				System.out.println(currentPlayer.getName() + " takes the Card and the " + coinsInTheMiddle + " Coins from the middle.");
				currentPlayer.take(cardInTheMiddle, coinsInTheMiddle);
				numCardsOnStack -= 1;
				coinsInTheMiddle = 0;
				move--;
				System.out.println();
				updateCardInTheMiddle(numCardsOnStack);
			}
			move++;
		}
		System.out.println();
		gameOver();
	}

	private void gameOver(){
		System.out.println("/////////////////////////   Game Over   /////////////////////////");
		System.out.println();

		
		//Ranking

		Player[] ranking = sortByPoints(players);

		//longest name
		int nameLength = 0;
		for (Player player : ranking) {
			if (nameLength < player.getName().length()){
				nameLength = player.getName().length();
			}
		}

		//Legend
		System.out.print("Rank\tName");
		for (int i = 1; i<= nameLength - 4 + 1; i++ ) {
			System.out.print(" ");
		}
		System.out.println("\tPoints\tCoins\tCards");

		//table
		for (int i = 0; i < ranking.length; i++){
			Player player = ranking[i];
			String name = player.getName();
			while (name.length() < nameLength + 1){
				name = (name + " ");
			}
			int coins = player.getCoins();
			int points = player.getCollectedPoints() - coins;
			String cards = Arrays.toString(takeAwayZeros(player.getCollectedCards()));
			System.out.println((i+1) + ".\t" + name + "\t" + points + "\t" + coins + "\t" + cards);
		}
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

	public  Player[] sortByPoints(Player[] liste){
		int anzahl = liste.length;

		boolean sortet = false;
		Player tmp;
		while (sortet == false) {
			sortet = true;
			for (int i = 0; i < anzahl - 1 ; i++) {
				if (liste[i].getCollectedPoints() > liste[i+1].getCollectedPoints()){
					sortet = false;
					tmp = liste[i]; liste[i] = liste[i+1]; liste[i+1] = tmp;
				}
			}
		}
		return liste;
	}
}
