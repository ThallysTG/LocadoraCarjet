package br.carjet.locadora.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.carjet.locadora.model.Locacao;
import br.carjet.locadora.model.ItemLocacao;
import br.carjet.locadora.model.Locacao;

@Named
@ViewScoped
public class DetalheLocacaoController implements Serializable{

	private static final long serialVersionUID = -6719949836747729139L;

	private Locacao locacao;
	
	public DetalheLocacaoController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		Locacao locacaoFlash = (Locacao) flash.get("locacaoFlash");
		if (locacaoFlash != null)
			locacao = locacaoFlash;

	}

	public Locacao getLocacao() {
		if (locacao == null) {
			locacao = new Locacao();
			locacao.setListaItemLocacao(new ArrayList<ItemLocacao>());
		}
		return locacao;
	}
}