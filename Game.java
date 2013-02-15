public class Game {

	private int maxPlayers;
	private int numOmitted;
	private int[] omitted;

	/**
	 * Constructor for a Game.
	 * @param maxPlayers gives the maximal number of players, which may participate.
	 * @param numOmitted is a list of the values of the cards that are omitted
	 */
	public Game(int maxPlayers, int[] omitted) {
		this.maxPlayers = maxPlayers;
		this.omitted = omitted;
		this.numOmitted = omitted.length;
	}

	/**
	 * Gets the number of player participating the game.
	 * @return number of players.
	 */
	public int getNumberOfPlayers() {
		/* TODO a) */
		return 0;
	}

	/**
	 * Creates and adds a Player to the Game.
	 * @param name of the Player.
	 * @param maxDiv maximal number of divisors for this Player.
	 * @return the created Player, if it is possible to add one without violating the maximum, otherwise null.
	 */
	public Player addPlayer(String name, int maxDiv) {
		/* TODO a) */
		return null;
	}
	
	/**
	 * Get a Player.
	 * @param position the index of the Player to return.
	 * First added Player is at position 0, 2nd at 1 and so on.
	 * @return the Player at this position.
	 */
	public Player getPlayer(int position) {
		/* TODO a) */
		return null;
	}

	/**
	 * Returns the Player who has to move next.
	 * @param current the Player current Player.
	 * @return the Player who has to move after current wrapped to 0 after last Player).
	 * Returns null, if the current Player is not part of this game.
	 */
	public Player getNextPlayer(Player current) {
		/* TODO b) */
		return null;
	}
}
