package br.carjet.locadora.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.carjet.locadora.application.Util;
import br.carjet.locadora.dao.CarroDAO;
import br.carjet.locadora.model.Carro;

@Named
@ViewScoped
public class ConsultaCarrosController implements Serializable {

	private static final long serialVersionUID = -7064857362220414218L;

	private Integer tipoFiltro;
	private String filtro;
	private List<Carro> listaCarro;
	
	public void novoCarro() {
		Util.redirect("carros.xhtml");
	}
	
	public void pesquisar() {
		CarroDAO dao = new CarroDAO();
		try {
			setListaCarro(dao.obterListaCarro(tipoFiltro, filtro));
		} catch (Exception e) {
			e.printStackTrace();
			setListaCarro(null);
		}
	}


	public void editar(Carro carro) {
		CarroDAO dao = new CarroDAO();
		Carro editarCarro = null;
		try {
			editarCarro = dao.obterUm(carro);
		} catch (Exception e) {
			e.printStackTrace();
			Util.addErrorMessage("Não foi possível encontrar a midia no banco de dados.");
			return;
		}
		
		Flash flash =  FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("carroFlash", editarCarro);
		novoCarro();
	}

	public Integer getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Carro> getListaCarro() {
		if (listaCarro == null)
			listaCarro = new ArrayList<Carro>();
		return listaCarro;
	}

	public void setListaCarro(List<Carro> listaCarro) {
		this.listaCarro = listaCarro;
	}

}