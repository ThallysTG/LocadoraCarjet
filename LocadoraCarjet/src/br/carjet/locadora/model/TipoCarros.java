package br.carjet.locadora.model;

public enum TipoCarros {
	SEDAN(1, "Sedan"), 
	DATCH(2, "Hatch");
	
	private int id;
	private String label;
	
	TipoCarros(int id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static TipoCarros valueOf(int id) {
		for (TipoCarros tipo : values()) {
			if (id == tipo.getId())
				return tipo;
		}
		return null;
	}
}


