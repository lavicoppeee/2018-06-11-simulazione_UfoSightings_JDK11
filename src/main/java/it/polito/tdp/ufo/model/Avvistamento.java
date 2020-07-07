package it.polito.tdp.ufo.model;


import java.time.LocalDateTime;

public class Avvistamento {

	int anno;
	int peso;
	
	public Avvistamento(int anno, int peso) {

		this.anno = anno;
		this.peso = peso;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return  anno + "  " + peso;
	}
	
	
	
	
}
