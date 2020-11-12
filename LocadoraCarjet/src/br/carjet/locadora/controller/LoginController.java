package br.carjet.locadora.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.inject.Named;

import br.carjet.locadora.application.Util;

@Named
@RequestScoped
public class LoginController  {

//	@NotBlank(message = "O usuario n�o pode ser nulo.")
	private String usuario;
	
//	@Size(min = 6, max = 10, message = "A senha deve conter no m�nimo 6 d�gitos e maximo 10.")
//	@NotBlank(message = "A senha n�o pode ser nula.")
	private String senha;
	
//	private UIComponent usuarioUIComponent;

	public void entrar() {

		if (getUsuario().isBlank()) {
			Util.addErrorMessage("O campo usuario nao pode ser nulo. 2");
			return;
		}
		
		if (getUsuario().equals("janio") && 
				getSenha().equals("123")) {
			Util.redirect("usuario.xhtml");
		}
		UIComponent component = Util.findComponent("itUsuario");
		Util.addErrorMessage(component.getClientId(),
				"O usu�rio n�o existe.");
				
//		System.out.println("Cliente Id: ".concat(getUsuarioUIComponent().getClientId()));
//		Util.addErrorMessage(getUsuarioUIComponent().getClientId(),
//							"O usu�rio n�o existe.");
	}
	
	public void validarLogin(String idComponent) {
		UIComponent comp = Util.findComponent(idComponent);
		
		if (!usuario.equals("janio")) {
			Util.addErrorMessage(comp.getClientId(), "Usu�rio inv�lido.");
		}
	}
	
	public void limpar() {
		System.out.println("Limpar");
		usuario = "blah";
		senha = "";
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
}