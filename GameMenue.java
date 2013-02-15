import java.util.Scanner;
import java.lang.String;

public class GameMenue {

	public static int mode=0;

	public static void start() {
		Scanner myInput = new Scanner(System.in);
		System.out.println("gametypes:");
		System.out.println("at the moment only 0 and 1 work");
		System.out.println("0 = exit");
		System.out.println("1 = just computers");
		System.out.println("2 = humans");
		System.out.println();
		System.out.print("choose: ");
		
		do {
			String temp = myInput.next();
			String temp2 = "";
			for (int i = 0; i<temp.length(); i++){
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
			//if (mode==1){
				int[] omitted = new int[] {3,4,5,6};
				Game newGame = new Game(4, omitted);
			//}

			
			System.out.println("Game starts in mode "+mode);		
			newGame.run();
		} else {
			System.out.println("game is closing!");
			System.exit(0);
		}

	}

	public static void main(String[] args) {
		start();
	}

}