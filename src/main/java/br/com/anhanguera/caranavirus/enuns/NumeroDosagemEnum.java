package br.com.anhanguera.caranavirus.enuns;

public enum NumeroDosagemEnum {
	
	PRIMEIRA("1ª"), SEGUNDA("2ª"), TERCEIRA("3ª");
	
	private String dose;
	
	private NumeroDosagemEnum(String dose) {
		this.dose = dose;
	} 
	
	public String getDose() {
		return dose;
	}
	
	public static NumeroDosagemEnum obterPorDose(String dose) {
		for (NumeroDosagemEnum dosagem : values()) {
			if (dosagem.getDose().equals(dose)) {
				return dosagem;
			}
		}
		return null;
	}

}
