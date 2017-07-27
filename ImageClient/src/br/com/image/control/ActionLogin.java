package br.com.image.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import br.com.image.exception.AppException;
import br.com.image.interfaces.TransferFormIF;
import br.com.image.model.UserAuthentication;
import br.com.image.model.UsuarioBC;
import br.com.image.util.Display;
import br.com.image.util.Message;
import br.com.image.util.Validate;
import br.com.image.view.ConexaoManager;
import br.com.image.view.FormLogin;
import br.com.image.view.FormManager;
import br.com.image.view.FormLoader;
import br.com.image.view.AppMenuFrame;

public class ActionLogin implements ActionListener {

	private FormLogin form;
	private JFrame auth;
	private UserAuthentication userAuth;
    
	public JFrame createLoginUI() 		{
		JFrame loginUI = new JFrame();
		loginUI.setTitle(Message.getInstance().getPropMessage("app.title"));
		loginUI.setSize(300, 320);
		loginUI.setLocationRelativeTo(null);
		loginUI.setDefaultCloseOperation(3);
		loginUI.add(getForm());
		loginUI.setResizable(false);
		loginUI.pack();
		return loginUI;
	}

	/**
	 * @return the form
	 */
	public FormLogin getForm() {
		return form;
	}

	/**
	 * @param form
	 *            the form to set
	 */
	public void setForm(FormLogin form) {
		this.form = form;
	}

	public void login() {
		auth = createLoginUI();
		auth.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event)		 {
		
		String command = event.getActionCommand();
		
		if (command.equals( Cmds.LOGIN_START.getCmdId()) ) 		{
			try {
				// Ler dados de login
				String matricula = form.getTxtLogin().getText();
				String senha = new String(form.getTxtSenha().getPassword());
				Validate.isEmpty(matricula, "app.acesso.matricula");
				Validate.isEmpty(senha, "app.acesso.senha");

				/** / Autentica usuário
				UserAuthentication userAuth = new UserAuthentication();
				if ( userAuth.authenticate( matricula, senha, "txt") )	{
					AppMenu InitAppMenu = new AppMenu();
				}*/
				AppMenuFrame InitAppMenu = new AppMenuFrame();
				
				/* ActionManager action = new ActionManager();
				action.setForm(new FormManager(Message.getInstance().getPropMessage("app.title"), action));
				action.showView();*/
				
				auth.dispose();
				
			} catch (AppException e) {
				Display.showError(form,
						Message.getInstance().getPropErro(e.getMessage()));
			}

		} else if (command.equals(Cmds.LOGIN_CANCEL.getCmdId()))	 {
			if (Display.confirm("Deseja realmente sair da aplicação")) {
				System.exit(0);
			}

		}
	}
	
	private void startAppMenu( boolean userAuthResult )		{
		// AppMenu InitAppMenu = new AppMenu(); 
	}

}
