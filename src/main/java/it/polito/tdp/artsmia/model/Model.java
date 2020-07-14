package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	ArtsmiaDAO dao;
	Graph<Artista,DefaultWeightedEdge> grafo ;
	public Map<Integer,Artista> mappaArtisti;
	
	public Model() {
		this.dao = new ArtsmiaDAO();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.mappaArtisti = new HashMap<>();
		List<Artista> artisti = this.dao.listVertex();
		for(Artista a : artisti ) {
			this.mappaArtisti.put(a.getId(),a);
			
		}
	}

	public List<String> getListRole(){
		return dao.listRole();
	}
	
	public Graph<Artista,DefaultWeightedEdge> creaGrafo(String role) {
		
		List<Artista> a = new ArrayList<>();
		a = this.dao.listVertex(role);
		Graphs.addAllVertices(this.grafo,a);
		
		List<Adiacenze> adiacenze = this.dao.listEdge(role, mappaArtisti);
		
		for(Adiacenze temp : adiacenze) {
			Graphs.addEdgeWithVertices(this.grafo, temp.getA1(), temp.getA2(),temp.getPeso());
		}
		
		
		
		
		return this.grafo;
		
	}
}
