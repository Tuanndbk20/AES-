package newAes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.apache.http.Consts;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TestAES {

	public static String localhost = "127.0.0.1";
	public final static int port = 9999;

	/* Transform a byte array in an hexadecimal string */
	private static String toHex(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	/* Concatenation of two byte arrays */
	private static byte[] concatByteArrays(byte[] a, byte[] b) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			outputStream.write(a);
			outputStream.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] concatResult = outputStream.toByteArray();
		return concatResult;
	}

	/* Concatenation of two byte arrays */
	private static byte[] concatByteArrays(byte[] a, byte b) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			outputStream.write(a);
			outputStream.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] concatResult = outputStream.toByteArray();
		return concatResult;
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

<<<<<<< Updated upstream
	public static void main(String[] args) {
		String kzz = "0f1571c947d9e8590cb7add6af7f67980f1571c947d9e8590cb7add6af7f6798";//"0f1571c947d9e8590cb7add6af7f6798";//
//		String kzz = "2B7E151628AED2A6ABF7158809CF4F3C";
		byte[] kz = hexStringToByteArray(kzz);
		String cleartext = "0000000000000000000000000000000000000000000000000000000000000001";//"0123456789abcdeffedcba9876543210";//
//		String cleartext = "3243F6A8885A308D313198A2E0370734";
=======
	/************************** encryp and decrypt *******************************/
//	public static void main(String[] args) {
//		String kzz = "0f1571c947d9e8590cb7add6af7f6798";
////		String kzz = "2B7E151628AED2A6ABF7158809CF4F3C";
//		byte[] kz = hexStringToByteArray(kzz);
//		String cleartext = "0123456789abcdeffedcba9876543210";
////		String cleartext = "3243F6A8885A308D313198A2E0370734";
//		byte[] plaintext = hexStringToByteArray(cleartext);
//		byte[] ciphertext = new byte[plaintext.length];
//
//		AESEngine newAES = new AESEngine(kz);
//		newAES.Encrypt(plaintext, ciphertext);
//		System.out.println("ciphertext: " + toHex(ciphertext));
//		
//		System.out.println("\n-------------------------------------------------------------------------\n");
//		newAES.Decrypt(ciphertext, plaintext);
//		System.out.println("plaintext: " + toHex(plaintext));
//	}

	/**************************
	 * encrypt and send with plaintext
	 *******************************/
//	public static byte[] EncryptResource(byte[] plaintext) {
//		byte[] ciphertext = new byte[plaintext.length];
//		String kzz = "0f1571c947d9e8590cb7add6af7f6798";
//		byte[] kz = hexStringToByteArray(kzz);
//
//		AESEngine newAES = new AESEngine(kz);
//		newAES.Encrypt(plaintext, ciphertext);
//		return (ciphertext);
//	}
//	
//	public static void main(String[] args) {
//		String cleartext = "0123456789abcdeffedcba98765432100123456789abcdeffedcba9876543210";
//		byte[] plaintext = hexStringToByteArray(cleartext);
//		byte[] ciphertext = new byte[plaintext.length];
//
//		ciphertext = EncryptResource(plaintext);
//		System.out.println("ciphertext: " + toHex(ciphertext));
//		
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		URI uri = null;
//		try {
//			uri = new URIBuilder().setScheme("http").setHost(localhost).setPort(port)
//					.setPath("/DecryptAES").build();
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		/* Create the json body for the request */
//		JsonObject jsonBody = new JsonObject();
//		//jsonBody.addProperty("clientID", Constants.clientID);
//		jsonBody.addProperty("ciphertext", toHex(ciphertext));
//		jsonBody.addProperty("end", "end");
//		
//		Gson gson = new GsonBuilder().create();
//		String body = gson.toJson(jsonBody);
//
//		/* Create the http post request */
//		HttpPost httpPostRequest = new HttpPost(uri);
//		StringEntity entity = new StringEntity(body, ContentType.create("application/json", Consts.UTF_8));
//		httpPostRequest.setEntity(entity);
//		try {
//			CloseableHttpResponse response = httpClient.execute(httpPostRequest);
//			String responseData = EntityUtils.toString(response.getEntity());
//			EntityUtils.consume(response.getEntity());
//			System.out.println("Response: " + responseData);
//			System.out.println("Response status code: " + response.getStatusLine().getStatusCode());
//
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

	/**************************
	 * encrypt and send with text split frames
	 *******************************/
	public static byte[] EncryptResource(byte[] plaintext) {
		byte[] ciphertext = new byte[plaintext.length];
		String kzz = "0f1571c947d9e8590cb7add6af7f6798";
		byte[] kz = hexStringToByteArray(kzz);

		AESEngine newAES = new AESEngine(kz);
		newAES.Encrypt(plaintext, ciphertext);
		return (ciphertext);
	}

	private static int chunk_split(byte[] original, int lenFrame, byte[][] Arr) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(original);
		int n = 0;
		int i = 0;
		byte[] buffer = new byte[lenFrame];
		int p = (original.length % lenFrame);
		int pblock = (original.length / lenFrame);

		while ((n = bis.read(buffer)) > 0) {
			Arr[i] = concatByteArrays(buffer, (byte) i);
			i++;
			Arrays.fill(buffer, (byte) 0);
		}

		return p;
	}

	public static void main(String[] args) throws IOException {
		String cleartext = "0123456789abcdeffedcba9876543210";
>>>>>>> Stashed changes
		byte[] plaintext = hexStringToByteArray(cleartext);
		byte[] ciphertext = new byte[plaintext.length];

		ciphertext = EncryptResource(plaintext);
		System.out.println("ciphertext: " + toHex(ciphertext));

		CloseableHttpClient httpClient = HttpClients.createDefault();
		URI uri = null;
		try {
			uri = new URIBuilder().setScheme("http").setHost(localhost).setPort(port).setPath("/DecryptAES").build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpPost httpPostRequest = new HttpPost(uri);
		CloseableHttpResponse response;
		
<<<<<<< Updated upstream
		System.out.println("\n-------------------------------------------------------------------------\n");
		newAES.Decrypt(ciphertext, plaintext);
		System.out.println("plaintext: " + toHex(plaintext));
		
//		String ctr = "00000000000000000000000000000000";
//		byte[] Ctr = hexStringToByteArray(ctr);
//		AESEngine newAES = new AESEngine(Ctr);
//		for(int i=0;i<429496729;i++) {
//		newAES.increCTR(Ctr);
//		}
=======
		int lenFrame = 10;
		if(lenFrame > ciphertext.length) {
			System.out.println("lenFrame too long!");
			System.out.println("auto lenFrame = ciphertext.length!");
			lenFrame = ciphertext.length;
		}
		int num = (ciphertext.length % lenFrame == 0) ? (ciphertext.length / lenFrame)
				: (ciphertext.length / lenFrame + 1);
		byte[][] Arr = new byte[num][lenFrame + 1];
		int p = chunk_split(ciphertext, lenFrame, Arr);
		
		for (int i = 0; i < num; i++) {
			System.out.println(i + " array: " + toHex(Arr[i]));

			/* Create the json body for the request */
			JsonObject jsonBody = new JsonObject();
			// jsonBody.addProperty("clientID", Constants.clientID);

			if (i == num - 1) {
				jsonBody.addProperty("end", "endPlaintext");
				byte[] arr = new byte[p+1];
				System.arraycopy(Arr[i], 0, arr, 0, p);
				System.arraycopy(Arr[i], lenFrame, arr, p, 1);
				jsonBody.addProperty("ciphertext", toHex(arr));
			} else {
				jsonBody.addProperty("end", "to be continue!");
				jsonBody.addProperty("ciphertext", toHex(Arr[i]));
			}

			Gson gson = new GsonBuilder().create();
			String body = gson.toJson(jsonBody);

			/* Create the http post request */
			StringEntity entity = new StringEntity(body, ContentType.create("application/json", Consts.UTF_8));
			httpPostRequest.setEntity(entity);
			try {
				httpClient = HttpClients.createDefault();// run new session http
				response = httpClient.execute(httpPostRequest);
				String responseData = EntityUtils.toString(response.getEntity());
				EntityUtils.consume(response.getEntity());
				System.out.println("Response: " + responseData);
				System.out.println("Response status code: " + response.getStatusLine().getStatusCode());
//				if(responseData!=toHex(Arr[lenFrame])||(response.getStatusLine().getStatusCode()!=200)) {
//					
//				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		httpClient.close();

>>>>>>> Stashed changes
	}

}
