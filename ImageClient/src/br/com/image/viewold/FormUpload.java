package br.com.image.viewold;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import com.jcraft.jsch.JSchException;

import br.com.image.control.*;
import br.com.image.model.*;
import br.com.image.util.LoadingBar;
import br.com.image.view.*;
import br.com.image.interfaces.*;

public class FormUpload extends JPanel implements TransferFormIF			{

	static ArrayList<String> orgaos = new ArrayList<String>();  
	static ArrayList<String> codOrgaos = new ArrayList<String>();
	static JComboBox cmbOrgaos = new JComboBox();
	int selectedCodOrgao;
	JPanel painelStatus;
	JLabel status = new JLabel();
	Color statusCor;
	JLabel fileLabel;
	JButton fileBtn;
	
	static JButton btnLogin = new JButton("Login");
	JFormattedTextField numAutoTxt;
	boolean isImgLoaded;
	private SecureShellFTP shell = SecureShellFTP.getInstance(); /** Canal para transfer�ncia do arquivo ao servidor */
	String remoteFilename;		  /** Nome final do arquivo no servidor */
	String imgLocalPath;		  /** Caminho da imagem localmente */
	
	JPanel panel;
	JLabel imgLabel;
	
	static String imgFmtDefault = ".jpg";
	boolean isCamposOk;
	
	JProgressBar loaderBar = new JProgressBar();		 /** Define a Barra de Progresso */
	static int loaderMaxValue = 100;
	// Thread upTask;										/** Define uma Thread para simular rodando */
	boolean loaderCompleted = false;
	GridBagConstraints cs;
	JButton enviaBtn;
	
	JFrame frame;
	Font font = new Font("Arial", Font.BOLD, 16);
	
	public FormUpload() 		{
		// System.out.print("Oi!! estou no upload!!" + this.toString() );
		drawGUI();
	}
	
	public FormUpload( SecureShellFTP shell )							{
		this.shell = shell;
		drawGUI();
	}
		
	public FormUpload( SecureShellFTP shell, JFrame f ) 	{
		this.shell = shell;
		drawGUI();
    }

	public JLabel getStatus()					{
		return status;
	}
	
	/** Preencher o comboBox */
	private void fillCombOrgaos()				{
		for(int i=0; i<orgaos.size(); i++)
			cmbOrgaos.addItem( orgaos.get(i) );
	}
	
	/** Leitura dos �rg�os autuadores */
    public static void readOrgaos() 			{
		
		ArrayList<String> lines = new ArrayList<String>();
		String temp;
		
		try {
			BufferedReader br = new BufferedReader( new FileReader(new File("./config/orgaos")) );				
			while ((temp = br.readLine()) != null)	{
			
				int lineSize = temp.length();
				
				// C�digos
				String cod = temp.substring( lineSize-10, lineSize );
				String codBlank = cod.trim().replaceAll(" +", " ");		// c�digo sem brancos
				
				// Orgaos
				String org = temp.replace(cod, "");
				String orgBlank = org.trim().replaceAll(" +", " ");
				
				// System.out.println("orgB = " + orgBlank + "z");
				codOrgaos.add( codBlank  );
				orgaos.add( orgBlank );
				cmbOrgaos.addItem( orgBlank );
			}	
			
		} catch (FileNotFoundException e) 	{
			e.printStackTrace();
		} catch (IOException e) {
		}
	}
    
    
    /**
     * Realiza a formata��o de um campo de texto, para que aceite apenas d�gitos. 
     * 
     * @return NumberFormatter
     * */
    private MaskFormatter fmtCampo()				{
    
 	   MaskFormatter formatter = null;
 	   try {
 		   formatter = new MaskFormatter("#####");
 	   } catch (ParseException e) 	{
 		   e.printStackTrace();
 	   }
 	   return formatter;
    }
    
    public void validaCampos()						{
    	
    	statusCor = Color.red;
    	// status.setBackground(  );
    	
    	int idCod = orgaos.indexOf( (String) cmbOrgaos.getSelectedItem() );
        selectedCodOrgao = Integer.parseInt( codOrgaos.get( idCod ) );

    	if ( numAutoTxt.getText().isEmpty() )
    		status.setText( "N� de auto inexistente !!" );
    	
    	isCamposOk = true;
    	
    	try	{
    		createImgFilename();
    	} catch (Exception e) 		{
    		System.out.println("erro ao criar imagem !!!!");
    	}
    }
    
