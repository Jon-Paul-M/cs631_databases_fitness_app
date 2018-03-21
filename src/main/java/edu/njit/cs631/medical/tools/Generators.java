package edu.njit.cs631.medical.tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

public class Generators {

	private static Random random;
	private static final int TOTAL = 132;
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public Generators() {
		super();
	}

	public static void main(String[] args) {
		random = new Random();
		// ssn();
		// middleInitals();
        String fromPrevious = "$2a$10$yJSLXVodHQtynXWesCHI/.mzaT.07wJbfPsYfPv/Z11qWA2fIqgoG";
        String hash = encodeTestPassword("password");
        System.out.println(hash);
        System.out.println(decodeTestPassword("password", hash));
        System.out.println(decodeTestPassword("password", fromPrevious));

	}

	private static String encodeTestPassword(String pwd) {
	    return encoder.encode(pwd);
    }

    private static boolean decodeTestPassword(String pwd, String hash) {
	    return encoder.matches(pwd, hash);
    }

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
