package br.carjet.locadora.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.carjet.locadora.application.Util;
import br.carjet.locadora.dao.CarrosDAO;
import br.carjet.locadora.model.Carros;

@Named
@ViewScoped
public class ConsultaCarrosController implements Serializable{
	
	private static final long serialVersionUID = 5971844866316069324L;
	
	private int tipoFiltro = 1;
	private String filtro;
	private List<Carros> listaCarros;
	
	public void pesquisar() {
		CarrosDAO dao = new CarrosDAO();
		if (tipoFiltro == 1)
			listaCarros = dao.findByModelo(getFiltro());
		else 
			listaCarros = dao.findByCategoria(getFiltro());
	}
	
	public void novoCarros() {
		Util.redirect("../pages/cadastrarcarros.xhtml");
	}
	
	public String editar(Carros carros) {
		CarrosDAO dao = new CarrosDAO();
		carros = dao.findById(carros.getId());
		
		Flash flash = FacesContext.getCurrentInstance().
						getExternalContext().getFlash();
		
		flash.put("flashCarros", carros);
		return "../pages/cadastrarcarros.xhtml?faces-redirect=true";
	}
	
	
	public void excluir(int id) {
		CarrosDAO dao = new CarrosDAO();
		dao.delete(id);
		listaCarros = null;
	}

	public List<Carros> getListaCarros() {
		if (listaCarros == null) {
			listaCarros = new ArrayList<Carros>();
		}
		return listaCarros;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public int getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(int tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

}