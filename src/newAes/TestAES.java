package newAes;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class TestAES {

	/* Transform a byte array in an hexadecimal string */
	private static String toHex(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	/* Convert long to byte array */
	private static byte[] longToByteArray(long value) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(value);
		return buffer.array();

	}

	/* Convert a string representation in its hexadecimal string */
	private static String toHex(String arg) {
		return String.format("%02x", new BigInteger(1, arg.getBytes()));
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

	public static void main(String[] args) {
		String kzz = "0f1571c947d9e8590cb7add6af7f67980f1571c947d9e8590cb7add6af7f6798";//"0f1571c947d9e8590cb7add6af7f6798";//
//		String kzz = "2B7E151628AED2A6ABF7158809CF4F3C";
		byte[] kz = hexStringToByteArray(kzz);
		String cleartext = "0000000000000000000000000000000000000000000000000000000000000001";//"0123456789abcdeffedcba9876543210";//
//		String cleartext = "3243F6A8885A308D313198A2E0370734";
		byte[] plaintext = hexStringToByteArray(cleartext);
		byte[] ciphertext = new byte[plaintext.length];

		AESEngine newAES = new AESEngine(kz);
		newAES.Encrypt(plaintext, ciphertext);
		System.out.println("ciphertext: " + toHex(ciphertext));
		
		System.out.println("\n-------------------------------------------------------------------------\n");
		newAES.Decrypt(ciphertext, plaintext);
		System.out.println("plaintext: " + toHex(plaintext));
		
//		String ctr = "00000000000000000000000000000000";
//		byte[] Ctr = hexStringToByteArray(ctr);
//		AESEngine newAES = new AESEngine(Ctr);
//		for(int i=0;i<429496729;i++) {
//		newAES.increCTR(Ctr);
//		}
	}
}
