package br.carjet.locadora.controller;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.carjet.locadora.dao.CarrosDAO;
import br.carjet.locadora.model.Carros;

@Named
@ViewScoped
public class CarrosController extends Controller<Carros> {

	private static final long serialVersionUID = 1651642114811762868L;
	
	public CarrosController() {
		super(new CarrosDAO());
		Flash flash = FacesContext.getCurrentInstance().
				getExternalContext().getFlash();
		flash.keep("flashJogos");
		entity = (Carros) flash.get("flashCarros");
	}
	
	@Override
	public Carros getEntity() {
		if (entity == null)
			entity = new Carros();
		return entity;
	}
	
}