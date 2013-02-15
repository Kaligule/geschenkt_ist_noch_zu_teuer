import java.util.*;

public class Player {

	private Game game;
	private String name;
	private int coins;
	private int[] collectedCards;

	public Player(Game game, String name, int coins) {
		this.game = game;
		this.name = name;
		this.coins = coins;
		this.collectedCards = new int[game.getMaxCardValue() + 1];
	}


	public String getName() {
		return name;
	}

	public int[] getCollectedCards(){
		return collectedCards;
	}

	public boolean wouldYouPay(int cardInTheMiddle, int coinsInTheMiddle){
		if (coins == 0) {
			return false;
		} else {
			// insert inteligence here
			return true;
		}
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

}