    // private void createImgFilename() throws	Exception			{
    private void createImgFilename() 	{
    	
    	String numAuto = numAutoTxt.getText();
    	
    	if ( isCamposOk ) 			{
    	
    		remoteFilename = numAuto + "-" + selectedCodOrgao + imgFmtDefault;
    		shell.setRemoteFilename(remoteFilename);
    		
    		/** if ( shell != null )
    			System.out.println("Not null");*/
    		
    	/** if ( shell != null && !remoteFilename.isEmpty() )	{
    		shell.setRemoteFilename(remoteFilename);
    		System.out.println(remoteFilename);
    	} */
    	// else throw new Exception();
    	}
    }
    
    private void setConsVals(int x, int y, int wid, int hei)	{
    	
    	cs.gridx = x;
    	cs.gridy = y;
    	
    	if (wid != -1)			{
	    	cs.gridwidth = wid;
    	} else if (hei != -1)	{
	    	cs.gridheight = hei;
    		
    	}
    }
    
    private void drawFileChooser()					{
    	 // ----------------- FileChooser ------------
    	setConsVals(1, 2, 1, -1);
        fileBtn = new JButton("Escolher imagem");
        fileBtn.setFont(font);
        fileBtn.setIcon(loadImage("./image/add.png"));
        fileBtn.addActionListener( new ActionLoader() );
        fileBtn.setActionCommand( Cmds.FILE_CHOOSER.getCmdId() );
        add(fileBtn, cs);
    }
    
    /*public ImageIcon loadImage(String imageName, int x, int y) 			{
    	ImageIcon imageIcon = new ImageIcon(new ImageIcon(imageName).getImage().getScaledInstance(x, y, Image.SCALE_DEFAULT));
		return imageIcon;
		return new ImageIcon(new ImageIcon(imageName);
	}*/
    
    public ImageIcon loadImage(String imageName) {
		return new ImageIcon(imageName);
	}

    
    private void drawOrgao()								{
        // -------------- �rg�os autuadores ---------
        setConsVals(0, 0, 1, -1);
        JLabel l = new JLabel("�rg�o autuador: ");
        l.setFont(font);
        add(l, cs );
        setConsVals(1, 0, -1, -1);
        
        /** panel.add( cmbOrgaos, cs );  -- Usar para varios orgaos */
        JLabel orgao = new JLabel("Detran-PB");
        orgao.setFont(font);
        add( orgao, cs );
    }
    
    /** ------------------- N� autos -------------- */
    private void drawNumAutos()						{
    	
	    NumberFormat formatter = NumberFormat.getNumberInstance(); 
		formatter.setMaximumIntegerDigits(6);
		setConsVals( 0, 1, 1, -1 );        
	    numAutoTxt = new JFormattedTextField( formatter );
	    Font f = new Font("Arial", Font.PLAIN, 16);
	    numAutoTxt.setFont( f );

	    JLabel l = new JLabel("N� do auto de infra��o: ");
	    l.setFont(font);
	    add( l, cs );
		  
		setConsVals( 1, 1, -1, -1 );
		add( numAutoTxt, cs );
    }
    
    private void drawBtnEnviar()					{
        setConsVals(1, 3, 1, -1);
        enviaBtn = new JButton("Enviar imagem");
        
        enviaBtn.setIcon(loadImage("./image/upload1.png"));
        enviaBtn.setSize(100, 60);
        enviaBtn.setFont( font );
        enviaBtn.setActionCommand( Cmds.UPLOAD_START.getCmdId() );
        // enviaBtn.addActionListener( new ImgTransferAction() );
        add(enviaBtn, cs);
    }
    
    private void drawNome()							{
        fileLabel = new JLabel("");
        fileLabel.setFont(font);
        setConsVals( 0, 5, 2, -1 );
        add(fileLabel, cs );
    }
    
    private void drawImg()							{
        imgLabel = new JLabel();
        imgLabel.setFont(font);
        setConsVals( 0, 6, 2, -1 );
        add(imgLabel, cs);
    }
    
    private void drawUpload()						{
        LoadingBar loader = new LoadingBar(loaderBar, enviaBtn, this );
        loaderBar.setMinimum(0); 				// Define o valor inicial da Barra
    	loaderBar.setMaximum( loaderMaxValue );	// Define o valor final da Barra de Progresso
    	loaderBar.setStringPainted(true); 	    // Mostra o valor na barra
    	setConsVals( 0, 7, 2, -1 );
        add(loaderBar, cs);
    }
    
