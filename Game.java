public class Game {

	private int reqPlayers;
	private int numPlayers = 0;
	private int startCoins = 11;
	private int minCardValue = 3;
	private int maxCardValue = 35;
	private int numOmitted;
	private int[] omitted;
	private Player[] players;

	public Game(int reqPlayers, int[] omitted) {
		this.reqPlayers = reqPlayers;
		this.players = new Player[reqPlayers];
		this.omitted = omitted;
		this.numOmitted = omitted.length;
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

	public boolean enoughPlayers(){
		return (numPlayers == reqPlayers);
	}

	public void run(){
		//Play the Game TODO
	}
}
