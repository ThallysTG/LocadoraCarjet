package br.carjet.locadora.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.carjet.locadora.application.Session;
import br.carjet.locadora.application.Util;
import br.carjet.locadora.dao.LocacaoDAO;
import br.carjet.locadora.model.Usuario;
import br.carjet.locadora.model.Locacao;

@Named
@ViewScoped
public class HistoricoLocacaoController implements Serializable {

	private static final long serialVersionUID = -8585030860902916284L;
	
	private List<Locacao> listaLocacao = null;
	
	/**
	 * Este metodo retorna todas a locacao do usuario logado
	 * 
	 * @return List<Locacao>
	 */
	public List<Locacao> getListaLocacao() {
		if (listaLocacao == null) {
			LocacaoDAO dao = new LocacaoDAO();
			Usuario usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
			listaLocacao = dao.findByUsuario(usuario.getId());
			if (listaLocacao == null)
				listaLocacao = new ArrayList<Locacao>();
		}
		return listaLocacao;
	}
	
	public void detalhes(Locacao locacao) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("locacaoFlash", locacao);
		Util.redirect("/LocadoraCarjet/faces/pages/detalheslocacao.xhtml");
	}
	
}