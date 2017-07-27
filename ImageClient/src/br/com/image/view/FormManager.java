package br.com.image.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import br.com.image.control.ActionManager;

public class FormManager extends JFrame {

	/**
	 *
	 **/
	private static final long serialVersionUID = 1L;
	JToolBar bar;

	public FormManager(String title, ActionManager action) {
		super(title);
		setSize(new Dimension(500, 500));
	}

}
