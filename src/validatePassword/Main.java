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

