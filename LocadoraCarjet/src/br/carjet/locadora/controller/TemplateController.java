package br.carjet.locadora.controller;

import java.util.List;

import br.carjet.locadora.application.Session;
import br.carjet.locadora.application.Util;
import br.carjet.locadora.model.Usuario;
import br.carjet.locadora.model.ItemLocacao;

public class TemplateController {


	Usuario usuarioLogado = null;

	int qtdItensCarrinho;

	public TemplateController() {
		usuarioLogado = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
	}

	public void encerrarSessao() {
		Session.getInstance().invalidateSession();
		Util.redirect("../faces/login.xhtml");
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public int getQtdItensCarrinho() {
		qtdItensCarrinho = 0;
		List<ItemLocacao> itens = (List<ItemLocacao>) Session.getInstance().getAttribute("carrinho");
		if (itens != null)
			qtdItensCarrinho = itens.size();
		return qtdItensCarrinho;

	}

	public void redirecionar(String pagina) {
		Util.redirect(pagina);
	}
}
