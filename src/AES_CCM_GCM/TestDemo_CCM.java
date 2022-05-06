package AES_CCM_GCM;


import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class TestDemo_CCM {

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
		a = ((Str1.length()-Count) / (Str1.length())) * 100;
//		System.out.println("so ki tu khac nhau: " + (Str1.length()-Count));
//		System.out.println("tong so ki tu: " + Str1.length());
//		System.out.println("ty le thay doi ban ma: " + a);
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

	static String hexToBinary(String hex)
    {
 
        // variable to store the converted
        // Binary Sequence
        String binary = "";
 
        // converting the accepted Hexadecimal
        // string to upper case
        hex = hex.toUpperCase();
 
        // initializing the HashMap class
        HashMap<Character, String> hashMap
            = new HashMap<Character, String>();
 
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

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//		// Generate a nonce (12 bytes)
//		SecureRandom random = new SecureRandom();
//		byte[] nonce = new byte[12];
//		random.nextBytes(nonce); // Fill the nonce with random bytes
//
//		String kzz = "5f5e1f20b4a333f8cdf1f04251ac0f93300bf1a9ce5b5436cd4b47b0c6a1ffab";
//		byte[] kz = hexStringToByteArray(kzz);
//		byte[] Kz = sha256(kz);
//
////		random = new SecureRandom();
////		byte[] a = new byte[16]; // Create a byte array with a size of 32 bytes
////		random.nextBytes(a); // Fill the array with random bytes
////
////		X9ECParameters ecp = SECNamedCurves.getByName("secp256r1");
////		ECDomainParameters domainParams = new ECDomainParameters(ecp.getCurve(), ecp.getG(), ecp.getN(), ecp.getH(),
////				ecp.getSeed());
////		ECPoint pointA = domainParams.getG().multiply(new BigInteger(a));
////
////		byte[] ECQVRandom = new byte[16];
////		random.nextBytes(ECQVRandom); // Fill the array with random bytes
////		System.out.println("u = " + toHex(ECQVRandom));
////
////		X9ECParameters ecp1 = SECNamedCurves.getByName("secp256r1");
////		ECDomainParameters domainParams1 = new ECDomainParameters(ecp1.getCurve(), ecp1.getG(), ecp1.getN(),
////				ecp1.getH(), ecp1.getSeed());
//
////		float totalMulti = 0;
////		float totalAdd = 0;
////		for (int i = 0; i < 10; i++) {
////			System.out.println("i: " + i);
////			/* Elliptic curve multiplication using the random number */
////
////			long nano_startTime = System.nanoTime();
////			ECPoint pointU = domainParams1.getG().multiply(new BigInteger(ECQVRandom));
////			long nano_endTime = System.nanoTime();
////			System.out.println("1_multi u.G: " + (nano_endTime - nano_startTime));
////
////			long nano_startTimeAdd = System.nanoTime();
////			ECPoint cert_u = pointU.add(pointA);
////			long nano_endTimeAdd = System.nanoTime();
////			System.out.println("1_add A+U: " + (nano_endTimeAdd - nano_startTimeAdd));
////
////			if(i!=0){
////			totalMulti += nano_endTime - nano_startTime;
////			totalAdd += nano_endTimeAdd - nano_startTimeAdd;
////			}
////		}
////
////		System.out.println("medium time muilti: " + totalMulti / 9999);
////		System.out.println("medium time add: " + totalAdd / 9999);
//
//		random = new SecureRandom();
//		byte[] cleartext = new byte[256];
//		random.nextBytes(cleartext);
//		//System.out.println("cleartext: "
//        //        + toHex(cleartext));
//		
//		byte[] resRegRandomZ = new byte[256];
//		random.nextBytes(resRegRandomZ); // Fill the array with random bytes
//		//System.out.println("z = " + toHex(resRegRandomZ));
//		
//		float total = 0;
//		float total1 = 0;
//		float totalSHA = 0;
//		for(int i=0; i<20000;i++) {
//			System.out.println("i: "+i);
//			
//			long nano_startTime = System.nanoTime();
//			// Encrypt the cleartext
//			CCMBlockCipher ccm = new CCMBlockCipher(new AESEngine());
//			ccm.init(true, new ParametersWithIV(new KeyParameter(Kz), nonce));
//			byte[] ciphertext = new byte[cleartext.length + 8];// output buffer
//			int len = ccm.processBytes(cleartext, 0, cleartext.length, ciphertext, 0);// the number of bytes written to out.
//			try {
//				len += ccm.doFinal(ciphertext, len);// Add MAC address or verify MAC address
//				
//			} catch (IllegalStateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvalidCipherTextException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			long nano_endTime = System.nanoTime();
//			System.out.println("Total time taken in nano seconds encrypt: "
//	                + (nano_endTime - nano_startTime));
//			
//			long nano_startTimeSHA = System.nanoTime();
//			byte[] Z = sha256(resRegRandomZ);
//			long nano_endTimeSHA = System.nanoTime();
//			System.out.println("Total time taken in nano seconds SHA: "+(nano_endTimeSHA-nano_startTimeSHA));
//			
//			long nano_startTime2 = System.nanoTime();
//			// Decoder Ciphertext
//			byte[] decode = null;
//			CCMBlockCipher ccm1 = new CCMBlockCipher(new AESEngine());
//			ccm1.init(false, new ParametersWithIV(new KeyParameter(Kz), nonce));
//			byte[] tmp = new byte[ciphertext.length];
//			int len1 = ccm1.processBytes(ciphertext, 0, ciphertext.length, tmp, 0);
//			try {
//				len1 += ccm1.doFinal(tmp, len1);
//				decode = new byte[len1];
//				System.arraycopy(tmp, 0, decode, 0, len1);
//			} catch (IllegalStateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvalidCipherTextException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			long nano_endTime2 = System.nanoTime();
//			
//			if(i!=0) {
//				total+=(nano_endTime - nano_startTime);
//				total1+=(nano_endTime2 - nano_startTime2);
//				totalSHA+=nano_endTimeSHA-nano_startTimeSHA;
//				}
//			System.out.println("Total time taken in nano seconds decrypt: " + (nano_endTime2 - nano_startTime2));
//
//			//System.out.println("cleartext: " + toHex(decode));
//		}
//		System.out.println("medium time encrypt: " + total/19999);
//		System.out.println("medium time decrypt: " + total1/19999);
//		System.out.println("medium time SHA: " + totalSHA/19999);
//
//	}
	
	public static void main(String[] args) {

		
		/* Plaintext change 8bit*/
//		String kzz = "5f5e1f20b4a333f8cdf1f04251ac0f93300bf1a9ce5b5436cd4b47b0c6a1ffab";
//		byte[] Kz = hexStringToByteArray(kzz);
//		
////		byte[] Kz;
//
////		SecureRandom random = new SecureRandom();
////		byte[] nonce = new byte[12];
////		random.nextBytes(nonce); // Fill the nonce with random bytes
//		String nonce ="6431b30b0d3ae2b9e4853968";
//		String cleartext1 = "74656d70657261747572657c7c73696c7665727c7c633035303165376563343064363637383862996437399235353035393564376234373162663134643431646362623139633732653536333064323935383032657c7c39353832376162343832643766306532363431356638656262333532646163397c7c34306432653930363638303435386534376232356431373038363164336463346362313932616436353433366162306239643738653233383061623863626630";
//		String hex ="36fa14998b43b943ea68f1cd478a141ad7300c47700a7a6ea46b5a31be277b83140fbdbfd28a715e36093f7af42c3d1299f553c0eb7d3c22d33db34310d05a476866800940043094190de74f483d190f3dd1aedbac724e4d0ea8732e615e78d3ea9a42b163b67c31db5ad605fb579d785bda23a2333e788109973d677aa273e8f7c841fec1877e82258cb697cbdce1f7aa6a36ff84b8f25d1265f26f5237f09f6ae5ddfd5c9294064015a308f8ae68b6aa616d5413f6e788dc830f1fab3a7b44ae";
//		String binary = "11001001111110100001010010011001100010110100001110111001010000111110101001101000111100011100110101000111100010100001010000011010110101110011000000001100010001110111000000001010011110100110111010100100011010110101101000110001101111100010011101111011100000110001010000001111101111011011111111010010100010100111000101011110001101100000100100111111011110101111010000101100001111010001001010011001111101010101001111000000111010110111110100111100001000101101001100111101101100110100001100010000110100000101101001000111011010000110011010000000000010010100000000000100001100001001010000011001000011011110011101001111010010000011110100011001000011110011110111010001101011101101101110101100011100100100111001001101000011101010100001110011001011100110000101011110011110001101001111101010100110100100001010110001011000111011011001111100001100011101101101011010110101100000010111111011010101111001110101111000010110111101101000100011101000100011001100111110011110001000000100001001100101110011110101100111011110101010001001110011111010001111011111001000010000011111111011000001100001110111111010000010001001011000110010110110100101111100101111011100111000011111011110101010011010100011011011111111100001001011100011110010010111010001001001100101111100100110111101010010001101111111000010011111011010101110010111011101111111010101110010010010100101000000011001000000000101011010001100001000111110001010111001101000101101101010101001100001011011010101010000010011111101101110011110001000110111000010100100010110100001011101010100001000100110110101001011111001";
//		float total=0;
//		System.out.println("key length: "+Kz.length);
//		String cleartext;
//		for(int i=0;i<20;i++) {
//			cleartext = ChangeAddBit(cleartext1,i,2);
////			cleartext = cleartext1;
////			System.out.println("cleartext: "+cleartext);
////			Kz = hexStringToByteArray(ChangeAddBit(kzz,i,2));
//			CCMBlockCipher ccm = new CCMBlockCipher(new AESEngine());
//			ccm.init(true, new ParametersWithIV(new KeyParameter(Kz), hexStringToByteArray(nonce)));
//			byte[] encryptedText = new byte[hexStringToByteArray(cleartext).length + 8];// output buffer
//			int len = ccm.processBytes(hexStringToByteArray(cleartext), 0, hexStringToByteArray(cleartext).length, encryptedText, 0);// the number of bytes written to out.
//			try {
//				len += ccm.doFinal(encryptedText, len);// Add MAC address or verify MAC address
//				
//			} catch (IllegalStateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvalidCipherTextException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
////			System.out.println("encrypt: "+encryptedText);
//			System.out.println("encrypt to hex: "+toHex(encryptedText));
//			String ciphertextHex = hexToBinary((toHex(encryptedText)));
////			System.out.println("ciphertextHex binary: "+ciphertextHex);
////			System.out.println("ciphertextHex length: "+ciphertextHex.length());
////			total +=CompareBit(ciphertextHex,binary);
////			total +=CompareBit(toHex(encryptedText),hex);
//			i++;
//		}
////		System.out.println("trung binh: "+total/5);
		
		
		
		
		
		/* key change 8bit*/
		String kzz = "5f5e1f20b4a333f8cdf1f04251ac0f93300bf1a9ce5b5436cd4b47b0c6a1ffab";
		byte[] Kz;

//		SecureRandom random = new SecureRandom();
//		byte[] nonce = new byte[12];
//		random.nextBytes(nonce); // Fill the nonce with random bytes
		String nonce ="6431b30b0d3ae2b9e4853968";
		
		
		String cleartext1 = "74656d70657261747572657c7c73696c7665727c7c633035303165376563343064363637383862996437399235353035393564376234373162663134643431646362623139633732653536333064323935383032657c7c39353832376162343832643766306532363431356638656262333532646163397c7c34306432653930363638303435386534376232356431373038363164336463346362313932616436353433366162306239643738653233383061623863626630";
		String binary = "00110110111110100001010010011001100010110100001110111001010000111110101001101000111100011100110101000111100010100001010000011010110101110011000000001100010001110111000000001010011110100110111010100100011010110101101000110001101111100010011101111011100000110001010000001111101111011011111111010010100010100111000101011110001101100000100100111111011110101111010000101100001111010001001010011001111101010101001111000000111010110111110100111100001000101101001100111101101100110100001100010000110100000101101001000111011010000110011010000000000010010100000000000100001100001001010000011001000011011110011101001111010010000011110100011001000011110011110111010001101011101101101110101100011100100100111001001101000011101010100001110011001011100110000101011110011110001101001111101010100110100100001010110001011000111011011001111100001100011101101101011010110101100000010111111011010101111001110101111000010110111101101000100011101000100011001100111110011110001000000100001001100101110011110101100111011110101010001001110011111010001111011111001000010000011111111011000001100001110111111010000010001001011000110010110110100101111100101111011100111000011111011110101010011010100011011011111111100001001011100011110010010111010001001001100101111100100110111101010010001101111111000010011111011010101110010111011101111111010101110010010010100101000000011001000000000101011010001100001000111110001010111001101000101101101010101001100001011011010101010000010011111101101110011110001000110111001000001100001111000111111010101100111010011110110100010010101110";
		String hex = "36fa14998b43b943ea68f1cd478a141ad7300c47700a7a6ea46b5a31be277b83140fbdbfd28a715e36093f7af42c3d1299f553c0eb7d3c22d33db34310d05a476866800940043094190de74f483d190f3dd1aedbac724e4d0ea8732e615e78d3ea9a42b163b67c31db5ad605fb579d785bda23a2333e788109973d677aa273e8f7c841fec1877e82258cb697cbdce1f7aa6a36ff84b8f25d1265f26f5237f09f6ae5ddfd5c9294064015a308f8ae68b6aa616d5413f6e788dc830f1fab3a7b44ae";
		float total=0;
		for(int i=0;i<1;i++) {
//			Kz = hexStringToByteArray(kzz);
			Kz = hexStringToByteArray(ChangeAddBit(kzz,i,2));
			CCMBlockCipher ccm = new CCMBlockCipher(new AESEngine());
			System.out.println("-------- init and save key, nonce, plaintext --------");
			ccm.init(true, new ParametersWithIV(new KeyParameter(Kz), hexStringToByteArray(nonce)));
			byte[] encryptedText = new byte[hexStringToByteArray(cleartext1).length + 8];// output buffer
			int len = ccm.processBytes(hexStringToByteArray(cleartext1), 0, hexStringToByteArray(cleartext1).length, encryptedText, 0);// the number of bytes written to out.
			try {
				len += ccm.doFinal(encryptedText, len);// Add MAC address or verify MAC address, len =0
				
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidCipherTextException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println("encrypt: "+toHex(encryptedText));
			System.out.println("encrypt: "+toHex(encryptedText));
			System.out.println("encrypt length: "+(encryptedText.length));
			String ciphertextHex = hexToBinary((toHex(encryptedText)));
//			System.out.println("ciphertextHex binary: "+ciphertextHex);
//			total +=CompareBit(ciphertextHex,binary);
			total +=CompareBit(toHex(encryptedText),hex);
			i++;
		}
		
//		System.out.println("trung binh: "+total/31);
		
		
	}
}

