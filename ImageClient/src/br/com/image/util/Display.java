package br.com.image.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Display {

	public static Color getBackGroundColorDefault() {
		return new Color(30, 144, 255);
	}

	public static Color getColorLabelDefault() {
		return new Color(30, 144, 255);
	}

	/*************************************************************************/
	public static void showInfo(Component component, String message) {
		JOptionPane.showMessageDialog(component, message, "SAInformação",
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon(
						"./image/informacao.png"));
	}

	/*************************************************************************/
	public static void showInfo(String message) {
		showInfo(null, message);
	}

	/*************************************************************************/
	public static void showError(Component component, String message) {
		JOptionPane.showMessageDialog(component, message, "SAErro",
				JOptionPane.ERROR_MESSAGE, new ImageIcon("./image/erro.png"));

	}

	/*************************************************************************/
	public static void showError(String message) {
		showError(null, message);
	}

	/*************************************************************************/
	public static void showError(String message, boolean exit) {
		showError(message);
		if (exit) {
			System.exit(0);
		}
	}

	/*************************************************************************/
	public static void showWarnig(Component component, String message) {
		JOptionPane.showMessageDialog(component, message, "Aviso",
				JOptionPane.WARNING_MESSAGE,
				new ImageIcon("./image/alerta.png"));
	}

	/*************************************************************************/
	public static void showWarnig(String message) {
		showWarnig(null, message);
	}

	/*************************************************************************/
	public static boolean confirm(Component component, String message) {
		return (JOptionPane.showConfirmDialog(component, message, "SAConfirm",
				0) == 0);
	}

	/*************************************************************************/
	public static boolean confirm(String message) {
		return confirm(null, message);
	}

	/*************************************************************************/
	public static void showOk() {
		showInfo("Operação concluida");
	}

}
