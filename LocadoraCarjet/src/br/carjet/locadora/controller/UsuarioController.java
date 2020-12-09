package br.carjet.locadora.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.carjet.locadora.dao.UsuarioDAO;
import br.carjet.locadora.model.Usuario;
import br.carjet.locadora.application.Util;
import br.carjet.locadora.model.TipoUsuario;

@Named
@ViewScoped
public class UsuarioController extends Controller<Usuario> implements Serializable {

	private static final long serialVersionUID = -3955368378894625110L;
	

	private List<Usuario> listaUsuario;
	
	public UsuarioController() {
		super(new UsuarioDAO());
	}
	
	@Override
	public Usuario getEntity() {
		if (entity == null)
			entity = new Usuario();
		return entity;
	}
	
	@Override
	public void limpar() {
		super.limpar();
		listaUsuario = null;
	}

	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			UsuarioDAO dao = new UsuarioDAO();
			listaUsuario = dao.findAll();
		}
		return listaUsuario;
	}

	
	@Override
	public boolean validarDados() {
		if (getEntity().getNome().isBlank()) {
			Util.addErrorMessage("O campo nome deve ser informado.");
			return false;
		}
		
		// gerando o hash da senha
		String senha = Util.hashSHA256(getEntity().getSenha());
		getEntity().setSenha(senha);
		
		return true;
	}

	public TipoUsuario[] getListaTipoUsuario() {
		return TipoUsuario.values();
	}

}
