package br.carjet.locadora.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.inject.Named;

import br.carjet.locadora.application.Session;
import br.carjet.locadora.application.Util;
import br.carjet.locadora.dao.UsuarioDAO;
import br.carjet.locadora.model.Usuario;

@Named
@RequestScoped
public class LoginController  {

//	@NotBlank(message = "O usuario não pode ser nulo.")
	private Usuario usuario;
	


	public String logar() {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario = dao.verificarLoginSenha(getUsuario().getLogin(),
				Util.hashSHA256(getUsuario().getSenha()));
		
		if (usuario != null) {
			// adicionando um ussuario na sessao
			Session.getInstance().setAttribute("usuarioLogado", usuario);
			// redirecionando para o template
			return "template.xhtml?faces-redirect=true";
		}
		Util.addErrorMessage("Login ou Senha inválido.");
		return "";
	}
	
	public void limpar() {
		usuario = null;
	}
	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void fazerCadastro() {
		Util.redirect("cadastrocliente.xhtml");
	}


	
}