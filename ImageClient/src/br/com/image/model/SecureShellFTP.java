package br.com.image.model;

import java.awt.Color;
import java.awt.Container;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import org.joda.time.*; // java.time.Duration;
import org.joda.time.Instant;
import java.util.Timer;// time.Instant;
import java.util.ArrayList;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/** 
 * @author Paulo Paiva
 * Singleton responsável por armazenar o canal de transmissão segura com ssh (scp, sftp, etc...) 
 * Utilizado para download/upload de arquivos em um local remoto. 
 * As configurações do servidor podem ser definidas no arquivo definido em serverConfigFile
 * */
public final class SecureShellFTP 				{
	
	private static SecureShellFTP shellFTP;
	
	private static final String serverConfigFile = "./config/ftpconfig";
	
	// Valores default 
	private String ipServer;
	private String port;
	private String username;
	private String filename = "detimg.jpg";		// local 
	private String remoteFilename = "dest.jpg";
	private String passwd = "paulo2907";
	
	private String localImgFolder	= "./image/"; 
	private String remoteImgFolder;
	private String localImgFullPath;
	private String localDwldFullPath;
	private String remoteImgFullPath;
	
	private ChannelSftp sftpChannel;
	private Session session = null;
	private JSch jsch = new JSch(); 
	
	private float connectTime; 
	private final int serverParamsNum = 4;
	
	public boolean isServerConfigOk;
	private boolean isUploaded;
	
	/** @return SecureShellFTP = instancia do shell sftp */
	public static synchronized SecureShellFTP getInstance()	{
		if ( shellFTP == null )
			shellFTP = new SecureShellFTP();
		return shellFTP;
	}
	
	
	/** Realiza valiação dos dados de configuração do servidor */
	public boolean validateServerData()				{
		
		int ipLen = ipServer.replace(".", "").length();
		boolean resp = true;
		
		if ( ( ipLen < 4 )||( ipLen > 12 ) )		
			resp = false;
		
		return resp;
	}
	
	public static void main(String args[]) 			{
		
		SecureShellFTP shell = new SecureShellFTP();
		shell.loadServerConfigs();
		shell.mountFullPaths();
		
		try 	{
			shell.startConection();
			shell.fileTransfer(false);

		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			System.out.print("Erro de ftp! ");
			e.printStackTrace();
		} catch (Exception e) {
			
			System.out.print(e.getClass().getName() );
			
			e.printStackTrace();
		}
	}
	
