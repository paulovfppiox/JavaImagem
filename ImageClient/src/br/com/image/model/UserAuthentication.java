package br.com.image.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.image.exception.AppException;
import br.com.image.util.Encryptor;

/** @author Paulo Paiva
 * Classe responsável pela autenticação do usuário
 * */
public class UserAuthentication 			{
	
	private static final String TXT_FILE = "txt";	// por arquivo txt
	private static final String XML_FILE = "xml";	// por arquivo xml
	private static final String DB = "bd";		// por BD
	
	/** @param username 
	 *  @param password 
	 *  */
	public static boolean authenticate( String username, String password, String... authType ) throws AppException {
    	
		String[] userData = null;
		
		if ( authType != null && authType[0] == TXT_FILE  )
			userData = loadUserDataFromFile("./config/senha");
		else {
			if ( authType[0] == DB )
				userData = loadUserDataFromDB();
		}	
			
    	final int LOGIN = 0;
    	final int PASSWD = 1;
    	
    	String encrPasswd = Encryptor.hashPassword(password);	// Criptografa a senha recebida
        if (username.equals(userData[LOGIN]) && encrPasswd.equals(userData[PASSWD])) 	{
            return true;
        }
        return false;
    }
	
	/** Consulta os dados de login em um BD */
	private static String[] loadUserDataFromDB() 		{
		// to do
		return new String[2];
	}
    
    /** 
	 * Carrega a senha criptografada de um arquivo de texto
	 * 
	 * @param filePath - caminho do arquivo contendo as senhas
	 * @return vetor de 2 posições no formato [ LOGIN, SENHA ]
	 * */
	private static String[] loadUserDataFromFile( String filePath ) 		{
		String[] dataLogin = { "", "" };
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));

			String temp = br.readLine();
			if (temp != null) {
				temp.trim();							// remove brancos
				dataLogin = temp.split(":");	// separa username da senha
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}

		return dataLogin;
	}
    
    
}