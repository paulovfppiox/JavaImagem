package br.com.image.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.image.control.ActionLoader;
import br.com.image.model.SecureShellFTP;

/** Classe que conterá todos os dialogs e menu */

public class AppMenuFrame 										{
	
	private static JFrame frame;
	
	/** Depois posso agrupar isto em uma ED a parte (classe) */

	// private JPanel[] pForms;	// panel forms
	private FormLoader panel;
	private int numForms;
	
	private SecureShellFTP shell = SecureShellFTP.getInstance(); 	// Instancia do shell
	private MenuBar menu;

	public static void main(String[] args) 		{
			
		 javax.swing.SwingUtilities.invokeLater(new Runnable() {		   
	   
			public void run() {
					AppMenuFrame app = new AppMenuFrame();
			}
		 });
	}
	/** ---------------------------------------------- */
	public AppMenuFrame()		{
		frame = new JFrame("Detran - Gerenciador de Imagem");
		initUI();
		menu = new MenuBar(this);
		// panel = new FormLoader(frame);
	}
	public AppMenuFrame(JFrame frame, JDialog[] dForms, JPanel[] pForms) {
		super();
		this.frame = frame;
	}
	public AppMenuFrame(JFrame frame, JDialog[] dForms, JPanel[] pForms, int numForms, SecureShellFTP shell, MenuBar menu) {
		super();
		this.frame = frame;
		this.numForms = numForms;
		this.shell = shell;
		this.menu = menu;
	}
	
	
	/** ---------------------------------------------- */
	public void openDialog( int x )													{
		System.out.println("Abrindo dialog " + x + "...");
		Window parentWindow = SwingUtilities.windowForComponent( frame );
        createDialog( parentWindow, x );
	}
	
	public ImageIcon size( String path, int x, int y )								{
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(x, y, Image.SCALE_DEFAULT));
		return imageIcon;
	}
	
	private void initUI()							{
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setSize(500, 420);
	     frame.setLayout(new FlowLayout());
	     frame.setLocationRelativeTo(null);
	     
	     frame.add( new JLabel(new ImageIcon("./image/barra.png")) );
	     frame.pack();
	     frame.setVisible(true);
	}
	
	public static JPanel parseFormToPanel(int formId)		{
		FormLoader form = new FormLoader(frame, formId ); 
		return form.getPanel();
	}
	
	/** Cria um Dialog */
    private static void createDialog( Window parentWindow, int formId )		{
    	
    	JDialog dialog = new JDialog(parentWindow);
    	dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        
        if ( formId == 0 )
        	dialog.setTitle("Upload de Imagem");
        else 
        	dialog.setTitle("Download de Imagem");
        	
        dialog.add( parseFormToPanel(formId) );
        dialog.pack();
        dialog.setVisible(true);
    }
	
	/** ---------------------------------------------- */
	public JFrame getFrame() 							{
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/** public JPanel[] getpForms() {
		return pForms;
	}
	public void setpForms(JPanel[] pForms) {
		this.pForms = pForms;
	}*/
	public int getNumForms() {
		return numForms;
	}

	public void setNumForms(int numForms) {
		this.numForms = numForms;
	}

	public SecureShellFTP getShell() {
		return shell;
	}

	public void setShell(SecureShellFTP shell) {
		this.shell = shell;
	}

	public MenuBar getMenu() {
		return menu;
	}

	public void setMenu(MenuBar menu) {
		this.menu = menu;
	}
	
	

}
