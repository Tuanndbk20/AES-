package gcm;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.digests.SHA256Digest;

public class AES_GCM_demo {
	static String plainText = "74656d70657261747572657c7c73696c7665727c7c633035303165376563343064363637383862996437399235353035393564376234373162663134643431646362623139633732653536333064323935383032657c7c39353832376162343832643766306532363431356638656262333532646163397c7c34306432653930363638303435386534376232356431373038363164336463346362313932616436353433366162306239643738653233383061623863626630";
	public static final int AES_KEY_SIZE = 256;
	public static final int GCM_IV_LENGTH = 12;
	public static final int GCM_TAG_LENGTH = 16;

	public static void main(String[] args) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(AES_KEY_SIZE);

		// Generate Key
		String Key = "BY540/ysBOTli0G1AWqsYr+vRSN3J1xBRrogHNh5U0M=";
//		SecretKey key = keyGenerator.generateKey();
//		System.out.println("Key Text : " + (Base64.getEncoder().encodeToString(key.getEncoded())));
		byte[] decodedKey = Base64.getDecoder().decode(Key);
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

//        byte[] IV1 = new byte[GCM_IV_LENGTH];
//        SecureRandom random = new SecureRandom();
//        random.nextBytes(IV1);
//		String IV = toHex(IV1);
		String IV = "6431b30b0d3ae2b9e4853968";
//		System.out.println("nonce Text : " + toHex(IV));

		System.out.println("Original Text : " + plainText);

//        byte[] cipherText = encrypt(plainText.getBytes(), key, IV);
//        System.out.println("Encrypted Text : " + Base64.getEncoder().encodeToString(cipherText));

		float total = 0;
		String binary = "0010010100110101010100110010000111001100001011010010010111010011000111111000000010000101001100111111011000111100111101011111110101000110011011011100110010001000110110101110110010101000110111010011110101000101110110100000011111101010101100101101100100111111000110001000010111011110100100101001010101011110001101100001100010111000111101000011010100111110110010111110011000110100100101110101000110000100100000101000001100101101110000100001000011001111001111100100100010110110110010000001001010001010100010001011101110000111011001110110111111101001011101001010000110101111001111100010100100010010100110110001101000011111001100011101000100111101010110111100000000111000000110101111000101001111111110000000001111111101001110101100110000011001000011000011011101100100001001100010110011011011110110100010010110010101100010110001010101111111111001101100010101101111111001000111010000010010101011011011100010010011011110001110100000001100101000010110101011101011011101101100100100011010100110011010000100111111110000101001100000000110011101110010011001010011000000111010000110001111011101000110111010100000110011110110100100101111001110110110010000001000111110011101001001011011101011110111111110101110101111011101011011100001001011000101011110001011001100111101101110011101101010110001111011011111101110001011010111111110111110100110110000011001011000111011010101001110110100000000100110001011011001110100101010000111101100011101001001111011110000100001101010110000111100110000001100101001111000010001001111011110011101110010110100100100111100110001000000101111101000000010011100010100000110010010001110100101100000011101101100001010110001000110100101010111010000011010101000010001010101111001001110010110001011100000011110001110101001010000010011100011011100001000101011011101000011110000100110110100001010011100101010010001001001010000001010110101100111111111001010010001001010100011000111101010000001111101010010111110001010000110000110100000000010100000000100111110100110001111100110101001101000110111001001001000111111101000111110110100110010100101100100100010000011100000001000110011111111000010110011101100001100110010111001111011000001100010001000100000000110101110010011100111000011101000100001010100001010000101000010100010101111111001000000100110100001110100100111101100100011001111110011001001000101010100110000010011001111010000010001010100100010011001011111000000011011101101100111010011001001011011100001001011110101000100001010111001101111100111001110101000111001010101111011111000101100000100101110101001101100010100100101101000110101000001000000100011111011010100011101001100000101000011000011000011100000011000101010110110010000111101100110000001001001111101000100110101000111101110000100111100101101100101110010010010100011101000010010110100100011001100001110011000101011111110011111111011111111000111001010111100010000101111110100110000001101110110000010000001001001110010011101110110010010000010001100100101001001000011111100001000000100010000110101011111001011110000010100100000010011101010110111101111111011010100100011010100100011000011011000110101110001111111100000110110";
		String cleartext;
		for (int i = 2; i < 12; i++) {
			cleartext = ChangeAddBit(plainText, i, 2);

			byte[] cipherText = encrypt(cleartext.getBytes(), originalKey, hexStringToByteArray(IV));
			System.out.println("Encrypted Text : " + Base64.getEncoder().encodeToString(cipherText));

			String ciphertextHex = hexToBinary((toHex(cipherText)));
//			System.out.println("ciphertextHex length: " + ciphertextHex.length());
			System.out.println("ciphertextHex binary: " + ciphertextHex);
			total += CompareBit(ciphertextHex, binary);
			i++;
		}

//        String decryptedText = decrypt(cipherText, key, IV);
//        System.out.println("DeCrypted Text : " + decryptedText);
	}

	/* Transform a byte array in an hexadecimal string */
	private static String toHex(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	/* Convert a string representation in its hexadecimal string */
	private static String toHex(String arg) {
		return String.format("%02x", new BigInteger(1, arg.getBytes()));
	}

	private static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception {
		// Get Cipher Instance
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

		// Create SecretKeySpec
		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

		// Create GCMParameterSpec
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

		// Initialize Cipher for ENCRYPT_MODE
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

		// Perform Encryption
		byte[] cipherText = cipher.doFinal(plaintext);

		return cipherText;
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
		a = ((Count) / (Str1.length())) * 100;
		System.out.println("so ki tu giong nhau: " + Count);
		System.out.println("tong so ki tu: " + Str1.length());
		System.out.println("ty le thay doi ban ma: " + a);
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

	public static String decrypt(byte[] cipherText, SecretKey key, byte[] IV) throws Exception {
		// Get Cipher Instance
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

		// Create SecretKeySpec
		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

		// Create GCMParameterSpec
		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);

		// Initialize Cipher for DECRYPT_MODE
		cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

		// Perform Decryption
		byte[] decryptedText = cipher.doFinal(cipherText);

		return new String(decryptedText);
	}
}
