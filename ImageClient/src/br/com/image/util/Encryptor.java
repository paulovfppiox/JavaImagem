package br.com.image.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author - Paulo
 *  Classe responsável por cifrar uma String. converte cadeia de bytes em hexadecimal 
 * */
public class Encryptor 			{
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	private static String SALT = "123456";
	
	/** Converte cadeia de bytes em hexadecimal */
	public static String bytesToHex(byte[] bytes) 		{
		
	   char[] hexChars = new char[bytes.length * 2];
	   int v;
	   for (int j = 0; j < bytes.length; j++) 	{
	     v = bytes[j] & 0xFF;
	     hexChars[j * 2] = hexArray[v >>> 4];
	     hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	   }
	   return new String(hexChars);
	 }
	
	 /** Método de hash de uma String */
	 public static String hashPassword(String in)	 {
	   
		 try 	{
		     MessageDigest md = MessageDigest.getInstance("SHA-256");
		     md.update(SALT.getBytes());        // <-- Prepend SALT.
		     md.update(in.getBytes());
		     // md.update(SALT.getBytes());     // <-- Or, append SALT.
		
		     byte[] out = md.digest();
		     return bytesToHex(out);            // <-- Return the Hex Hash.
		 } catch (NoSuchAlgorithmException e) {
			 e.printStackTrace();
		 }
	   return "";
	 }	

	 /** Debug use 
	 public static void main(String[] args) 			{
		 System.out.println(Encryptor.hashPassword("paulo2907"));
		 System.out.println(hashPassword("paulo2907"));
		 System.out.println(hashPassword("Hello1"));
		 System.out.println(hashPassword("Hello2"));
	 }*/
	 
}
