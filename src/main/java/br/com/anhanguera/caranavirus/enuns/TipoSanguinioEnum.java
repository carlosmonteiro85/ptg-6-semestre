package br.com.anhanguera.caranavirus.enuns;

public enum TipoSanguinioEnum {
	
	A_POSITIVO("A+"),A_NEGATIVO("A-"), B_POSITIVO("B+"), B_NEGATIVO("B-"), AB_POSITIVO("AB+"), AB_negativo("AB-"), O_POSITIVO("O+"), O_NEGATIVO("O-");

	private String descricao;
	
	private TipoSanguinioEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoSanguinioEnum obterPorDescricao(String descricao) {
		for (TipoSanguinioEnum tipoSanguinio : values()) {
			if (tipoSanguinio.getDescricao().equals(descricao)) {
				return tipoSanguinio;
			}
		}
		return null;
	}
}
