import java.util.*;
import java.util.Scanner;
import java.lang.String;

public class GameMenue {

	public static void main(String[] args) {
		int mode = 0;

		Scanner myScanner = new Scanner(System.in);
		System.out.println("gametypes:");
		System.out.println("at the moment only 0 and 1 work");
		System.out.println("0 = exit");
		System.out.println("1 = just computers");
		System.out.println("2 = humans");
		System.out.println();
		System.out.print("choose: ");
		
		do {
			String temp = myScanner.next();
			String temp2 = "";
			for (int i = 0; i < temp.length(); i++){
				if ((int) temp.charAt(i) >= 48 && (int) temp.charAt(i) <= 57){
					temp2 += temp.charAt(i);	
				}
			}
			
			if (temp2 == "")
				mode = -1;
			else 
				mode = Integer.parseInt(temp2);
			
			if (mode<0 || mode>2)
			{
				System.out.println("wrong input! try again...");
				System.out.println();
				System.out.print("chosse: ");
			}
		} while (mode<0 || mode>2);
		
		if (mode!=0) {	

			//Whot to do when mode is inserted
			int[] omitted = {};

			Game newGame = new Game(4, omitted);
			while (!newGame.enoughPlayers()){
				System.out.print("A new Player is created. What will his name be? ");
				String name = myScanner.next();
				newGame.addPlayer(name);
			}
			
			System.out.println("Game starts in mode "+mode);		
			newGame.run();
		} else {
			System.out.println("game is closing!");
			System.exit(0);
		}

	}

}