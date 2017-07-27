package br.com.image.util;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class UIConfig {

	/*************************************************************************/
	public static void setup() {
		setLookAndFeel();
		String dir = System.getProperty("user.dir");

		Message p = Message.getInstance();

		p.addProperties(Message.CONFIG, dir
				+ "\\properties\\config.properties");

		p.addProperties(Message.MESSAGES, dir
				+ "\\properties\\messages.properties");

		p.addProperties(Message.ERRORS, dir
				+ "\\properties\\erro.properties");

		p.starContext();
	}

	/*************************************************************************/
	public static void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*************************************************************************/
}
