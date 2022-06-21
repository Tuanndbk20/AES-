package newAes;

import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class ConvertByteBase {
	public static String ConvertByteToBase(byte[] InByte) {
		byte[] encode = Base64.getEncoder().encode(InByte);
		String result = new String(encode);
		System.out.println(result);
		return result;
	}

	// Using encodeToString() to get String directly
//    String encodeToString = Base64.getEncoder().encodeToString("Java2blog".getBytes());
//    System.out.println(encodeToString);

	public static void main(String[] args) {
	        try {
	            byte[] name = Base64.getEncoder().encode("hello World".getBytes());
	            byte[] decodedString = Base64.getDecoder().decode(new String(name).getBytes("UTF-8"));
	            System.out.println(new String(decodedString));
	        } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
}
