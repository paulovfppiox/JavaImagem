package br.com.image.viewold;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class MenuBar			{
	
	/** Ids dos itens do menu 1*/
	final int UP_MN1 = 0; 
	final int DW_MN1 = 1;
	final int CONF_MN1 = 2;
	
	/** Ids dos itens do menu 2*/
	final int ORGAUT_MN2 = 0;
	final int CODAUT_MN2 = 1;
	
	AppMenuFrame appMenu;	// componente ao qual será associado a barra
	
	/** Itens */
	ArrayList<ArrayList<JMenuItem>> menuItens = new ArrayList<ArrayList<JMenuItem>>();
	JMenuItem m1Item1;
	JMenuItem m1Item2;

	/** Barra */
	JMenuBar menubar;
	
	/** Menu */
	ArrayList<JMenu> menus = new ArrayList<JMenu>();
	JMenu m1;
	JMenu m2; 
	
	
	
	public MenuBar( AppMenuFrame frame )			{
		this.appMenu = frame;
		criaBarraMenu();
	}
	
	private void criaBarraMenu() 			{
		
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
	    
	    lsItensM1.get(0).addActionListener( new MenuAction() );
	    lsItensM1.get(1).addActionListener( new MenuAction() );
	    lsItensM1.get(2).addActionListener( new MenuAction() );
	    
	    
	    /** Acessar a estrutura local e gerar o menu */
	    menus.get(1).add( lsItensM2.get(0) );
	    menus.get(1).add( lsItensM2.get(1) );
	    menubar = new JMenuBar();
	    menubar.add(menus.get(0));
	    menubar.add(menus.get(1));
	    menus.get(0).add(lsItensM1.get(0));
	    menus.get(0).add(lsItensM1.get(1));
	    menus.get(0).add(lsItensM1.get(2));
	    appMenu.getFrame().setJMenuBar(menubar);
	}
	
	private class MenuAction implements ActionListener			{
		
		private JDialog dialog = new JDialog(); // Painel
	    private MenuAction(){}
	    @Override
		public void actionPerformed(ActionEvent event) 	{
			JMenuItem m = (JMenuItem) event.getSource();
			appMenu.openDialog();
		}	
		
    }
}
