package br.carjet.locadora.model;

import java.time.LocalDate;
import java.util.List;

public class Locacao extends Entity<Locacao> {

	private static final long serialVersionUID = 3530239405197865065L;
	private LocalDate data;
	private Usuario usuario;
	private List<ItemLocacao> listaItemLocacao;

	// campo calculado
	private Float totalLocacao = 0.0f;

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemLocacao> getListaItemLocacao() {
		return listaItemLocacao;
	}

	public void setListaItemLocacao(List<ItemLocacao> listaItemLocacao) {
		this.listaItemLocacao = listaItemLocacao;
	}

	public Float getTotalLocacao() {
		if (listaItemLocacao != null)
			for (ItemLocacao itemLocacao : listaItemLocacao)
				totalLocacao += itemLocacao.getValor();

		return totalLocacao;
	}

	public void setTotalLocacao(Float totalLocacao) {
		this.totalLocacao = totalLocacao;
	}

}