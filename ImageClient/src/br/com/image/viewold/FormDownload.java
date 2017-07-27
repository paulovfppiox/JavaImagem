package br.com.image.viewold;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Collections;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import br.com.image.control.*;
import br.com.image.view.*;
import br.com.image.interfaces.*;
import br.com.image.model.*;

public class FormDownload extends JPanel implements TransferFormIF	 {

	JTree tree;
	JScrollPane scrollpane;
	JPanel treePanel = new JPanel();
	SecureShellFTP shell; 
	
    private static final long serialVersionUID = 1L;
    private File rootdir = new File("./image");
    JButton abrirImgBtn;
    JButton downloadImgBtn; 
    
    public void setShell(SecureShellFTP sftp)			{
    	shell = sftp;
    }
    
    public File getRootDir()							{
    	return rootdir;
    }
    
    public void setRootDir( File f )					{
    	this.rootdir = f;
    }

    /** Cria hierarquia de arquivos */
    public FormDownload() 												{
    	
        setLayout(new BorderLayout(BoxLayout.X_AXIS, JFrame.EXIT_ON_CLOSE));
        tree = new JTree( addNodes(null, rootdir) );
        
        tree.addTreeSelectionListener(new TreeSelectionListener()	{
            @Override
            public void valueChanged(TreeSelectionEvent e) 	{
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
                System.out.println("You selected " + node);
            }
        });
        DefaultTreeCellRenderer rend = (DefaultTreeCellRenderer) tree.getCellRenderer();
        rend.setLeafIcon(null);
        rend.setClosedIcon(null);
        rend.setOpenIcon(null);
        
        scrollpane = new JScrollPane();
        scrollpane.getViewport().add(tree);
        
        abrirImgBtn = new JButton("Abrir imagem");
        JButton downloadImgBtn = new JButton("Iniciar download"); 
        
        treePanel.add( abrirImgBtn );
        treePanel.add( downloadImgBtn );
        
        downloadImgBtn.addActionListener(new ActionListener()	{
        	@Override
			public void actionPerformed(ActionEvent ev) {
        		try	{
        			shell.fileTransfer(false);
        		} catch (Exception ex)	{
        			ex.printStackTrace();
        		}
			}
        });
        
        abrirImgBtn.addActionListener(new ActionListener()		{

			@Override
			public void actionPerformed(ActionEvent ev) {
				/* ImgViewer view = new ImgViewer(rootdir.getAbsolutePath());*/
			}
        });
        
        add(BorderLayout.CENTER, scrollpane);
        add(BorderLayout.SOUTH, treePanel );
        
        System.out.println( "Dim = " + this.getSize() );
        /* setTitle("Download de imagem");
        setLocationRelativeTo(null);
        setForeground(Color.black);
        setBackground(Color.lightGray);
        pack();
        setVisible(true);*/
    }

    /** Monta a estrutura de dados em árvore a partir da raíz corrente */
    private DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
        
    	String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
        if (curTop != null) {
            curTop.add(curDir);
        }
        Vector<String> ol = new Vector<String>();
        String[] tmp = dir.list();
        for (int i = 0; i < tmp.length; i++) {
            ol.addElement(tmp[i]);
        }
        Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
        File f;
        Vector<Object> files = new Vector<Object>();
        for (int i = 0; i < ol.size(); i++) {
            String thisObject = ol.elementAt(i);
            String newPath;
            if (curPath.equals(".")) 	{
                newPath = thisObject;
            } 	else 	{
                newPath = curPath + File.separator + thisObject;
            }
            if ((f = new File(newPath)).isDirectory()) {
                addNodes(curDir, f);
            } 	else	 {
                files.addElement(thisObject);
            }
        }
        for (int fnum = 0; fnum < files.size(); fnum++) {
            curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
        }
        return curDir;
    }

    /** File transfer done.. */ 
    public void ftDone()								{
    	System.out.println("Downloaded... ");
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(200, 400);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 400);
    }

    public static void main(final String[] av) 			{
        
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() 		{
                FormDownload img = new FormDownload();
            }
        });
    }

	@Override
	public void fileTransfDone() 		{
		System.out.println("Acabou o download!!!");
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
		// TODO Auto-generated method stub
		return null;
	}
    
}
