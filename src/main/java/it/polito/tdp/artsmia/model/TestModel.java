package it.polito.tdp.artsmia.model;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		
		Graph<Artista,DefaultWeightedEdge> g = m.creaGrafo("Artist");
		
		for(DefaultWeightedEdge e : g.edgeSet()) {
			System.out.println(e);
		}
	}
	

}
