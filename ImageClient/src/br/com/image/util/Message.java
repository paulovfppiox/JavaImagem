package br.com.image.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/** @author Rubean
 * PropertiesManager
 * 
 */
public class Message {

	public static final String MESSAGES = "MESSAGES";
	public static final String CONFIG = "CONFIG";
	public static final String ERRORS = "ERROS";

	private Map<String, Properties> context;
	private Map<String, String> params;

	private static Message instance = new Message();

	/**
	 * @return the instance
	 */
	public static Message getInstance() {
		return instance;
	}

	/*************************************************************************/
	public Message() {
		context = new HashMap<String, Properties>();
		params = new HashMap<String, String>();
	}

	/*************************************************************************/
	public void addProperties(String name, String path) {
		params.put(name, path);
	}

	/*************************************************************************/
	public void starContext() {
		if (!params.isEmpty()) {
			Set<String> keys = params.keySet();

			for (String key : keys) {
				String path = params.get(key);
				System.out.println("KEY " + key + " PATH " + path);
				try {
					Properties properties = new Properties();
					FileInputStream fd1 = new FileInputStream(path);

					properties.load(fd1);
					context.put(key, properties);

				} catch (FileNotFoundException e) {

				} catch (IOException e) {

				}

			}
		}
	}

	/*************************************************************************/
	public String getPropConfig(String key) {
		return getProp(CONFIG, key);
	}

	/*************************************************************************/
	public String getPropErro(String key) {
		return getProp(ERRORS, key);
	}

	/*************************************************************************/
	public String getPropMessage(String key) {
		return getProp(MESSAGES, key);
	}

	/*************************************************************************/
	public String getPropErroExt(String key) {
		return getPropExt(ERRORS, key);
	}

	/*************************************************************************/
	public void finishContext() {

	}

	/*************************************************************************/
	private String getProp(String name, String key) {
		Properties properties = context.get(name);
		String result = "[ Não localizada a propriedade " + key + " ] ";
		if (properties != null && key != null) {
			String temp = properties.getProperty(key);
			if (temp != null)
				result = temp;
		}

		return result;
	}

	/*************************************************************************/
	private String getPropExt(String name, String key) {
		Properties properties = context.get(name);
		String result = key;
		if (properties != null && key != null) {
			String temp = properties.getProperty(key);
			if (temp != null) {
				result = temp;
			}
		}

		return result;
	}
	/*************************************************************************/

}
