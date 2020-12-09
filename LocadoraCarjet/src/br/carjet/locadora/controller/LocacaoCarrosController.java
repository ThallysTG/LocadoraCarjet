package br.carjet.locadora.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.carjet.locadora.application.Session;
import br.carjet.locadora.application.Util;
import br.carjet.locadora.dao.CarrosDAO;
import br.carjet.locadora.model.ItemLocacao;
import br.carjet.locadora.model.Carros;

@Named
@ViewScoped
public class LocacaoCarrosController implements Serializable {

	private static final long serialVersionUID = -3711196463081749135L;

	private String modelo;
	private List<Carros> listaCarros = null;
	
	public void pesquisar() {
		listaCarros = null;
	}
	
	public void adicionar(int idCarros) {
		CarrosDAO dao = new CarrosDAO();
		Carros Carros = dao.findById(idCarros);
		// verifica se existe um carrinho na sessao
		if (Session.getInstance().getAttribute("carrinho") == null) {
			// adiciona um carrinho (de itens de venda) na sessao
			Session.getInstance().setAttribute("carrinho", 
					new ArrayList<ItemLocacao>());
		}
		
		// obtendo o carrinho da sessao
		List<ItemLocacao> carrinho = 
				(ArrayList<ItemLocacao>) Session.getInstance().getAttribute("carrinho");
		
		// criando um item de venda para adicionar no carrinho
		ItemLocacao item = new ItemLocacao();
		item.setCarros(Carros);
		item.setValor(Carros.getPreco());
		
		// adicionando o item no carrinho (variavel local) 
		carrinho.add(item);
		
		// atualizando o carrinho na sessao
		Session.getInstance().setAttribute("carrinho", carrinho);
		
		Util.addInfoMessage("Carro adicionado no carrinho. "
				+ "Quantidade de Itens: " + carrinho.size());
		
	}

	public List<Carros> getListaCarros() {
		if (listaCarros == null) {
			CarrosDAO dao = new CarrosDAO();
			listaCarros = dao.findByModelo(getModelo());
			if (listaCarros == null)
				listaCarros = new ArrayList<Carros>();
		}
		return listaCarros;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
}