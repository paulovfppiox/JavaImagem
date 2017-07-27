package br.com.image.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import br.com.image.view.*;
import br.com.image.interfaces.*;

public class ActionLoader implements ActionListener 	{

	private static FormLoader form;
	
	public ActionLoader() 					{
		super();
	}

	public void setForm( FormLoader f )		{
		this.form = f;
	}

	@Override
	public void actionPerformed( ActionEvent event ) 		{
		
		String command = event.getActionCommand();

		if ( form.getPanel() != null)
			form.fileChooser();
		
		if (command.equals(	Cmds.FILE_CHOOSER.getCmdId() ))  	{
			System.out.println("Choosing file... ");
		}
		else if (command.equals( Cmds.UPLOAD_START.getCmdId() ))  {
			System.out.println("Uploading file... ");
		}
		
	}

	

	
	
}
