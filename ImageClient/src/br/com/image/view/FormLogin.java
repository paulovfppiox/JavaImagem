package br.com.image.view;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import br.com.image.control.ActionLogin;
import br.com.image.control.Cmds;
import br.com.image.util.Display;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class FormLogin extends JPanel {
	
	private static final long serialVersionUID = 1L;
	JButton btCancelar = new JButton();
	JButton btAcessar = new JButton();
	JLabel logo = new JLabel();
	JTextField txtLogin = new JTextField();
	JPasswordField txtSenha = new JPasswordField();
	
	public boolean isLoginOk;

	/**
	 * Default constructor
	 * 
	 * @param action
	 */
	public FormLogin(ActionLogin action) {
		initializePanel();

		btAcessar.addActionListener(action);
		btAcessar.setActionCommand( Cmds.LOGIN_START.getCmdId() );

		btCancelar.addActionListener(action);
		btCancelar.setActionCommand( Cmds.LOGIN_CANCEL.getCmdId() );
	}

	/**
	 * Adds fill components to empty cells in the first row and first column of
	 * the grid. This ensures that the grid spacing will be the same as shown in
	 * the designer.
	 * 
	 * @param cols
	 *            an array of column indices in the first row where fill
	 *            components should be added.
	 * @param rows
	 *            an array of row indices in the first column where fill
	 *            components should be added.
	 */
	void addFillComponents(Container panel, int[] cols, int[] rows) {
		Dimension filler = new Dimension(10, 10);

		boolean filled_cell_11 = false;
		CellConstraints cc = new CellConstraints();
		if (cols.length > 0 && rows.length > 0) {
			if (cols[0] == 1 && rows[0] == 1) {
				/** add a rigid area */
				panel.add(Box.createRigidArea(filler), cc.xy(1, 1));
				filled_cell_11 = true;
			}
		}

		for (int index = 0; index < cols.length; index++) {
			if (cols[index] == 1 && filled_cell_11) {
				continue;
			}
			panel.add(Box.createRigidArea(filler), cc.xy(cols[index], 1));
		}

		for (int index = 0; index < rows.length; index++) {
			if (rows[index] == 1 && filled_cell_11) {
				continue;
			}
			panel.add(Box.createRigidArea(filler), cc.xy(1, rows[index]));
		}

	}

	/**
	 * Helper method to load an image file from the CLASSPATH
	 * 
	 * @param imageName
	 *            the package and name of the file to load relative to the
	 *            CLASSPATH
	 * @return an ImageIcon instance with the specified image file
	 * @throws IllegalArgumentException
	 *             if the image resource cannot be loaded.
	 */
	public ImageIcon loadImage(String imageName) {
		return new ImageIcon(imageName);
	}

	/**
	 * Method for recalculating the component orientation for right-to-left
	 * Locales.
	 * 
	 * @param orientation
	 *            the component orientation to be applied
	 */
	public void applyComponentOrientation(ComponentOrientation orientation) {
		// Not yet implemented...
		// I18NUtils.applyComponentOrientation(this, orientation);
		super.applyComponentOrientation(orientation);
	}

	public JPanel createPanel() {
		JPanel panel = new JPanel();
		FormLayout formlayout1 = new FormLayout(
				"FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE,FILL:DEFAULT:NONE",
				"CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE,CENTER:DEFAULT:NONE");
		CellConstraints cc = new CellConstraints();
		panel.setLayout(formlayout1);

		JLabel l1 = new JLabel();
		l1.setFont(new Font("Arial", Font.BOLD, 16));
		l1.setText("Matrícula");
		l1.setHorizontalAlignment(JLabel.LEFT);
		panel.add(l1, cc.xywh(3, 15, 17, 1));

		JLabel l2 = new JLabel();
		l2.setFont(new Font("Tahoma", Font.BOLD, 16));
		l2.setText("Senha");
		l2.setHorizontalAlignment(JLabel.LEFT);
		panel.add(l2, cc.xywh(3, 21, 17, 1));

		btCancelar.setActionCommand("Cancelar");
		btCancelar.setFont(new Font("Arial", Font.BOLD, 16));
		btCancelar.setIcon(loadImage("image\\cancel.png"));
		btCancelar.setName("btCancelar");
		btCancelar.setText("Cancelar");
		panel.add(btCancelar, cc.xywh(3, 30, 15, 5));

		btAcessar.setActionCommand("Acessar");
		btAcessar.setFont(new Font("Arial", Font.BOLD, 16));
		btAcessar.setIcon(loadImage("image\\ok.png"));
		btAcessar.setName("btAcessar");
		btAcessar.setText("Acessar");
		panel.add(btAcessar, cc.xywh(19, 30, 14, 5));

		logo.setIcon(loadImage("image\\logo.jpg"));
		logo.setName("logo");
		logo.setHorizontalAlignment(JLabel.CENTER);

		panel.add(getPanelLogo(logo), cc.xywh(1, 1, 34, 12));

		txtLogin.setFont(new Font("Arial", Font.PLAIN, 16));
		txtLogin.setName("txtLogin");
		txtLogin.setToolTipText("Informe sua matrícula");
		panel.add(txtLogin, cc.xywh(3, 16, 30, 4));

		txtSenha.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSenha.setName("txtSenha");
		txtSenha.setToolTipText("Informe sua senha");
		panel.add(txtSenha, cc.xywh(3, 22, 30, 4));

		addFillComponents(panel, new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
				12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
				28, 29, 30, 31, 32, 33, 34 }, new int[] { 2, 3, 4, 5, 6, 7, 8,
				9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
				25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36 });
		return panel;
	}

	/**
	 * Initializer
	 */
	protected void initializePanel() {
		setLayout(new BorderLayout());
		add(createPanel(), BorderLayout.CENTER);
	}

	public JPanel getPanelLogo(JLabel logo) {

		JPanel panelLogo = new JPanel();
		panelLogo.add(logo);
		BevelBorder border = new BevelBorder(0, null, null, null, null);
		panelLogo.setBorder(border);
		panelLogo.setBackground(Display.getBackGroundColorDefault());

		return panelLogo;
	}

	/**
	 * @return the txtLogin
	 */
	public JTextField getTxtLogin() {
		return txtLogin;
	}

	/**
	 * @return the txtSenha
	 */
	public JPasswordField getTxtSenha() {
		return txtSenha;
	}

}
