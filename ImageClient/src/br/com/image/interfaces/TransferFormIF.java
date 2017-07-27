package br.com.image.interfaces;

import javax.swing.JPanel;

public interface TransferFormIF 						{
	
	void fileTransfDone();		/* Executa quando a transferência acabar */		
	double getPercentStatus();  /* Returna o estado da transferência */
	boolean hasFinished();		/* Retorna booleano que indica se a transferência acabou */
	boolean getTransferTime();	/* Retorna tempo de transferência */
	JPanel getPanel();
}