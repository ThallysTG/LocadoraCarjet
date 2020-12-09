package br.carjet.locadora.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.carjet.locadora.application.Util;
import br.carjet.locadora.dao.UsuarioLoginDAO;
import br.carjet.locadora.model.TipoUsuario;
import br.carjet.locadora.model.Usuario;

@Named
@ViewScoped
public class UsuarioLoginController extends Controller<Usuario> implements Serializable {

	private static final long serialVersionUID = -896455756378619853L;
	
	private List<Usuario> listaUsuarioLogin;
	
	public UsuarioLoginController() {
		super(new UsuarioLoginDAO());
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
		listaUsuarioLogin = null;
	}

	public List<Usuario> getListaUsuario() {
		if (listaUsuarioLogin == null) {
			UsuarioLoginDAO dao = new UsuarioLoginDAO();
			listaUsuarioLogin = dao.findAll();
		}
		return listaUsuarioLogin;
	}

	


	public TipoUsuario[] getListaTipoUsuario() {
		return TipoUsuario.values();
	}

}



