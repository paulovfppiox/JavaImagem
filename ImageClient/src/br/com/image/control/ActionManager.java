package br.com.image.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import br.com.image.view.FormManager1;

public class ActionManager implements ActionListener {

	FormManager1 form;

	@Override
	public void actionPerformed(ActionEvent event) {

	}

	/**
	 * @return the form
	 */
	public FormManager1 getForm() {
		return form;
	}

	/**
	 * @param form
	 *            the form to set
	 */
	public void setForm(FormManager1 form) {
		this.form = form;
	}

	public void showView() {
		if (form != null) {
			form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			form.setLocationRelativeTo(null);
			form.setVisible(true);
		}
	}

}
