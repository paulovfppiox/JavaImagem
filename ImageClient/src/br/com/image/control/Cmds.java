package br.com.image.control;

public enum Cmds		{
	
	FILE_CHOOSER("0"),
	UPLOAD_START("1"),
	DWNLOAD_START("2"),
	IMG_VIEWER("3"),
	LOGIN_CANCEL("4"),
	LOGIN_START("5");
	
	private final String id;
	
	Cmds( String id )	{
		this.id = id;
	}
	public String getCmdId()	{
		return id;
	}

}