	/** Carrega as configurações do servidor de SFTP presentes no arquivo ftpconfig.txt */
	public boolean loadServerConfigs()							{
		
		String blankLine = "";
		/** 
		 * --- Utilizar caso o n° de parâmetros do servidor almentar consideravelmente ---
		 * ArrayList<String> params = new ArrayList<String>();
		 * ArrayList<String> values = new ArrayList<String>();
		*/ 
		String serverData[] = {"", ""};
		
		int lineNum = 0;
		final int PARAM = 0;
		final int VALUE = 1;
		
		System.out.println("Carregando dados de configuração do servidor...");
		
		try {
			FileReader reader = new FileReader(new File( serverConfigFile ));
			BufferedReader br = new BufferedReader(reader);
			String temp = ""; 
			
			while ((temp = br.readLine()) != null)	{
				
				serverData = temp.replaceAll(" ", "").split("=");
				
				/** 
				 * params.add( serverData[PARAM] );
				 * values.add( serverData[VALUE] );
				*/
				
				if ( serverData[PARAM].equals("SERVER_PATH") )	
					remoteImgFolder = serverData[VALUE];
				else if ( serverData[PARAM].equals("SERVER_IP" ) )
					ipServer = serverData[1];
				else if ( serverData[PARAM].equals("SERVER_PORT") )
					port = serverData[1];
				else if ( serverData[PARAM].equals("USERNAME" ) )
					username = serverData[1];
				
				/** lineNum++; */
			}  
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
		
		/** Debug 
		System.out.print("Dados do servidor carregados." + "\n" + 
				ipServer + "\n" +
				username + "\n" + 
				remoteImgFolder + "\n" +
				port);
		*/
		
		isServerConfigOk = validateServerData();
		return isServerConfigOk;
	}
	
	public void SecureShellFTP()							{
		
	}
	
	/** Monta os caminhos do arquivo  */
	public final void mountFullPaths()						{
		localImgFullPath = localImgFolder + filename;
		remoteImgFullPath = remoteImgFolder + remoteFilename;
		localDwldFullPath = localImgFolder + remoteFilename;
		
		remoteImgFullPath.trim();
		localImgFullPath.trim();
	}
	
	/** Verifica se a img foi de fato transferida */	
	public void validaUploadImg()							{
	}
	
	public void startConection() throws Exception			{
		
		// session = jsch.getSession("PAULOV", "172.31.1.32", 22);
		session = jsch.getSession("PAULOV", ipServer, 22);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword( passwd );
		session.connect();	
	}
	
	/** 
	 * Realiza a transferência (upload/download) do filename para o remoteImgFullPath.
	 * @param isUpload - true = upload / false = download
	 * */
	public final void fileTransfer(boolean isUpload) throws Exception				{

		Instant inicio = Instant.now();
		
		if (!passwd.isEmpty()) 			{
			
			System.out.print( "local = " + localImgFullPath + "\n\n" + "rmt = " + remoteImgFullPath + "\n\n" );
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp sftpChannel = (ChannelSftp) channel;
			
			if ( isUpload )		{
				sftpChannel.put( localImgFullPath , remoteImgFullPath ); // Upload
			} 		else		{
				sftpChannel.get( remoteImgFullPath, localDwldFullPath );
								
				/* FileTree
				setRootDir*/
			}
			
			sftpChannel.exit();
			session.disconnect();
		} else {
			System.err.print("Erro! Senha indefinida! \n");
		}
		// connectTime = Duration.between(inicio, Instant.now()).getSeconds();
	}	
	
	
	public String getRemoteFilename()				{
		return remoteFilename;
	}
	public void setRemoteFilename(String filename)	{
		remoteFilename = filename;
	}
	
	public boolean getIsUploaded()		{
		return isUploaded;
	}
	
	public String getIpServer() {
		return ipServer;
	}

	public void setIpServer(String ipServer) {
		this.ipServer = ipServer;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getLocalImgFolder() {
		return localImgFolder;
	}

	public void setLocalImgFolder(String localImgFolder) {
		this.localImgFolder = localImgFolder;
	}

	public String getRemoteImgFolder() {
		return remoteImgFolder;
	}

	public void setRemoteImgFolder(String remoteImgFolder) {
		this.remoteImgFolder = remoteImgFolder;
	}

	public String getLocalImgFullPath() {
		return localImgFullPath;
	}

	public void setLocalImgFullPath(String localImgFullPath) {
		this.localImgFullPath = localImgFullPath;
	}

	public String getRemoteImgFullPath() {
		return remoteImgFullPath;
	}

	public void setRemoteImgFullPath(String remoteImgFullPath) {
		this.remoteImgFullPath = remoteImgFullPath;
	}

	public ChannelSftp getSftpChannel() {
		return sftpChannel;
	}

	public void setSftpChannel(ChannelSftp sftpChannel) {
		this.sftpChannel = sftpChannel;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public JSch getJsch() {
		return jsch;
	}

	public void setJsch(JSch jsch) {
		this.jsch = jsch;
	}

	public float getConnectTime() {
		return connectTime;
	}

	public void setConnectTime(float connectTime) {
		this.connectTime = connectTime;
	}

	public boolean isServerConfigOk() {
		return isServerConfigOk;
	}

	public void setServerConfigOk(boolean isServerConfigOk) {
		this.isServerConfigOk = isServerConfigOk;
	}

	public int getServerParamsNum() {
		return serverParamsNum;
	}

	@Override
	public String toString() {
		return "SecureShellFTP [ipServer=" + ipServer + ", port=" + port + ", username=" + username
				+ ", remoteImgFolder=" + remoteImgFolder + "]";
	}

	
		

}
