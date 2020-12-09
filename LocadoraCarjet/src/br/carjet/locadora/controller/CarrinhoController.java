package br.carjet.locadora.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.carjet.locadora.application.Session;
import br.carjet.locadora.application.Util;
import br.carjet.locadora.dao.LocacaoDAO;
import br.carjet.locadora.model.ItemLocacao;
import br.carjet.locadora.model.Usuario;
import br.carjet.locadora.model.Locacao;

@Named
@ViewScoped
public class CarrinhoController implements Serializable{

	private static final long serialVersionUID = 780477496476930858L;

	private Locacao locacao;

	public Locacao getLocacao() {
		if (locacao == null) 
			locacao = new Locacao();
		
		// obtendo o carrinho da sessao
		List<ItemLocacao> carrinho = 
				(ArrayList<ItemLocacao>)Session.getInstance().getAttribute("carrinho");
		
		// adicionando os itens do carrinho na locacao
		if (carrinho == null)
			carrinho = new ArrayList<ItemLocacao>();
		locacao.setListaItemLocacao(carrinho);
		
		return locacao;
	}
	
	public void remover(int idProduto) {
		Usuario usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
		if (usuario == null)
			Util.addWarningMessage("Necessário estar logado para realizar um pedido");
		else {
			List<ItemLocacao> carrinho = (List<ItemLocacao>) Session.getInstance().getAttribute("carrinho");

			for (ItemLocacao itemLocacao : carrinho) {
				if (itemLocacao.getCarros().getId().equals(idProduto)) {
					carrinho.remove(itemLocacao);
					return;
				}
			}
		}
	}
	
	public void finalizar() {
		Usuario usuario = (Usuario)Session.getInstance().getAttribute("usuarioLogado");
		if (usuario == null) {
			Util.addWarningMessage("É preciso estar logado para realizar uma locacao. Faca o Login!");
			return;
		}
		// montar a locacao
		Locacao locacao = new Locacao();
		locacao.setData(LocalDate.now());
		locacao.setUsuario(usuario);
		List<ItemLocacao> carrinho = (ArrayList<ItemLocacao>)  Session.getInstance().getAttribute("carrinho");
		locacao.setListaItemLocacao(carrinho);
		// salvar no banco
		LocacaoDAO dao = new LocacaoDAO();
		if (dao.create(locacao)) {
			Util.addInfoMessage("Locacao realizada com sucesso.");
			// limpando o carrinho
			Session.getInstance().setAttribute("carrinho", null);
		} else {
			Util.addErrorMessage("Erro ao finalizar a locacao.");
		}
		
	}

	public void setLocacao(Locacao locacao) {
		
		this.locacao = locacao;
	}
	
	
	
}