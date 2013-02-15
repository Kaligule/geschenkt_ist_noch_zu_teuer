public class Game {

	private int reqPlayers;
	private int numPlayers = 0;
	private int startcoins = 0;
	private int minCardValue = 3;
	private int maxCardValue = 35;
	private int numOmitted;
	private int[] omitted;
	private Player[] players = new Player[reqPlayers];

	public Game(int reqPlayers, int[] omitted) {
		this.reqPlayers = reqPlayers;
		this.omitted = omitted;
		this.numOmitted = omitted.length;
	}


	public int getNumberOfPlayers() {
		return numPlayers;
	}


	public Player addPlayer(String name, int coins) {
		if (numPlayers < reqPlayers){
				Player newPlayer = new Player (this, name, startcoins);
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


	public int getPosition(Player current) {
		// TODO
		return null;
	}
}
