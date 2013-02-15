public class Player {

	private Game game;
	private String name;
	private int coins;
	private int[] collectedCards = null;

	/**
	 * Constructs a Player.
	 * @param game the Game, which this Player participates.
	 * @param name the name of the Player.
	 * @param coins the Number of coins the Player has.
	 */
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

	

}
