// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ViewDialog.java

package br.com.image.util;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class ViewDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*************************************************************************/
	public ViewDialog(String title, JComponent component, Dimension dimension) {
		initComponent(title, component, dimension);
	}

	/*************************************************************************/
	public ViewDialog(String title, JComponent component, Dimension dimension,
			JFrame owner) {
		initComponent(title, component, dimension);
		this.setLocationRelativeTo(owner);
	}

	/*************************************************************************/
	public ViewDialog(String title, JComponent component, Dimension dimension,
			JComponent owner) {
		initComponent(title, component, dimension);
		this.setLocationRelativeTo(owner);
	}

	/*************************************************************************/
	public void initComponent(String title, JComponent component, Dimension dimension) {
		this.setTitle(title);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.getContentPane().add(component);
		this.setPreferredSize(dimension);
		this.setModal(true);
		this.setResizable(false);
		this.pack();
	}
	
	/*************************************************************************/
	public void centerFullScreen(Component component){
		this.setLocationRelativeTo(component);
	}
	
	/*************************************************************************/
	public void showView() {
		this.setVisible(true);
	}
	/*************************************************************************/

}
