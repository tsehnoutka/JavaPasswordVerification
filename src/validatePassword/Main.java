/*
Password Strength

Programming challenge description:
Write a function that uses regular expressions to determine a password's strength.
A valid password for this challenge is a UTF-8 encoded string consisting of a minimum of 6 and a maximum of 25 characters. The accepted range of characters is [U+0021, U+007A] in the Basic Latin Unicode block.
Use the following rules to determine the password strength:
1.    A strong password must have at least one lowercase letter [U+0061, U+007A], one uppercase letter [U+0041, U+005A], one digit [U+0030, U+0039], and one special character (all other characters from the valid range in the Basic Latin block). The length of a strong password must be at least 10 characters.
2.    The rule for a password of medium strength is the same as for strong, except it will not contain special characters and its length must be greater or equal to 8 characters. A password with the special character but shorter than 10 characters has a medium strength. For example, iT*2spX*8 is of medium strength.
3.    All other valid passwords are weak.
All value ranges in the above description are inclusive.

Input:
A string containing a password. For example:
Nufu&YM21S

Output:
Print out the password's strength: strong, medium, or weak.
Print invalid if the password is not valid.

Test 1
Nufu&YM21S
Expected OutputDownload Test 1 Input
strong

Test 2
iT*2spX*8
Expected OutputDownload Test 2 Input
medium

Test 3
gZAGel
Expected OutputDownload Test 3 Input
weak

Test 4
2N# 9k
Expected OutputDownload Test 4 Input
invalid

 */


package validatePassword;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Main {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	
	public enum Strength {
		invalid, weak, medium, strong
	}

	public Strength validatePassword(String input) {

		final String NOT_ALLOWED = "(?=[^\\{\\}\\|\\~]*$)(?=\\S+$)";  //  4 chars NOT allowed - { | } ~    and whitespace
		final String ALPHANUM = "(?=.*[\\u0061-\\u007A])(?=.*[\\u0041-\\u005A])(?=.*[\\u0030-\\u0039])"+NOT_ALLOWED;  // [a-z[A-z][0-9]
		final String SPECIAL_CHAR = "(?=.*[\\u0021-\\u002F\\u003A-\\u0040\\u005B-\\u0060])";
		final String STRONG_REGEX =  ALPHANUM + SPECIAL_CHAR ;
		final Pattern STRONG  = Pattern.compile("^" + STRONG_REGEX + ".{10,25}$");
		final Pattern MEDIUM  = Pattern.compile( "(^" + ALPHANUM + ".{8,25}$)|(^" + STRONG_REGEX + ".{8,10}$)" );
		final Pattern WEAK    = Pattern.compile("^(?=.*[\\u0021-\\u007A])(?=\\S+$)"+ NOT_ALLOWED+".{6,25}$");

		if (STRONG.matcher(input).matches())
			return Strength.strong;			
		if (MEDIUM.matcher(input).matches())
				return Strength.medium;	
		if (WEAK.matcher(input).matches())
				return Strength.weak;

		return Strength.invalid;
	} 

	/**
	 * Iterate through each line of input.
	 */
	public static void main(String[] args) throws IOException {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println(sdf.format(timestamp));
		InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
		BufferedReader in = new BufferedReader(reader);
		String line;
		Main myMainClass = new Main();

		while ((line = in.readLine()) != null) {
			System.out.println(line + " is " + myMainClass.validatePassword(line));

		}
	}
}

