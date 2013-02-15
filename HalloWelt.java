import java.util.*;

public class HalloWelt {
	public static void main (String[] args){
		System.out.println("Hallo Welt!");
		int [] cardsOnStack = prepareCardsOnStack();
		for (int i = 0; i < cardsOnStack.length; i++){
			System.out.println( cardsOnStack[i]);
		}
	}

	private static int[] prepareCardsOnStack(){
		int maxCardValue = 10;
		int minCardValue = 3;
		int[] omitted = {5,6,7};


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
}