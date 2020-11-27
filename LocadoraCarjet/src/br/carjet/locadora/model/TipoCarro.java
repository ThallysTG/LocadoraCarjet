package br.carjet.locadora.model;

public enum TipoCarro {
	SEDAN(1, "Sedan"), 
	DATCH(2, "Hatch");
	
	private int id;
	private String label;
	
	TipoCarro(int id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static TipoCarro valueOf(int id) {
		for (TipoCarro tipo : values()) {
			if (id == tipo.getId())
				return tipo;
		}
		return null;
	}
}


