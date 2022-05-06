package gcm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;

public class TestAes256GCM {

	// Pre-configured Encryption Parameters
	public static int NonceBitSize = 128;
	public static int MacBitSize = 128;
//	public static String encryptedText;

	/* Transform a byte array in an hexadecimal string */
	private static String toHex(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	/*
	 * Transform an hexadecimal string in byte array (It works if the string only
	 * contains the hexadecimal characters)
	 */
	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	/* Convert a string representation in its hexadecimal string */
	private static String toHex(String arg) {
		return String.format("%02x", new BigInteger(1, arg.getBytes()));
	}

	/* Perform SHA256 and return the result */
	private static byte[] sha256(byte[] data) {
		SHA256Digest digest = new SHA256Digest();
		byte[] hash = new byte[digest.getDigestSize()];
		digest.update(data, 0, data.length);
		digest.doFinal(hash, 0);
		return hash;
	}

	static float CompareBit(String Str1, String Str2) {
		float Count = 0, a;
		if (Str1.length() == Str2.length()) {
			for (int i = 0; i < Str1.length(); i++) {
				if (Str1.charAt(i) == Str2.charAt(i))
					++Count;
			}
		} else {
			System.out.println("Length Str1 != Str2!");
		}
		a = ((Str1.length() - Count) / (Str1.length())) * 100;
		// System.out.println("so ki tu khac nhau: " + (Str1.length()-Count));
		// System.out.println("tong so ki tu: " + Str1.length());
		// System.out.println("ty le thay doi ban ma: " + a);
		System.out.println(a);
		return a;
	}

	static float CompareBitTag(String Str1, String Str2, String text) {
		float Count = 0, a = 0;
		int Taglen = 0; // the tag GCM = 16 byte = 32hex =128bit

		if (text == "hex") {
			Taglen = 32;
		} 
		if(text=="binary") {
			Taglen = 128;
		}
		
		if (Str1.length() == Str2.length()) {
			for (int i = (Str1.length() - Taglen); i < Str1.length(); i++) {
				if (Str1.charAt(i) == Str2.charAt(i))
					++Count;
			}
		} else {
			System.out.println("Length Str1 != Str2!");
		}
		a = ((Taglen - Count) / Taglen) * 100;
		System.out.println(a);
		return a;
	}

	// num la so bit can thay num*4=so bit can thay. addr la vi tri bat dau thay
	static String ChangeAddBit(String Str, int addr, int num) {
		StringBuilder str = new StringBuilder(Str);
		char Arr[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		for (int j = 0; j < num; j++) {
			int i = 0;
			while (i < 16) {
				if (str.charAt(addr) == Arr[i]) {
					break;
				}
				i++;
			}
			str.setCharAt(addr, Arr[15 - i]);
			addr++;
		}
		return str.toString();
	}

	static String hexToBinary(String hex) {

		// variable to store the converted
		// Binary Sequence
		String binary = "";

		// converting the accepted Hexadecimal
		// string to upper case
		hex = hex.toUpperCase();

		// initializing the HashMap class
		HashMap<Character, String> hashMap = new HashMap<Character, String>();

		// storing the key value pairs
		hashMap.put('0', "0000");
		hashMap.put('1', "0001");
		hashMap.put('2', "0010");
		hashMap.put('3', "0011");
		hashMap.put('4', "0100");
		hashMap.put('5', "0101");
		hashMap.put('6', "0110");
		hashMap.put('7', "0111");
		hashMap.put('8', "1000");
		hashMap.put('9', "1001");
		hashMap.put('A', "1010");
		hashMap.put('B', "1011");
		hashMap.put('C', "1100");
		hashMap.put('D', "1101");
		hashMap.put('E', "1110");
		hashMap.put('F', "1111");

		int i;
		char ch;

		// loop to iterate through the length
		// of the Hexadecimal String
		for (i = 0; i < hex.length(); i++) {
			// extracting each character
			ch = hex.charAt(i);

			// checking if the character is
			// present in the keys
			if (hashMap.containsKey(ch))

				// adding to the Binary Sequence
				// the corresponding value of
				// the key
				binary += hashMap.get(ch);

			// returning Invalid Hexadecimal
			// String if the character is
			// not present in the keys
			else {
				binary = "Invalid Hexadecimal String";
				return binary;
			}
		}

		// returning the converted Binary
		return binary;
	}

	public static void main(String[] args) {

		// using above code these key and iv was generated
//		String hexKey = "2192B39425BBD08B6E8E61C5D1F1BC9F428FC569FBC6F78C0BC48FCCDB0F42AE";
		String kzz = "5f5e1f20b4a333f8cdf1f04251ac0f93300bf1a9ce5b5436cd4b47b0c6a1ffab";
		byte[] kz = hexStringToByteArray(kzz);
		byte[] Kz = sha256(kz);

//		String hexIV = "E1E592E87225847C11D948684F3B070D";
//		String nonces = "bf782587a8672d2e6ce0d161";
//		byte[] nonce = sha256(hexStringToByteArray(nonces));
//		System.out.println("nonce : " + toHex(nonce));
		SecureRandom random = new SecureRandom();
		byte[] nonce = new byte[12];
		random.nextBytes(nonce); // Fill the nonce with random bytes

		random = new SecureRandom();
		byte[] plainText = new byte[256];
		random.nextBytes(plainText);
		
//		String plainText1 = "74656d70657261747572657c7c73696c7665727c7c633035303165376563343064363637383862996437399235353035393564376234373162663134643431646362623139633732653536333064323935383032657c7c39353832376162343832643766306532363431356638656262333532646163397c7c34306432653930363638303435386534376232356431373038363164336463346362313932616436353433366162306239643738653233383061623863626630";
//		byte[] plainText = hexStringToByteArray(plainText1);

//		float total = 0;
//		float total1 = 0;
//		for (int i = 0; i < 10000; i++) {
//			System.out.println("i: " + i);
//			long nano_startTime = System.nanoTime();
//			String encryptedText = AesGcm256.encrypt(cleartext, Kz, nonce);
//			long nano_endTime = System.nanoTime();
//			System.out.println("Time taken in nano seconds encrypt: " + (nano_endTime - nano_startTime));
//			//System.out.println("encrypt: "+toHex(toHex(encryptedText)));
//			
//			long nano_startTime1 = System.nanoTime();
//			String decryptedText = AesGcm256.decrypt(encryptedText, Kz, nonce);
//			long nano_endTime1 = System.nanoTime();
//			if (i != 0) {
//				total += (nano_endTime - nano_startTime);
//				total1 += (nano_endTime1 - nano_startTime1);
//			}
//			 System.out.println("Time taken in nano seconds decrypt: " + (nano_endTime1 - nano_startTime1));
//			 System.out.println("decrypt: "+(decryptedText));
//		}
		

		float total = 0;
		float total1 = 0;
		int n=20000;
		for (int i = 0; i < n; i++) {
			
			long nano_startTime = System.nanoTime();
		    byte[] encryptedText = AesGcm256.encrypt(plainText, Kz, nonce);
			long nano_endTime = System.nanoTime();
//			System.out.println("Time taken in nano seconds encrypt: " + (nano_endTime - nano_startTime));
			
			// System.out.println("encrypt: "+toHex(toHex(encryptedText)));

			long nano_startTime1 = System.nanoTime();
			byte[] decryptedText = AesGcm256.decrypt(encryptedText, Kz, nonce);
			long nano_endTime1 = System.nanoTime();
//			if (i >=(n/2)) {
			if (i !=0) {
				total += (nano_endTime - nano_startTime);
				total1 += (nano_endTime1 - nano_startTime1);
			}
			
			if (0<i && i<= 2600) {
			System.out.println("i: " + i);
			System.out.println();
			System.out.println((nano_endTime - nano_startTime));
//			System.out.println("Time taken in nano seconds decrypt: " + (nano_endTime1 - nano_startTime1));
			System.out.println( (nano_endTime1 - nano_startTime1));
//			System.out.println("decrypt: " + toHex(toHex(decryptedText)));
			}
		}

//		System.out.println("plainText: "+toHex(toHex(plainText)));
		System.out.println("\nGCM medium time encrypt: " + total / (n-1));
		System.out.println("GCM medium time decrypt: " + total1 / (n-1));
	}


//	public static void main(String[] args) {
//
//		/* Plaintext change 8bit */
////		String kzz = "5f5e1f20b4a333f8cdf1f04251ac0f93300bf1a9ce5b5436cd4b47b0c6a1ffab";
////		byte[] Kz = hexStringToByteArray(kzz);
////
////		String nonce = "6431b30b0d3ae2b9e4853968";
////
////		String cleartext1 = "74656d70657261747572657c7c73696c7665727c7c633035303165376563343064363637383862996437399235353035393564376234373162663134643431646362623139633732653536333064323935383032657c7c39353832376162343832643766306532363431356638656262333532646163397c7c34306432653930363638303435386534376232356431373038363164336463346362313932616436353433366162306239643738653233383061623863626630";
////		String binary = "011101011001111100011011100010111110110001101001000010011011010010111100110010010101110100110010000001000000011011101010000110101000011010111011010101101111111001010101110011001010111100110011001101010101001001100010010101101010111101101011111000000101101110101101110001100011001011010101100011011011000100110011010111100101101111111010101101101011010100101110000111110000110010110100110000000100101000011111010010011100011010100000101001101011101101010110011111000100001111110011111110000101111010111000010111101110101011100011100110101110001001000001101101001010110001111111011001101110100101111010111111101111110011100111110010101101100011010001110110001100011110100010100101000110101100000000010110111111100100110001110000110101011110100000101100110110001000110000101110101100010100011000011110110110000100001111101010010000011011011110100111001101100001010010101011010111100011100100001111010000110011111011001000000101110000110011000101011000111010101001110001111101001100110100010000100010001101101011010101111010001001000110000010011100010011100101100011111001110001100011100110100111100110111110010111101100011001111000011000000000101010011110000000101011000110111000011110001000011001110110001100010011010011100110011000100100010110111110011000100001011101101100100111000100000100010010001101100101100000001011110001111000111011111100010001100101101100111101100011100101101101101000001001001110010001101011110111101110001100110001100011011011110100101000000100101000001010010111000111000100110101000101111001010111111000111001100010101001111110011101001110010101110001001000000011011110101101001010";
////		String hex = "759f1b8bec6909b4bcc95d320406ea1a86bb56fe55ccaf3335526256af6be05badc632d58db1335e5bfab6b52e1f0cb4c04a1f49c6a0a6bb567c43f3f85eb85eeae39ae241b4ac7f66e97afefce7cad8d1d8c7a2946b005bf931c357a0b36230bac5187b610fa906de9cd852ad78e43d0cfb205c33158ea9c7d33442236b57a24609c4e58f9c639a79be5ec678600a9e02b1b87886763134e66245be62176c9c411236580bc78efc465b3d8e5b6824e46bdee3318dbd281282971c4d45e57e398a9f9d395c480deb4a";
////		float total = 0;
////		String cleartext;
////		for (int i = 0; i < 200; i++) {
////			cleartext = ChangeAddBit(cleartext1, i, 2);
//////			cleartext = cleartext1;
////			String encryptedText = AesGcm256.encrypt(cleartext, Kz, hexStringToByteArray(nonce));
//////			System.out.println(encryptedText);
////			String ciphertextHex = hexToBinary((encryptedText));
//////			System.out.println(ciphertextHex);
////			
////			//compare ciphertext
//////			total +=CompareBit(ciphertextHex,binary);
//////			total +=CompareBit((encryptedText),hex);
////
////			// compare tag
////			total += CompareBitTag((encryptedText), hex, "hex");
//////			total += CompareBitTag(ciphertextHex, binary, "binary");
////			i++;
////		}
//
//		
//		
//		
//		/* Key change 8bit*/
//		String kzz = "5f5e1f20b4a333f8cdf1f04251ac0f93300bf1a9ce5b5436cd4b47b0c6a1ffab";
//		byte[] Kz;
//
//		String nonce ="6431b30b0d3ae2b9e4853968";
//		
//		
//		String cleartext1 = "74656d70657261747572657c7c73696c7665727c7c633035303165376563343064363637383862996437399235353035393564376234373162663134643431646362623139633732653536333064323935383032657c7c39353832376162343832643766306532363431356638656262333532646163397c7c34306432653930363638303435386534376232356431373038363164336463346362313932616436353433366162306239643738653233383061623863626630";
//		String binary = "011101011001111100011011100010111110110001101001000010011011010010111100110010010101110100110010000001000000011011101010000110101000011010111011010101101111111001010101110011001010111100110011001101010101001001100010010101101010111101101011111000000101101110101101110001100011001011010101100011011011000100110011010111100101101111111010101101101011010100101110000111110000110010110100110000000100101000011111010010011100011010100000101001101011101101010110011111000100001111110011111110000101111010111000010111101110101011100011100110101110001001000001101101001010110001111111011001101110100101111010111111101111110011100111110010101101100011010001110110001100011110100010100101000110101100000000010110111111100100110001110000110101011110100000101100110110001000110000101110101100010100011000011110110110000100001111101010010000011011011110100111001101100001010010101011010111100011100100001111010000110011111011001000000101110000110011000101011000111010101001110001111101001100110100010000100010001101101011010101111010001001000110000010011100010011100101100011111001110001100011100110100111100110111110010111101100011001111000011000000000101010011110000000101011000110111000011110001000011001110110001100010011010011100110011000100100010110111110011000100001011101101100100111000100000100010010001101100101100000001011110001111000111011111100010001100101101100111101100011100101101101101000001001001110010001101011110111101110001100110001100011011011110100101000000100101000001010010111000111000100110101000101111001010111111000111001100010101001111110011101001110010101110001001000000011011110101101001010";
//		String hex = "759f1b8bec6909b4bcc95d320406ea1a86bb56fe55ccaf3335526256af6be05badc632d58db1335e5bfab6b52e1f0cb4c04a1f49c6a0a6bb567c43f3f85eb85eeae39ae241b4ac7f66e97afefce7cad8d1d8c7a2946b005bf931c357a0b36230bac5187b610fa906de9cd852ad78e43d0cfb205c33158ea9c7d33442236b57a24609c4e58f9c639a79be5ec678600a9e02b1b87886763134e66245be62176c9c411236580bc78efc465b3d8e5b6824e46bdee3318dbd281282971c4d45e57e398a9f9d395c480deb4a";
//		long total=0;
//		for(int i=0;i<64;i++) {
//			Kz = hexStringToByteArray(kzz);
////			Kz = hexStringToByteArray(ChangeAddBit(kzz,i,2));
//			long star_enc = System.nanoTime();
//			String encryptedText = AesGcm256.encrypt(cleartext1, Kz, hexStringToByteArray(nonce));
//			total +=  (System.nanoTime() - star_enc);
//			System.out.println(i+ " ******star_enc: " + (System.nanoTime() - star_enc));
////			System.out.println((encryptedText));
//			String ciphertextHex = hexToBinary((encryptedText));
////			System.out.println(ciphertextHex);
//			
//			// compare ciphertext
////			total +=CompareBit(ciphertextHex,binary);
////			total +=CompareBit((encryptedText),hex);
//			
//			// compare tag
////			total += CompareBitTag((encryptedText), hex, "hex");
////			total += CompareBitTag(ciphertextHex, binary, "binary");
//			i++;
//		}
//		
//		System.out.println(" medium: "+total/32);
//		
////		String decryptedText = AesGcm256.decrypt(encryptedText, Kz, hexStringToByteArray(nonce));
////		System.out.println("decrypt: " + toHex(toHex(decryptedText)));
//
//	}
}
