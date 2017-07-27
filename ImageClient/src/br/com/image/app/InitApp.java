package br.com.image.app;

import br.com.image.control.ActionLogin;
import br.com.image.util.UIConfig;
import br.com.image.view.FormLogin;

public class InitApp 								{

	public static void main(String[] args) 		{
		UIConfig.setup();
		
		System.out.println("Iniciou app...");
		ActionLogin action = new ActionLogin();
		action.setForm(new FormLogin(action));
		action.login();

	}
}
