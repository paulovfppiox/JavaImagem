package br.com.image.viewold;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import br.com.image.*;
import br.com.image.exception.AppException;
import br.com.image.model.SecureShellFTP;
import br.com.image.model.UserAuthentication;

public class ConexaoManager extends JDialog 		{	
 
    private JTextField usernameTxt;		
    private JPasswordField passwd;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean isLoginOk;
    
    private FormUpload loaderWindow;			// Janela para carregar imagem
    private SecureShellFTP shell = SecureShellFTP.getInstance();
    
    public SecureShellFTP getShell()	{
    	return this.shell;
    }
    
    public ConexaoManager(JFrame parent)		 		{
    	
    	super(parent, "Login", true);
    	//shell = new SecureShellFTP();			/** Inicializa o shell para transferência */
    	shell = SecureShellFTP.getInstance();
    	shell.loadServerConfigs();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(new JLabel("Insira os seus dados para conexão"), cs );        
        
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(new JLabel("Usuário: "), cs);
 
        usernameTxt = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(usernameTxt, cs);
 
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(new JLabel("Senha: "), cs);
 
        passwd = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(passwd, cs);
        
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(new JLabel("Diretório: "), cs);
        
    	/** Usar caso necessário apresentar uma lista de diretórios
        JComboBox serverDirs = new JComboBox();
    	serverDirs.addItem("/workfile/detran/");
    	serverDirs.addItem("/workfile/detran/multas");
    	serverDirs.addItem("/workfile/detran/imgs-paulo/");
    	*/
        
        JLabel serverDir = new JLabel(shell.getRemoteImgFolder());
        serverDir.setForeground( Color.LIGHT_GRAY );
    	cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(serverDir, cs);
        
        panel.setBorder(new LineBorder(Color.GRAY));
 
        btnLogin = new JButton("Iniciar");
        btnLogin.addActionListener(new ActionButtonLogin(){});
        
        btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
 
    public String getUsername() 	{
        return usernameTxt.getText().trim();
    }
 
    public String getPassword() 	{
        return new String( passwd.getPassword() );
    }
 
    public boolean isSucceeded() {
        return isLoginOk;
    }
          
   /** public static void main(String[] args) 						{
        final JFrame frame = new JFrame("Detran - Gerenciador de Conexão");
        final JButton btnLogin = new JButton("Iniciar conexão");
        btnLogin.addActionListener	(
                new ActionListener()		{
                    public void actionPerformed(ActionEvent e) {
                        ConexaoManager loginDlg = new ConexaoManager(frame);
                        loginDlg.setVisible(true);
                        
                        if(loginDlg.isSucceeded())			{
                        	// frame.getContentPane().add(new JLabel( loginDlg.getUsername() + " conectado em " + loginDlg.getShell().getIpServer()  ));
                        	frame.getContentPane().add(new JLabel( loginDlg.getUsername() + " conectado no servidor de Imagens"  ));
                        	btnLogin.setText("Nova conexão");
                        }
                    }
                });
 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 420);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(btnLogin);
        frame.add(new JLabel(new ImageIcon("./image/detranpbMultas.jpg")));
        frame.setVisible(true);
    }*/
    
    
    private class ActionButtonLogin implements ActionListener {
    	
    	 public ActionButtonLogin(){}
    	 public void actionPerformed(ActionEvent e) {
             
    		 try {
				if ( UserAuthentication.authenticate(getUsername(), getPassword(), "txt")) {
				     isLoginOk = true;
				     shell.getIpServer();
				     dispose();
				     
				     loaderWindow = new FormUpload(shell);
			    	 loaderWindow.setVisible(true);
			    	 
				     /** SwingUtilities.invokeLater(() -> {
				     });*/
				     
				     
				 } else {
				     JOptionPane.showMessageDialog(ConexaoManager.this,
				             "Dado(s) inválidos!",
				             "Login",
				             JOptionPane.ERROR_MESSAGE);
				     // reset username and password
				     usernameTxt.setText("");
				     passwd.setText("");
				     isLoginOk = false;

				 }
			} catch (HeadlessException | AppException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
         }
    }
    
    
}