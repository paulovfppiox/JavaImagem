package br.com.image.viewold;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

/** Classe responsável por armazenar e configurar todos os paineis, bem como seus respectivos objetos de tratamento de eventos */


import br.com.image.*;
import br.com.image.control.ActionDownload;
import br.com.image.control.ActionLoader;
import br.com.image.model.*;
import br.com.image.view.*;
import br.com.image.util.*;
import br.com.image.interfaces.*;

public class AppMenu 					{

	private JFrame frame;
	
	/** Ids dos itens do menu 1*/
	final int UP_MN1 = 0; 
	final int DW_MN1 = 1;
	final int CONF_MN1 = 2;
	
	/** Ids dos itens do menu 2*/
	final int ORGAUT_MN2 = 0;
	final int CODAUT_MN2 = 1;
	
	private int painelAtual = 0; 
	
	/** Formulários de transferência **/  
	private ActionLoader actionUp;
	private FormUpload formUp;
	private FormDownload formDw;
	
	// Instancia do shell
	private SecureShellFTP shell = SecureShellFTP.getInstance();
	 
	/** Formulários diversos **/
	private JPanel[] forms;
	
	private JButton btnLogin;
	private static int numForms;
	
	/** Itens */
	ArrayList<ArrayList<JMenuItem>> menuItens = new ArrayList<ArrayList<JMenuItem>>();
	JMenuItem m1Item1;
	JMenuItem m1Item2;

	/** Barra */
	JMenuBar menubar;

	JPanel painelStatus; 
	JLabel status;		 // Mensagem

	/** Menu */
	ArrayList<JMenu> menus = new ArrayList<JMenu>();
	JMenu m1;
	JMenu m2; 
	
	public void setForms( JPanel... f )		{
		if (f != null)		{
			forms = new JPanel[f.length];
			forms = f;		
		}
	}
	
	/** ---------------------------------- Construtores ------------------------------ */
	public AppMenu( JFrame frame, JButton btn, FormUpload up, FormDownload dw )			{
		super();
		this.frame = frame;
		this.btnLogin = btn;
		formUp = up;
		formDw = dw;
		configFrame();
		criaBarraMenu(false);
	    //;setListeners();
	};
	
	public AppMenu()								{
		super();
		initFrame();
		configFrame();
		criaBarraMenu(false);
	    //setListeners();
	}
	
	public AppMenu( JFrame frame, JPanel[] f ) 		{
		super();
		this.frame = frame;
		this.forms = f;
	}
	
	public AppMenu( JFrame frame, FormUpload up, FormDownload dw,  ActionLoader ac1, ActionDownload ac2, JButton btnLogin ) 	{
		super();
		this.frame = frame;
		
		this.formUp = up;
		this.formDw = dw;
		
		this.actionUp = ac1;
		this.actionDw = ac2;
		
		this.btnLogin = btnLogin;
	}
	/** ------------------------------------------------------------------------------------------ */

	private void configFrame()												{
		
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setSize(500, 420);
	     frame.setLayout(new FlowLayout());
	     frame.setLocationRelativeTo(null);
	     // frame.getContentPane().add(btnLogin);
	       
	     /** Adiciona todos os paineis de formulário */
	     frame.getContentPane().add( formDw );	     
	     	     
	     if ( forms != null )
	    	 for(int j=0; j<=forms.length; j++)
	    	 frame.add(forms[j]);
	     
	     frame.add( new JLabel(new ImageIcon("./image/detranpbMultas.jpg")) );
	     frame.setVisible(true);
	     
	}

	private void initFrame()												{
		
		   frame = new JFrame("Detran - Gerenciador de Imagem");	       
	       actionUp = new ActionLoader(frame);  
	       formUp = new FormUpload();		  
	       actionUp.setCallerForm(formUp);
	       
	       actionDw = new ActionDownload(frame);  
	       formDw = new FormDownload();		  
	       actionDw.setCallerForm(formDw);
	       
	       /** Varre vetor dos outros forms
	        * */
	       
	       configFrame();
	}

