public class Player {

	private Game game;
	private String name;
	private int coins;
	private int[] collectedCards = null;

	public Player(Game game, String name, int coins) {
		this.game = game;
		this.name = name;
		this.coins = coins;
	}


	public String getName() {
		return name;
	}

	public int[] getCollectedCards(){
		return collectedCards;
	}

	private boolean pay(){
		if (coins == 0) {
			return false;
		} else {
			// insert inteligence here
			return true;
		}
	}

}
