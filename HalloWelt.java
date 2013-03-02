import java.util.*;

public class HalloWelt {
	public static void main (String[] args){
			System.out.println("Hallo Welt!");
			int [] testarray = new int[4];
			showArray (testarray);
	}

	private static void showArray(int[] array){
		System.out.print("Your array is at the moment: ");
		System.out.println(Arrays.toString(array));
	}
}