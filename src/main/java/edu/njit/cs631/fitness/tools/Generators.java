package edu.njit.cs631.fitness.tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Generators {

	private static Random random;
	private static final int TOTAL = 132;
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public Generators() {
		super();
	}

	public static void main(String[] args) {
		givenList_whenNumberElementsChosen_shouldReturnRandomElementsRepeat();
		
		//random = new Random();
		// ssn();
		// middleInitals();
        /*String fromPrevious = "$2a$10$yJSLXVodHQtynXWesCHI/.mzaT.07wJbfPsYfPv/Z11qWA2fIqgoG";
        String hash = encodeTestPassword("password");
        System.out.println(hash);
        System.out.println(decodeTestPassword("password", hash));
        System.out.println(decodeTestPassword("password", fromPrevious));*/

	}
	
	public static void givenList_whenNumberElementsChosen_shouldReturnRandomElementsRepeat() {
	    Random rand = new Random();
	    List<Integer> givenList = Arrays.asList(1, 221, 223, 225, 226, 227, 228, 229, 222, 230, 231, 232, 233, 234);
	 
	    int numberOfElements = 52;
	 
	    for (int i = 0; i < numberOfElements; i++) {
	        int randomIndex = rand.nextInt(givenList.size());
	        Integer randomElement = givenList.get(randomIndex);
	        System.out.println(randomElement);
	    }
	}

	private static String encodeTestPassword(String pwd) {
	    return encoder.encode(pwd);
    }

    private static boolean decodeTestPassword(String pwd, String hash) {
	    return encoder.matches(pwd, hash);
    }

    @SuppressWarnings("unused")
	private static void middleInitals() {
		for (int i = 0; i < TOTAL; i++) {
			if (random.nextInt(100) < 33) {
				// only 1/3 of people have/use middle initials
				System.out.println(generateInital());
			} else {
				System.out.println();
			}
		}
	}

	private static String generateInital() {
		char b = (char) (random.nextInt(26) + 'A');
		return "" + b;
	}

	@SuppressWarnings("unused")
	private static void ssn() {
		for (int i = 0; i < TOTAL; i++) {
			System.out.println(generateNextSnn());
		}
	}

	private static String generateNextSnn() {
		return nextDigit() + nextDigit() + nextDigit() + "-" + nextDigit() + nextDigit() + "-" + nextDigit()
				+ nextDigit() + nextDigit() + nextDigit();
	}

	private static String nextDigit() {
		return "" + random.nextInt(10);
	}

}
