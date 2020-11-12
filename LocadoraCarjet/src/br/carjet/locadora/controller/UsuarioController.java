package br.carjet.locadora.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.carjet.locadora.dao.UsuarioDAO;
import br.carjet.locadora.model.Perfil;
import br.carjet.locadora.model.Sexo;
import br.carjet.locadora.model.Usuario;

@Named
@ViewScoped
public class UsuarioController extends Controller<Usuario> implements Serializable {

	private static final long serialVersionUID = -3955368378894625110L;
	
	public UsuarioController() {
		super(new UsuarioDAO());
	}

	@Override
	public Usuario getEntity() {
		if (entity == null)
			entity = new Usuario();
		return entity;
	}
	
	public Sexo[] getListaSexo() {
		return Sexo.values();
	}

	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}

	
}
