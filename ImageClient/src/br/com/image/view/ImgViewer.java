package br.com.image.view;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author Paulo
 *  Janela para visualização mais detalhada da imagem carregada
 * */
public class ImgViewer extends JFrame			{
	
	private float larg;
	private float alt;
	
	private static String imgLocalPath;
	
	public ImgViewer(String localPath) 					{
		
		super(imgLocalPath); 
		imgLocalPath = localPath;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 420);
		setLayout(new FlowLayout());
		setLocationRelativeTo(this);
		add(new JLabel(new ImageIcon(imgLocalPath)));
		setVisible(true);
		
	}
	
	public void scaleImg( double perc )				{
		// to do

	}
	
	public void setPosition( double x, double y )	{
	}
	
	public void scaleImg( double x, double y )		{
		// to do

	}

}   