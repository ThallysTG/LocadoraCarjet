package br.carjet.locadora.model;

import java.util.Arrays;
import java.util.List;

public enum TipoUsuario {
	
	CLIENTE(1, "Cliente",
			Arrays.asList("login.xhtml", "template.xhtml", "aluguelcarros.xhtml", "detalhesaluguel.xhtml", "carrinho.xhtml",
					"historico.xhtml", "cadastrocliente.xhtml")),
	FUNCIONARIO(2, "Funcionario",
			Arrays.asList("carrinho.xhtml", "aluguelcarros.xhtml", "cadastrarcarros.xhtml", "cadastrousuario.xhtml",
					"consultausuario.xhtml", "detalhesaluguel.xhtml", "historico.xhtml", "login.xhtml", "signup.xhtml",
					"template.xhtml", "consultajogos.xhtml"));
	
	private int id;
	private String label;
	private List<String> pages;
	
	TipoUsuario(int id, String label, List<String> pages) {
		this.id = id;
		this.label = label;
		this.pages = pages;
	}

	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}

	public List<String> getPages() {
		return pages;
	}
	// retorna um perfil a partir de um valor inteiro
	public static TipoUsuario valueOf(int id) {
		for (TipoUsuario tipoUsuario : TipoUsuario.values()) {
			if (id == tipoUsuario.getId())
				return tipoUsuario;
		} 
		return null;
	}
	
}