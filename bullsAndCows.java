import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

public class bullsAndCows {

	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		System.out.println("Input the length of the secret code: ");
		String lgt = sc.nextLine();
		if (!isNumeric(lgt)) {
			System.out.println("Error: \""+ lgt +"\" isn't a valid number.");
			System.exit(0);
		}
		
		if (Integer.parseInt(lgt) <= 0) {
			System.out.println("Error: \""+ lgt +"\" isn't a valid number.");
			System.exit(0);
		}
		
		System.out.println("Input the number of possible symbols in the code:");
		String lgtChar = sc.nextLine();	
		
		if (!isNumeric(lgtChar)) {
			System.out.println("Error: \" "+ lgt +"\" isn't a valid number.");
			System.exit(0);
		}
		
		if (Integer.parseInt(lgtChar) > 36) {
			System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
			System.exit(0);
		}
		
		if (Integer.parseInt(lgtChar) < Integer.parseInt(lgt)) {
			System.out.println("Error: it's not possible to generate a code with a length of " + lgtChar +" with "+ lgt+ " unique symbols.");
			System.exit(0);
		}
		
		msgSecret(Integer.parseInt(lgt), Integer.parseInt(lgtChar));
		System.out.println("Okay, let's start a game!");
		
		int bull = 0;
		int cow = 0;
		String code = codeGenerator(Integer.parseInt(lgt), Integer.parseInt(lgtChar));
		String[] arrCode = code.split("");
		int counter = 1;
		String answer = "";

		while(code != answer) {
			System.out.println("Turn " + counter + ":");
			answer = sc.next();
			String[] arrAnswer = answer.split("");
			
			for (int i = 0; i < arrCode.length; i++) {
				for (int j = 0; j < arrCode.length; j++) {
					if (arrAnswer[j].contains(arrCode[i]) && j == i) {
						bull++;
					}
					if (arrAnswer[j].contains(arrCode[i]) && j != i) {
						cow++;
					}
				}
			}
			if (allEquals(arrCode) && bull != 0) {
				System.out.println("Grade: " + bull + " bulls.");
			} else {
				
				if (cow != 0 && bull != 0) {
					if (cow > 1 && bull > 1) {
						System.out.println("Grade: " + bull + " bulls and " + cow + " cows.");
					} else {
						System.out.println("Grade: " + bull + " bull and " + cow + " cow.");
					}
				} else if (cow == 0 && bull > 0) {
					if (bull > 1) {
						System.out.println("Grade: " + bull + " bulls. ");
							if (bull == arrCode.length) {
								System.out.println("Congratulations! You guessed the secret code.");
								System.exit(0);
							}
					} else {
						System.out.println("Grade: " + bull + " bull.");
						if (bull == arrCode.length) {
							System.out.println("Congratulations! You guessed the secret code.");
							System.exit(0);
						}
					}
				} else if (cow > 0 && bull == 0) {
					if (cow > 1) {
						System.out.println("Grade: " + cow + " cows. ");
					} else {
						System.out.println("Grade: " + cow + " cow.");
					}
				} else {
					System.out.println("Grade: " + bull + " bull and " + cow + " cow.");
				}
				bull = 0;
				cow = 0;
				counter++;
			}
			}
		

	}
	
	public static String codeGenerator(int numNB, int numLT) {
		String[] code = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String[] lets = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "x", "w", "y", "z"};
		String[] withAll = new String[numLT];
		for (int i = 0; i < 10; i++) {
			withAll[i] = code[i];
		}
		int aux = 10;
		for (int i = 0; i < (numLT - 10); i++) {
			withAll[aux] = lets[i];
			aux++;
		}
		Collections.shuffle(Arrays.asList(withAll));
		String[] toReturn = new String[numNB];
		Random rd = new Random();
		for (int i = 0; i < toReturn.length; i++) {
			int random = rd.nextInt(numLT);
			toReturn[i] = withAll[random];
			while (withAll[random].contains("X")) {
				random = rd.nextInt(numLT);
				toReturn[i] = withAll[random];
			} 
			withAll[random] = "X";
		}
		if (toReturn[0].contains("0")) {
			String end = toReturn[toReturn.length - 1];
			toReturn[0] = end;
			toReturn[toReturn.length - 1] = "0";
		}
		String end = "";
		for (int i = 0; i < toReturn.length; i++) {
			end += toReturn[i];
		}
		return end;
	}
	
	public static boolean allEquals(String[] st) {
		boolean isAllEqual = false;
		for (int i = 1; i < st.length; i++) {
			if (st[0].contains(st[1])) {
				return true;
			}
		}
		return false;
	}
	
	public static void msgSecret(int b, int i) {
		String[] lets = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "x", "w", "y", "z"};
		int auxParentheses = (10 - i);
		String asterisk = "";
		for (int j = 0; j < b; j++) {
			asterisk += "*";
		}
		if (auxParentheses < 0) {
			System.out.println("The secret is prepared: "+ asterisk + " (0-9, a-" + lets[Math.abs(auxParentheses)-1] + ").");
		} else {
		System.out.println("The secret is prepared: "+ asterisk + " (0-9).");
		}
	}
	
	public static boolean isNumeric(String i) {
		try {
			Integer.parseInt(i);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
