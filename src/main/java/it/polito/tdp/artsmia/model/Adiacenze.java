package it.polito.tdp.artsmia.model;

public class Adiacenze {

	private Artista a1;
	private Artista a2 ;
	private Integer peso ;
	public Artista getA1() {
		return a1;
	}
	public void setA1(Artista a1) {
		this.a1 = a1;
	}
	public Artista getA2() {
		return a2;
	}
	public void setA2(Artista a2) {
		this.a2 = a2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	public Adiacenze(Artista a1, Artista a2, Integer peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "Adiacenze [a1=" + a1 + ", a2=" + a2 + ", peso=" + peso + "]";
	}
	
	
	
}
