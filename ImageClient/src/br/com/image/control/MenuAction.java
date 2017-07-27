package br.com.image.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenuItem;

import br.com.image.view.AppMenuFrame;

public class MenuAction implements ActionListener			{
	
	private JDialog dialog = new JDialog(); // Painel
    AppMenuFrame appMenu;
	
	public MenuAction(AppMenuFrame app)	{
		this.appMenu = app;
	}
	public void setAppMenu(AppMenuFrame app)	{
		this.appMenu = app;
	}
    
    
    @Override
	public void actionPerformed( ActionEvent event ) 		{
		
    	JMenuItem m = (JMenuItem) event.getSource();
    	
    	int formId = 0;
    	
    	if ( m.getText() == "Upload " ) formId = 0;
    	if 	( m.getText() == "Download" ) formId = 1; 
    	if 	( m.getText() == "Configurações" ) formId = 2;
    	if 	( m.getText() == "Por órgão autuador" ) formId = 3;
    	if 	( m.getText() == "Por código" ) formId = 4;
		
		appMenu.openDialog(formId);
	}	
	
}