	private class MenuAction implements ActionListener			{
		
		private JPanel painel = new JPanel(); // Painel
	    private MenuAction( JPanel p ) 		{
	        this.painel = p;
	    }
	    
		public MenuAction(){}
		
		@Override
		public void actionPerformed(ActionEvent event) 	{
			JMenuItem m = (JMenuItem) event.getSource();
			changePanel( painel );
		}	
		
    }
	
	private void criaBarraMenu(boolean x) 						{
		
		ArrayList<ArrayList<JMenuItem>> menuItens = new ArrayList<ArrayList<JMenuItem>>();
		ArrayList<JMenu> menus = new ArrayList<JMenu>();

		/** Instanciar/armazenar menus */
		JMenu m1 = new JMenu("Transferir imagem");
		JMenu m2 = new JMenu("Consultas");
		JMenu m3 = new JMenu("Ajuda");
		menus.add(m1);
		menus.add(m2);
		menus.add(m3);
		
		/** Instanciar/armazenar itens de menus */
		ArrayList<JMenuItem> lsItensM1 = new ArrayList<>();
	    ArrayList<JMenuItem> lsItensM2 = new ArrayList<>();
	    
	    lsItensM1.add( UP_MN1, new JMenuItem("Upload ") ); 
	    lsItensM1.add( DW_MN1, new JMenuItem("Download") );
	    lsItensM1.add( CONF_MN1, new JMenuItem("Configurações"));
	    lsItensM2.add( ORGAUT_MN2, new JMenuItem("Por órgão autuador") );
	    lsItensM2.add(CODAUT_MN2, new JMenuItem("Por código"));
	    menuItens.add( lsItensM1 );
	    menuItens.add( lsItensM2 );
	    
	    lsItensM1.get(0).addActionListener( new MenuAction(formUp) );
	    lsItensM1.get(1).addActionListener( new MenuAction(formDw) );
	    
	    
	    /** Acessar a estrutura local e gerar o menu */
	    menus.get(1).add( lsItensM2.get(0) );
	    menus.get(1).add( lsItensM2.get(1) );
	    menubar = new JMenuBar();
	    menubar.add(menus.get(0));
	    menubar.add(menus.get(1));
	    menus.get(0).add(lsItensM1.get(0));
	    menus.get(0).add(lsItensM1.get(1));
	    menus.get(0).add(lsItensM1.get(2));
	    frame.setJMenuBar(menubar);
	}


	/** Troca o painel corrente pelo passado */
	private void changePanel( JPanel form ) 						{
	    
		frame.getContentPane().removeAll(); 	   // Remove painel anterior
		frame.getContentPane().add(form, BorderLayout.CENTER);     		// Adiciona o painel novo
		frame.getContentPane().doLayout();		// configura layout e atualiza visão
		
		System.out.println("nome = " + form.getName());
	    Graphics g = frame.getGraphics();
	    
	    if (g != null) {	
	    	frame.update(g);
	    	frame.getContentPane().paintAll(g); // paintComponent(g);
	    }
	    // else 
	    frame.repaint();
	    frame.revalidate();
	    configStatusPanel();
	}

	public void configStatusPanel()							{
		
		painelStatus = new JPanel();
		painelStatus.setBorder(new BevelBorder(BevelBorder.LOWERED));
		frame.add(painelStatus, BorderLayout.SOUTH);
	    painelStatus.setPreferredSize(new Dimension( frame.getWidth(), 16) );
		painelStatus.setLayout(new BoxLayout(painelStatus, BoxLayout.X_AXIS));
		
		JLabel status = new JLabel("status");
		status.setHorizontalAlignment(SwingConstants.LEFT);
		painelStatus.add(status);
	}
	

	/*** public static void main(String[] args) 		{
			
	   javax.swing.SwingUtilities.invokeLater(new Runnable() {		   
		   
			public void run() {
				AppMenu InitAppMenu = new AppMenu();		
			}
		});
	}*/
	
	
}


