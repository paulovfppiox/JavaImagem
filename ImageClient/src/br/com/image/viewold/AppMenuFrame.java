package br.com.image.viewold;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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

import br.com.image.control.ActionDownload;
import br.com.image.control.ActionLoader;
import br.com.image.model.SecureShellFTP;

/** Classe que conterá todos os dialogs e menu */

public class AppMenuFrame 										{
	
	private JFrame frame;
	private JDialog[] forms;
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
	}
	public AppMenuFrame(JFrame frame, JDialog[] forms, int numForms, SecureShellFTP shell, MenuBar menu) {
		super();
		this.frame = frame;
		this.forms = forms;
		this.numForms = numForms;
		this.shell = shell;
		this.menu = menu;
	}
	
	public void openDialog()						{
		System.out.println("Abrindo dialog!!!");
		newButton("Ok!");
		Window parentWindow = SwingUtilities.windowForComponent( frame );
        createDialog( parentWindow );
	}
	
	private void initUI()							{
		 // JPanel pane = newPane("Label in frame");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setSize(500, 420);
	     frame.setLayout(new FlowLayout());
	     frame.setLocationRelativeTo(null);
	     
	     // frame.add(pane);
	     
	     frame.add( new JLabel(new ImageIcon("./image/detranpbMultas.jpg")) );
	     frame.pack();
	     frame.setVisible(true);
	}

		
	 private static JButton newButton( String label ) 				{		
	        
	    	final JButton button = new JButton(label);
	        button.addActionListener(new ActionListener() 		{
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                Window parentWindow = SwingUtilities.windowForComponent(button);
	                // createDialog(parentWindow, button);
	            }
	        });
	        return button;
	 }

     private static JLabel newLabel(String label) {
        JLabel l = new JLabel(label);
        l.setFont(l.getFont().deriveFont(24.0f));
        return l;
     }

	
	/** Retorna um novo painel */
    public static JPanel newPane(String labelText) 			{
        JPanel pane = new JPanel(new BorderLayout());
        pane.add(newLabel(labelText));
        pane.add(newButton("Open dialog"), BorderLayout.SOUTH);
        return pane;
    }
	
	/** Cria um Dialog */
    private static void createDialog( Window parentWindow, JComponent... cmpCaller )		{
    	JDialog dialog = new JDialog(parentWindow);
        
        if ( cmpCaller != null )
        	dialog.setLocationRelativeTo(cmpCaller[0]);
        else
        	dialog.setLocationRelativeTo(null);
        
        dialog.setModal(true);
        dialog.add(newPane("Label in dialog"));
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

	public JDialog[] getForms() {
		return forms;
	}

	public void setForms(JDialog[] forms) {
		this.forms = forms;
	}

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
