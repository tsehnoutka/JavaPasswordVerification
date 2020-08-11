package validatePassword;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import validatePassword.Main.Strength;

class testValidatePassword {
 Main myMainClass = new Main();
 
	@Test
	void testInvalid() {
		assertEquals (Strength.invalid, myMainClass.validatePassword("12345678901234567890123456"), "12345678901234567890123456");  // longer than 25
		assertEquals (Strength.invalid, myMainClass.validatePassword("12345"),                      "12345");                       // shorter than 6
		assertEquals (Strength.invalid, myMainClass.validatePassword("1234 6&Tt"),                  "1234");                   		// space 
		assertEquals (Strength.invalid, myMainClass.validatePassword("iT*2 pX*8"),                  "iT*2s pX*(^&*JIB85kfsd");      // >10 space
		assertEquals (Strength.invalid, myMainClass.validatePassword("iT*2 pX*8"),                  "iT*2s pX*");                   // >8 <10 space
		assertEquals (Strength.invalid, myMainClass.validatePassword("iT*2 pX*8"),                  "iTy2s pX9");                   // >8 <10 space NO special char
		assertEquals (Strength.invalid, myMainClass.validatePassword("iT*2 p"),                  	"iT*2s p");                  	// >6 space
		//FAILING :  4 characters NOT allowed - { | } ~  (u007B - u007E)
		assertEquals (Strength.invalid, myMainClass.validatePassword("Timothy1~Patrick"),           "Timothy1~Patrick");            // Invalid special char ('~') > 10
		assertEquals (Strength.invalid, myMainClass.validatePassword("Timoth1~P"),           		"Timo1hy~P"); 		            // Invalid special char ('~') > 8 < 10
		assertEquals (Strength.invalid, myMainClass.validatePassword("Tim1t&y~P"),           		"Tim1t&y~P"); 		            // Invalid special char ('~') with a valid special char  > 8 < 10
		assertEquals (Strength.invalid, myMainClass.validatePassword("Timoth~"),           			"Timoth~");            			// Invalid special char ('~') > 6 < 8
	}
	
	@Test
	void testStrong() {
		assertEquals (Strength.strong,  myMainClass.validatePassword("Timothy1%Patrick"),           "Timothy1%Patrick");            // one of each, greater than 10
		assertEquals (Strength.strong,  myMainClass.validatePassword("Nufu&YM21S"),                 "Nufu&YM21S");                  // one of each, 10 char long
		assertEquals (Strength.strong,  myMainClass.validatePassword("123456789012Tt%6789012345"),  "123456789012Tt%6789012345");   // one of each, 25 char long
	}
	
	@Test
	void testMed() {
		assertEquals (Strength.medium,  myMainClass.validatePassword("Timothy1%"),                  "Timothy1%");                   // >8 <10 one of each
		assertEquals (Strength.medium,  myMainClass.validatePassword("iT*2spX*8"),                  "iT*2spX*8");                   // >8 <10 one of each
		assertEquals (Strength.medium,  myMainClass.validatePassword("iT52spX88"),                  "iT52spX88");                   // >8 <10 no special char
		assertEquals (Strength.medium,  myMainClass.validatePassword("123456789012Tt6789012345"),   "123456789012Tt6789012345");     // No special char, > 10
	}

	@Test
	void testWeak() {
		assertEquals (Strength.weak,    myMainClass.validatePassword("Timothy"), 					"Timothy");					  	// >6 < 8 no special char
		assertEquals (Strength.weak,    myMainClass.validatePassword("Timo1%"),                     "Timo1%");                      // <8 one of each                   
		assertEquals (Strength.weak,    myMainClass.validatePassword("1234567890123456789012345"),  "1234567890123456789012345");   // no special character, only one type ( no lower or upper)
		assertEquals (Strength.weak,    myMainClass.validatePassword("123456"),                     "123456");                      // <8 only one type
		assertEquals (Strength.weak,    myMainClass.validatePassword("gZAGel"),                     "gZAGel");                      // <8 only one type
	}

}
