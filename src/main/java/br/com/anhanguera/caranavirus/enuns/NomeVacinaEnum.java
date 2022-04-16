package br.com.anhanguera.caranavirus.enuns;

public enum NomeVacinaEnum {
	

	PFIZER("Pfizer"), CORONAVAC("Coronavac"), JANSSEN("Janssen"), ASTRAZENECA("Astrazeneca"), SPUTNIK("Sputnik"), COVAXIN("Covacxin");
	
	private String nomeVacina;
	
	private NomeVacinaEnum(String nomeVacina) {
		this.nomeVacina = nomeVacina;
	} 
	
	public String getNomeVacina() {
		return nomeVacina;
	}
	
	public static NomeVacinaEnum obterPorNome(String nomeVacina) {
		for (NomeVacinaEnum dosagem : values()) {
			if (dosagem.getNomeVacina().equals(nomeVacina)) {
				return dosagem;
			}
		}
		return null;
	}

}