    private void drawGUI() 							{

    	// readOrgaos();
    	
    	// Painel
        //panel = new JPanel( new GridBagLayout() );
    	this.setLayout( new GridBagLayout() );
        setBorder(new EmptyBorder(new Insets(40, 60, 40, 60)));
        
        // Constraints - layout
        cs = new GridBagConstraints();
        cs.insets = new Insets(4, 4, 4, 4);					// Espa�amento entre componentes desenhados
        cs.fill = GridBagConstraints.HORIZONTAL;
        
        // Desenha todos os componentes
        drawOrgao();
        drawNumAutos();      
        drawFileChooser();
        drawBtnEnviar();
        drawNome();
        drawImg();
        drawUpload();

        // configStatusPanel();
        
        // this.add(panel);
        /* setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setBounds(200, 200, 500, 500);
        setTitle("Upload de imagem");
        setLocationRelativeTo(null);
        this.pack();
        setVisible(true);*/
    }

    /** Instancia e configura a barra de status */
    public void configStatusPanel()					{
    	
    	painelStatus = new JPanel();
    	painelStatus.setBorder( new BevelBorder(BevelBorder.LOWERED) );
    	this.add( painelStatus, BorderLayout.SOUTH );
        painelStatus.setPreferredSize(new Dimension( getWidth(), 16) );
    	painelStatus.setLayout(new BoxLayout(painelStatus, BoxLayout.X_AXIS));

    	status.setForeground( statusCor );
    	status.setHorizontalAlignment( SwingConstants.LEFT );
    	painelStatus.add( status );
    }
    
    /** Teste 
    public static void main( String[] args ) 				{

    	FormUpload ex = new FormUpload();
    	/** SwingUtilities.invokeLater(() -> {
            
            // ex.setVisible(true);
        });
    }*/
    
    /** Temporizador para zerar o status */
   	private class StatusTimer extends TimerTask 		{
   		
   		Timer timer;
   		public StatusTimer(Timer t)	{
   			this.timer = t;
   		}
   		public void run() 			{
   	        status.setText("");
   			timer.cancel(); 
   	    }
   	}
       
    public void cleanStatusInTime(int delay)						{
       	Timer t = new Timer();
   		t.schedule(new StatusTimer(t), delay*1000);
    }

	@Override
	public void fileTransfDone() {
		fileLabel.setText( "Arquivo carregado em \n" + shell.getIpServer() + ":" + shell.getRemoteImgFullPath() );
	}

	@Override
	public double getPercentStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getTransferTime() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JPanel getPanel() {
		return this;
	}
	
	@Override
	public String toString() {
		return "FormUpload [selectedCodOrgao=" + selectedCodOrgao + ", painelStatus=" + painelStatus + ", status="
				+ status + ", statusCor=" + statusCor + ", fileLabel=" + fileLabel + ", fileBtn=" + fileBtn
				+ ", numAutoTxt=" + numAutoTxt + ", isImgLoaded=" + isImgLoaded + ", shell=" + shell
				+ ", remoteFilename=" + remoteFilename + ", imgLocalPath=" + imgLocalPath + ", panel=" + panel
				+ ", imgLabel=" + imgLabel + ", isCamposOk=" + isCamposOk + ", loaderBar=" + loaderBar
				+ ", loaderCompleted=" + loaderCompleted + ", cs=" + cs + ", enviaBtn=" + enviaBtn + "]";
	}
       
