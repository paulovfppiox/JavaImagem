package br.com.image.interfaces;

import javax.swing.JPanel;

public interface TransferFormIF 						{
	
	void fileTransfDone();		/* Executa quando a transfer�ncia acabar */		
	double getPercentStatus();  /* Returna o estado da transfer�ncia */
	boolean hasFinished();		/* Retorna booleano que indica se a transfer�ncia acabou */
	boolean getTransferTime();	/* Retorna tempo de transfer�ncia */
	JPanel getPanel();
}