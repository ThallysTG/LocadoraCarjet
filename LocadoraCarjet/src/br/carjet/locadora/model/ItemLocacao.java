package br.carjet.locadora.model;

public class ItemLocacao extends Entity<ItemLocacao> {
	private static final long serialVersionUID = 4337294373727203428L;
	private Carros carros;
	private Float valor;
	private Locacao locacao;

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Locacao getLocacao() {
		return locacao;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}

	public Carros getCarros() {
		return carros;
	}

	public void setCarros(Carros carros) {
		this.carros = carros;
	}

}