	public void fileChooser()	 				{
		
	      System.out.println("Escolhendo arquivo... ");
		
		  JFileChooser fileChooser = new JFileChooser("C:/Users/Paulo/Desktop/");
	        
    	  // Adiciona filtragem do tipo de arquivo para JPG
    	  fileChooser.setFileFilter( new FileFilter() 		{
    		   public String getDescription() 	{
    		       return "JPG Images (*.jpg)";
    		   }
    		   public boolean accept(File f) 	{
    		       if (f.isDirectory()) {
    		           return true;
    		       } else {
    		           String filename = f.getName().toLowerCase();
    		           return filename.endsWith(".jpg") || filename.endsWith(".jpeg") ;
    		       }
    		   }
    		});
    	  
    	  
    	  int returnValue = fileChooser.showOpenDialog(null);
    	  if (returnValue == JFileChooser.APPROVE_OPTION) 			{
    		  
    		  File selectedFile = fileChooser.getSelectedFile();
    		  System.out.println("Arq = " + selectedFile.getAbsolutePath() );
    		  
    		  imgLocalPath = selectedFile.getAbsolutePath();
    		  
    		  status.setText( selectedFile.getName() + " selecionado com sucesso!" );
    		  isImgLoaded = true;
    		  
    		 /** try	{
    		  } catch EmptyFileNameException 		{
    			  System.out.println("Error!!!");
    		  } catch ShellNotInitializedException()	{
    			  System.out.println("Error!!!");
    		  }*/
    		  
    		  // ImgViewer view = new ImgViewer(imgLocalPath);
    		  
    		  BufferedImage binImg = null;
				try {
					binImg = ImageIO.read(new File(imgLocalPath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		  int imgOriginalAlt = binImg.getWidth();
    		  int imgOriginalLarg  = binImg.getHeight();
    		  
    		  int imgAlt = imgOriginalAlt / 3; 
    		   
    		  int imgLarg = imgOriginalLarg / 3; 
    		  
    		 /** Math.round(imgAlt.d);
    		  Math.round(imgLarg);
    		  
    		  Image img = new ImageIcon(imgLocalPath).getImage().getScaledInstance( imgAlt, imgLarg, Image.SCALE_DEFAULT);
    		  ImageIcon imageIcon = new ImageIcon(img);
    		  imageIcon.getIconHeight();
    		  imageIcon.getIconWidth();
    		  frame.imgLabel.setIcon(imageIcon);
    		  frame.cleanStatusInTime(6);*/

    
  	    }
  	  
	}
	
    /** A��o para escolha de arquivos 
    private class fChooserBtnAction implements ActionListener 				{
    	
    	FormUpload frame;
    	
    	public fChooserBtnAction(FormUpload f)		{
    		this.frame = f;
    	};
    	
    	// Cmds.FILE_CHOOSER.getCmdId() 
    	
    	public void actionPerformed(ActionEvent ae)	 				{
  	        
    	  JFileChooser fileChooser = new JFileChooser("C:/Users/Paulo/Desktop/");
        
    	  // Adiciona filtragem do tipo de arquivo para JPG
    	  fileChooser.setFileFilter( new FileFilter() 		{
    		   public String getDescription() 	{
    		       return "JPG Images (*.jpg)";
    		   }
    		   public boolean accept(File f) 	{
    		       if (f.isDirectory()) {
    		           return true;
    		       } else {
    		           String filename = f.getName().toLowerCase();
    		           return filename.endsWith(".jpg") || filename.endsWith(".jpeg") ;
    		       }
    		   }
    		});
    	  
    	  
    	  int returnValue = fileChooser.showOpenDialog(null);
    	  if (returnValue == JFileChooser.APPROVE_OPTION) 			{
    		  File selectedFile = fileChooser.getSelectedFile();
    		  System.out.println("Arq = " + selectedFile.getAbsolutePath() );
    		  
    		  imgLocalPath = selectedFile.getAbsolutePath();
    		  
    		  status.setText( selectedFile.getName() + " selecionado com sucesso!" );
    		  isImgLoaded = true;
    		  
    		  /** try	{
    		  } catch EmptyFileNameException 		{
    			  System.out.println("Error!!!");
    		  } catch ShellNotInitializedException()	{
    			  System.out.println("Error!!!");
    		  }
    		  
    		  // ImgViewer view = new ImgViewer(imgLocalPath);
    		  
    		  BufferedImage binImg = null;
				try {
					binImg = ImageIO.read(new File(imgLocalPath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		  int imgOriginalAlt = binImg.getWidth();
    		  int imgOriginalLarg  = binImg.getHeight();
    		  
    		  /**
    		   *  int imgAlt = imgOriginalAlt / 3; 
    		   
    		  int imgLarg = imgOriginalLarg / 3; 
    		 /** Math.round(imgAlt.d);
    		  Math.round(imgLarg);
    		  
    		  Image img = new ImageIcon(imgLocalPath).getImage().getScaledInstance( imgAlt, imgLarg, Image.SCALE_DEFAULT);
    		  ImageIcon imageIcon = new ImageIcon(img);
    		  imageIcon.getIconHeight();
    		  imageIcon.getIconWidth();
    		  frame.imgLabel.setIcon(imageIcon);
    		  
    		  frame.cleanStatusInTime(6);
    		  *
    		  
    	  }
  	    }
    } */
    
}